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

    public static boolean verificandoConexaoRabbitmq(RabbitTemplate rabbitTemplate){
        return rabbitTemplate.isRunning();
    }


}
