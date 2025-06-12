package com.retail.exceptionhandler;

public class NoTransactionFoundException extends RuntimeException {
    public NoTransactionFoundException(String message) {
        super(message);
    }
}
