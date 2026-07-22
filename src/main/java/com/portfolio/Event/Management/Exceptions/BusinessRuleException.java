package com.portfolio.Event.Management.Exceptions;

public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException (String message){
        super(message);
    }
}
