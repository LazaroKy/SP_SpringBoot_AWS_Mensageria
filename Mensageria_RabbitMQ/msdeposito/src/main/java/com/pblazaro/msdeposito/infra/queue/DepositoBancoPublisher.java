package com.pblazaro.msdeposito.infra.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pblazaro.msdeposito.domain.DepositoBanco;
import com.pblazaro.msdeposito.infra.exception.exceptions.ConxecaoRabbitMQException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class DepositoBancoPublisher {


    private final RabbitTemplate rabbitTemplate;
    private final Queue queueTransacaoBanco;

    public void EnviandoDepositoParaEfetuarTransacao(DepositoBanco depositoBanco) throws JsonProcessingException {
        try {
            String json = convertIntoJson(depositoBanco);
            log.info("ENVIANDO MENSAGEM EM {}", queueTransacaoBanco.getName());
            rabbitTemplate.convertAndSend(queueTransacaoBanco.getName(), json);
        } catch (AmqpConnectException e) {
            log.error("NÃO FOI POSSÍVEL ENVIAR MENSAGEM EM {} - FALHA NA CONEXÃO COM RABBITMQ", queueTransacaoBanco.getName());
            log.warn("REGISTRO DE DEPÓSITO Nº: {} DESFEITO", depositoBanco.getIdDeposito());
            throw new ConxecaoRabbitMQException(String.format("Error: %s", e.getMessage()));
        }

    }

    //Necessario passar para JSon
    private String convertIntoJson(DepositoBanco depositoBanco) {
        try {
            log.info("CONVERTENDO MENSAGEM EM STRING");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(depositoBanco);
            return json;
        } catch (JsonProcessingException msg) {
            log.error("NÃO FOI POSSÍVEL CONVERTER A MENSAGEM EM STRING");
            throw new RuntimeException(String.format("Error: ", msg.getMessage()));
        }

    }

}
