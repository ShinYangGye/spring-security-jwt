package com.meta.advice.exception;

public class CTokenCreationException extends RuntimeException {
    public CTokenCreationException(String msg, Throwable t) {
        super(msg, t);
    }

    public CTokenCreationException(String msg) {
        super(msg);
    }

    public CTokenCreationException() {
        super();
    }
}
