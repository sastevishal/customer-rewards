package com.retail.exceptionhandler;

public class NoCustomerFoundException extends RuntimeException{
    public NoCustomerFoundException(String message) {
        super(message);
    }
}
