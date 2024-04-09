package org.example.service;


import org.example.dao.WordCounterDaoInterface;
import org.example.exception.InvalidWordInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class WordCounterServiceImpl implements WordCounterServiceInterface{
    
    private WordCounterDaoInterface dao;
    private final TranslatorService translator;

    private final String WHITESPACE = "\\s+";
    private final String COMMA = ",";
    // allow user to add multiple words separated by COMMA or WHITESPACE
    private final String DELIMITER = "\\s*" + COMMA + "\\s*|" + WHITESPACE;
    @Autowired
    public WordCounterServiceImpl(WordCounterDaoInterface dao, TranslatorService translator) {
        this.dao = dao;
        this.translator = translator;
    }

    @Override
    //if any word is not correct, not adding any word and provide error info to user
    public void addWords(List<String> words) throws InvalidWordInputException {
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
    public int getWordCount(String word) throws InvalidWordInputException{
        word = translator.translateToEnglish(word);
        if(isValidWord(word)){
            return dao.getWordCount(word.toLowerCase());
        }else{
            throw new InvalidWordInputException("ERROR: Word can only be alphabetic characters :" + word);
        }
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

    @Override
    public List<String> parseWordsInput(String input){
        List<String> wordsList = new ArrayList<>();
        String[] wordTokens =  input.split(DELIMITER);
        for (String word : wordTokens){
            wordsList.add(word);
        }
        return wordsList;
    }
}
