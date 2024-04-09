package org.example.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounterDaoImpl implements WordCounterDaoInterface{

    private Map<String, Integer> allWords = new HashMap<>();
    @Override
    public Map<String, Integer> getAllWords() {
        return allWords;
    }

    @Override
    public int getWordCount(String word) {
        return allWords.get(word);
    }

    @Override
    public void addWords(List<String> words) {
        for (String word : words){
            allWords.put( word, allWords.getOrDefault(word,0)+1);
        }
    }

}
