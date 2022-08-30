package com.meta.advice.exception;

public class CPartCodeNotExistException extends RuntimeException {
    public CPartCodeNotExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public CPartCodeNotExistException(String msg) {
        super(msg);
    }

    public CPartCodeNotExistException() {
        super();
    }
}
