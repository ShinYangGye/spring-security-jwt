package com.meta.advice.exception;

public class CTokenUnknownException extends RuntimeException {
    public CTokenUnknownException(String msg, Throwable t) {
        super(msg, t);
    }

    public CTokenUnknownException(String msg) {
        super(msg);
    }

    public CTokenUnknownException() {
        super();
    }
}
