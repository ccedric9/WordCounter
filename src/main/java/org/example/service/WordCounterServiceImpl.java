package org.example.service;


import org.example.dao.WordCounterDaoInterface;
import org.example.exception.InvalidWordInputException;

import java.util.ArrayList;
import java.util.Map;

public class WordCounterServiceImpl implements WordCounterServiceInterface{
    
    private WordCounterDaoInterface dao;
    private final TranslatorService translator;

    public WordCounterServiceImpl(WordCounterDaoInterface dao, TranslatorService translator) {
        this.dao = dao;
        this.translator = translator;
    }

    @Override
    //if any word is not correct, not adding any word and provide error info to user
    public void addWords(String... words) throws InvalidWordInputException {
        ArrayList<String> validWords = new ArrayList<>();
        for (String word : words){
            word = translator.translateToEnglish(word);
            if(isValidWord(word)){
                word.toLowerCase();
                validWords.add(word);
            }else{
                throw new InvalidWordInputException("ERROR: Word can only be alphabetic characters :" + word);
            }
        }
        //if successful, use service to add all words to dao
        dao.addWords(validWords);
    }

    @Override
    public int getWordCount(String word) {
        word = translator.translateToEnglish(word);
        if(isValidWord(word)){
            return dao.getWordCount(word.toLowerCase());
        }
        return 0;
    }

    @Override
    public Map<String, Integer> getAllWords() {
        return dao.getAllWords();
    }


    //helper -- check if word input only contains alphabetic characters
    private boolean isValidWord(String word){
        String regex = "^[a-zA-Z]+$";
        return word.matches(regex);
    }
}
