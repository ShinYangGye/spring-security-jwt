package com.meta.advice.exception;

public class CTokenInvalidException extends RuntimeException {
    public CTokenInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public CTokenInvalidException(String msg) {
        super(msg);
    }

    public CTokenInvalidException() {
        super();
    }
}
