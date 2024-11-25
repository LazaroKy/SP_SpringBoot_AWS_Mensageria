package com.pblazaro.msdeposito;

import com.pblazaro.msdeposito.Application.DepositoBancoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class MsdepositoApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(MsdepositoApplication.class, args);
    }
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public void run(String... args) throws Exception {
        if (DepositoBancoService.verificandoConexaoRabbitmq(rabbitTemplate)){
            log.info("CONEXÃO COM RABBITMQ ESTABELICIDA - PRONTO PARA ENVIO DE MENSAGEM");
        } else{
            log.warn("NECESSÁRIO ESTABELECER CONXEÇÃO COM RABBITMQ");
        }
    }


}
