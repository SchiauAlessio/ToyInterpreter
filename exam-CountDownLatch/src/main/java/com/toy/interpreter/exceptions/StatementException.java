package com.toy.interpreter.exceptions;

public class StatementException extends RuntimeException {
    public StatementException(String error) {
        super(error);
    }
}
