package com.pblazaro.msextratobancario.infra.queue;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.pblazaro.msextratobancario.domain.ExtratoBancario;
import com.pblazaro.msextratobancario.domain.model.DepositoBanco;
import com.pblazaro.msextratobancario.domain.model.Movimentacoes;
import com.pblazaro.msextratobancario.infra.repository.ExtratoBancarioRepository;
import com.pblazaro.msextratobancario.infra.repository.MovimentacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Log4j2
public class ExtratoBancarioSubscriber {


    private final RetryTemplate retryTemplate;
    private final ExtratoBancarioRepository extratoBancarioRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public ExtratoBancarioSubscriber(RetryTemplate retryTemplate, ExtratoBancarioRepository extratoBancarioRepository, MovimentacaoRepository movimentacaoRepository) {
        this.retryTemplate = retryTemplate;
        this.extratoBancarioRepository = extratoBancarioRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @RabbitListener(queues = "${mq.queues.transacao-banco}")
    public void recebeMensagemTransacaoDeDeposito(String payload) {
        try {
            log.info("RECEBENDO MENSAGEM..");
            retryTemplate.execute(exc -> {
                ObjectMapper mapper = new ObjectMapper();
                DepositoBanco depositoBanco = mapper.readValue(payload, DepositoBanco.class);

                log.info("PROCESSANDO DADOS.. ");
                ExtratoBancario extratoBancario = extratoBancarioRepository
                        .findExtratoBancarioByIdContaCliente(depositoBanco.getIdContaCliente());

                log.info("PROCESSANDO DADOS DE MOVIMENTAÇÃO.. ");
                Movimentacoes movimentacoes = new Movimentacoes();
                movimentacoes.setSaldoAnterior(extratoBancario.getSaldo());
                movimentacoes.setTipoTransacao(depositoBanco.getTipoTransacao());
                movimentacoes.setValorMovimentado(depositoBanco.getValorDeposito());

                if (depositoBanco.getStatus() == DepositoBanco.StatusDeposito.ERRO) {
                    log.warn("MOVIMENTAÇÃO INVÁLIDA DETECTADA - REPROCESSANDO..");
                    throw new Exception("TRANSAÇÃO INVÁLIDA");
                }
                BigDecimal saldoAtual = extratoBancario.getSaldo().add(depositoBanco.getValorDeposito());
                movimentacoes.setSaldoAtual(saldoAtual);
                movimentacaoRepository.save(movimentacoes);
                log.info("ATRIBUINDO MOVIMENTAÇÃO A EXTRATO.. ");
                extratoBancario.getMovimentacoes().add(movimentacoes);
                extratoBancario.setSaldo(saldoAtual);
                extratoBancarioRepository.save(extratoBancario);

                log.info("DADOS PROCESSADOS");
                log.info("MOVIMENTAÇÃO EFETUADA COM SUCESSO");

                return null;
            });
        } catch (Exception e) {
            log.error("ERRO AO REALIZAR MOVIMENTAÇÃO ");
            log.warn("A MENSAGEM SERÁ ENVIADA PARA DEAD LETTER QUEUE ");
            throw new RuntimeException(String.format("Error: ", e.getMessage()));
        }

    }


}
