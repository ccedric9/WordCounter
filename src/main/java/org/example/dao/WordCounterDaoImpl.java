package org.example.dao;


import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// dao provides easy manipulation of data, will leave business logics to be handled by service layer
@Repository
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
            // if word not exists, set value as 1 by default, otherwise, increment counts by 1
            allWords.put( word, allWords.getOrDefault(word,0)+1);
        }
    }

}
