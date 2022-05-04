package com.toy.interpreter.exceptions;

public class ControllerException extends RuntimeException {
    public ControllerException(String error) {
        super(error);
    }
}
