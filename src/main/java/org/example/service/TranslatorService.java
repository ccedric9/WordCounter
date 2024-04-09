package org.example.service;

import org.springframework.stereotype.Repository;

@Repository
public class TranslatorService {

    //mock the external translator
    public String translateToEnglish(String word){
        return word;
    }
}
