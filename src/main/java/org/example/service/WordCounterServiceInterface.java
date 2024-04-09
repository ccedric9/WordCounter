package org.example.service;

import org.example.exception.InvalidWordInputException;

import java.util.Map;

public interface WordCounterServiceInterface {

    public void addWords(String... words) throws InvalidWordInputException;

    public int getWordCount(String word);

    public Map<String,Integer> getAllWords();

}
