package org.example.controller;

import org.example.exception.InvalidWordInputException;
import org.example.service.WordCounterServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WordCounterController {

    private WordCounterServiceInterface service;

    public WordCounterController(WordCounterServiceInterface service) {
        this.service = service;
    }

    @GetMapping("/words")
    public Map<String,Integer> getAllWords(){
        return service.getAllWords();
    }

    @GetMapping("/addwords")
    public void addWords(String... words){
        try{
            service.addWords(words);
        } catch (InvalidWordInputException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/word")
    public int getWordCount(String word){
        return service.getWordCount(word);
    }
}
