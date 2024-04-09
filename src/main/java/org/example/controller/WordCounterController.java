package org.example.controller;

import org.example.exception.InvalidWordInputException;
import org.example.service.WordCounterServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class WordCounterController {

    private WordCounterServiceInterface service;
    private final String RETURN_HOME_LINK = "<a href=/>Return To Home</a>";

    public WordCounterController(WordCounterServiceInterface service) {
        this.service = service;
    }

    @GetMapping("/words")
    public Map<String,Integer> getAllWords(){
        return service.getAllWords();
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addWords(@RequestParam("words") String input) {

    try {
        List<String> words = service.parseWordsInput(input);
        service.addWords(words);
        String htmlContent = "<p>Added Successfully</p>" +
                RETURN_HOME_LINK;
        return new ResponseEntity<>(htmlContent, HttpStatus.OK);
    } catch (InvalidWordInputException e) {
        return new ResponseEntity<>("<p>"+e.getMessage() +"</p>" + RETURN_HOME_LINK, HttpStatus.OK);
    }
}

    @GetMapping("/count/{word}")
    public ResponseEntity<String> getWordCount(@PathVariable String word) {
        try {
            int count = service.getWordCount(word);
            String htmlContent = "<h1>Word Count for \"" + word + "\"</h1>"
                    + "<p>The count is: " + count + "</p>" +
                    RETURN_HOME_LINK;
            return new ResponseEntity<>(htmlContent, HttpStatus.OK);
        } catch (InvalidWordInputException e) {
           return new ResponseEntity<>(e.getMessage()+"\n"+RETURN_HOME_LINK,HttpStatus.OK);
        }

    }
}
