package com.pblazaro.msdeposito;

import com.pblazaro.msdeposito.Application.DepositoBancoService;
import com.pblazaro.msdeposito.infra.exception.exceptions.ConxecaoRabbitMQException;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class MsdepositoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsdepositoApplication.class, args);
    }

}
