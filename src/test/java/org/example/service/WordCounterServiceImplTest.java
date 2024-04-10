package org.example.service;

import org.example.dao.WordCounterDaoImpl;
import org.example.dao.WordCounterDaoInterface;
import org.example.exception.InvalidWordInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WordCounterServiceImplTest {

    @InjectMocks
    private WordCounterDaoImpl dao ;

    @InjectMocks
    private TranslatorService translator ;
    @InjectMocks
    private WordCounterServiceImpl serviceTest;

    @BeforeEach
    void setUp() {
        serviceTest = new WordCounterServiceImpl(dao,translator);
    }

    @Test
    public void testServiceAddWordsThrows() throws InvalidWordInputException {
        //In service layer, invalid string is not allowed to add ,will throw error
        String[] arr1 = new String[]{"xyz","s_f999s"};
        assertThrows(InvalidWordInputException.class, () -> {
            serviceTest.addWords(Arrays.asList(arr1));
        });

        String[] arr2 = new String[]{"hell0"};
        assertThrows(InvalidWordInputException.class, () -> {
            serviceTest.addWords(Arrays.asList(arr2));
        });

        String[] arr3 = new String[]{""};
        assertThrows(InvalidWordInputException.class, () -> {
            serviceTest.addWords(Arrays.asList(arr3));
        });

        assertThrows(NullPointerException.class, () -> {
            serviceTest.addWords(Arrays.asList(null));
        });

        String[] arr4 = new String[]{"99&*^#@*@$&*&$","java","programmming ----"};
        assertThrows(InvalidWordInputException.class, () -> {
            serviceTest.addWords(Arrays.asList(arr4));
        });

    }
    @Test
    public void testServiceAddWords2() throws InvalidWordInputException {
        String word1 = "extremeeeelylongWORD";
        String word2 = "erextremeeeelylongWORD";
        serviceTest.addWords(Arrays.asList(word1,word1,word1,word2,word2));
        assertEquals(serviceTest.getAllWords().size(),2);
        String test = word1.toLowerCase();
        assertEquals(serviceTest.getWordCount(word1),3);
        assertEquals(serviceTest.getWordCount(word2),2);
        String word3 = "WorD";
        String word4 = "worD";
        String word5 = "word";
        serviceTest.addWords(Arrays.asList(word3,word4,word5,word5,word5));
        assertEquals(serviceTest.getWordCount(word3),5);
    }

    @Test
    public void testParseUserInput(){
        String userInput1 = "hello,    world, word,WWWORD    U*&*&NS47384";
        List<String> list1= serviceTest.parseWordsInput(userInput1);
        assertEquals(list1.size(),5);
        assertEquals(list1.get(4),"U*&*&NS47384");
        assertEquals(list1.get(1),"world");
        assertEquals(list1.get(3),"WWWORD");

        String userInput2 = ",    ORRd,WWWORD        extreemelylong ... ";
        List<String> list2 = serviceTest.parseWordsInput(userInput2);
        assertEquals(list2.size(),5);
        assertEquals(list2.get(0),"");
        assertEquals(list2.get(4),"...");
        assertEquals(list2.get(3),"extreemelylong");

        String userInput3 = ",,,,,,,,         ,   ,   ,  ";
        List<String> list3 = serviceTest.parseWordsInput(userInput3);        assertEquals(list2.size(),5);
        assertEquals(list3.size(),0);

    }

}

