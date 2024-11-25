package com.pblazaro.msextratobancario.infra.exception.exceptions;

public class ExtratoBancarioNotFoundException extends RuntimeException {
    public ExtratoBancarioNotFoundException(String msg) {
        super(msg);
    }
}
