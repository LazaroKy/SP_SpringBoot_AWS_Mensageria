package com.pblazaro.msdeposito.infra.exception;

import com.pblazaro.msdeposito.infra.exception.exceptions.ConxecaoRabbitMQException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorMessage> numberFormatException(NumberFormatException ex,
                                                              HttpServletRequest request) {
        log.error("Error detectado: ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Deve ser inserido um número válido"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> httpMessageNotReadableException(HttpMessageNotReadableException ex,
                                                                        HttpServletRequest request) {
        log.error("Error detectado: ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Dados inválidos, deve ser inserido dígitos"));
    }

    @ExceptionHandler(ConxecaoRabbitMQException.class)
    public ResponseEntity<ErrorMessage> amqpConnectException(ConxecaoRabbitMQException ex,
                                                             HttpServletRequest request) {
        log.error("Error detectado: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, "Falha na conexão com Rabbitmq"));
    }

}
