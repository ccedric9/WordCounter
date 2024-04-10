package org.example.service;


import org.example.dao.WordCounterDaoImpl;
import org.example.dao.WordCounterDaoInterface;
import org.example.exception.InvalidWordInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class WordCounterServiceImpl implements WordCounterServiceInterface{
    
    private WordCounterDaoImpl dao;
    private final TranslatorService translator;

    private final String WHITESPACE = "\\s+";
    private final String COMMA = ",";
    // allow user to add multiple words separated by COMMA or WHITESPACE
    private final String DELIMITER = "\\s*" + COMMA + "\\s*|" + WHITESPACE;
    @Autowired
    public WordCounterServiceImpl(WordCounterDaoImpl dao, TranslatorService translator) {
        this.dao = dao;
        this.translator = translator;
    }

    @Override
    //if any word is not correct, not adding any word and provide error info to user
    public void addWords(List<String> words) throws InvalidWordInputException {
        ArrayList<String> validWords = new ArrayList<>();
        for (String word : words){
            //Translate from other languages to English
            word = translator.translateToEnglish(word);
            if(isValidWord(word)){
            //make sure all words are lowercase as Map-Key is case-sensitive
                String str = word.toLowerCase();
                validWords.add(str);
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
            String str =  word.toLowerCase();
            return dao.getWordCount(str);
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
        if(word == null || word.equals("")) return false;
        for (char w: word.toCharArray()){
            if(!Character.isLetter(w)) {
                return false;
            }
        }
        return true;
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
