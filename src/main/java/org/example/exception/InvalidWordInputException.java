package org.example.exception;

public class InvalidWordInputException extends WordCounterException{
    public InvalidWordInputException(String message) {
        super(message);
    }
}
