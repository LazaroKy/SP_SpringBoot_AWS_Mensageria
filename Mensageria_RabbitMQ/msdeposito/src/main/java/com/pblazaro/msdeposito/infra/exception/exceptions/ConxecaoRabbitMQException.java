package com.pblazaro.msdeposito.infra.exception.exceptions;

public class ConxecaoRabbitMQException extends RuntimeException {

    public ConxecaoRabbitMQException(String msg) {
        super(msg);
    }
}
