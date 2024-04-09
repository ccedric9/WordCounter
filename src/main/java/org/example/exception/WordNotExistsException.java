package org.example.exception;

public class WordNotExistsException extends WordCounterException{
    public WordNotExistsException(String message) {
        super(message);
    }
}
