package com.pblazaro.msdeposito.Application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pblazaro.msdeposito.domain.DepositoBanco;
import com.pblazaro.msdeposito.domain.model.DadosParaDeposito;
import com.pblazaro.msdeposito.domain.model.StatusDeposito;
import com.pblazaro.msdeposito.infra.exception.exceptions.ConxecaoRabbitMQException;
import com.pblazaro.msdeposito.infra.queue.DepositoBancoPublisher;
import com.pblazaro.msdeposito.infra.repository.DepositoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Log4j2
@RequiredArgsConstructor
public class DepositoBancoService {

    private final DepositoBancoPublisher depositoBancoPublisher;
    private final DepositoRepository depositoRepository;

    public static boolean verificandoConexaoRabbitmq(RabbitTemplate rabbitTemplate){
        return rabbitTemplate.isRunning();
    }

    @Transactional
    public DepositoBanco efetuarDepositoBancario(DadosParaDeposito dadosDoDeposito) {
        try {
            DepositoBanco depositoBanco = new DepositoBanco();
            depositoBanco.setIdContaCliente(dadosDoDeposito.getIdContaCliente());
            depositoBanco.setValorDeposito(dadosDoDeposito.getValorDeposito());

            BigDecimal valorDoDeposito = dadosDoDeposito.getValorDeposito();
            if (depositoValido(valorDoDeposito) == false) {
                log.warn("PROCESSANDO DEPÓSITO INVÁLIDO");
                depositoBanco.setStatus(StatusDeposito.ERRO);
            } else {
                log.warn("PROCESSANDO DEPÓSITO VÁLIDO");
                depositoBanco.setStatus(StatusDeposito.DEPOSITADO);
            }

            depositoRepository.save(depositoBanco);
            log.info("DEPÓSITO DE Nº: {} REGISTRADO!", depositoBanco.getIdDeposito());

            depositoBancoPublisher.EnviandoDepositoParaEfetuarTransacao(depositoBanco);
            log.info("MENSAGEM ENVIADA COM SUCESSO - DEPÓSIDO ENVIADO PARA EXTRATO");
            return depositoBanco;
        } catch (NumberFormatException e) {
            log.error("FALHA AO REALIZAR DEPOSITO");
            throw new NumberFormatException();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("NÃO FOI POSSÍVEL CONVERTER PARA JSON");
        }
    }

    public boolean depositoValido(BigDecimal valorDeposito) {
        String stringValor = String.valueOf(valorDeposito);
        Double doubleValor = Double.parseDouble(stringValor);
        if (doubleValor <= 0) {
            return false;
        }
        return true;
    }


}
