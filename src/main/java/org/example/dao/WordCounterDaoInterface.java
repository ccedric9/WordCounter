package org.example.dao;


import java.util.*;

public interface WordCounterDaoInterface {


    public Map<String,Integer> getAllWords();

    public int getWordCount(String word);

    public void addWords(List<String> words);
}
