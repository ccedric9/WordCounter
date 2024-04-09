package org.example.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WordCounterDaoImplTest {

    @Mock
    private WordCounterDaoInterface daoTest;

    @BeforeEach
    void cleanUp(){
        daoTest = new WordCounterDaoImpl();
    }

    @Test
    public void testDaoAtBeginning(){
        assertEquals(0, daoTest.getAllWords().size());
        assertEquals(daoTest.getWordCount("anyword"),0);
        assertEquals(daoTest.getWordCount("x8"),0);
        assertEquals(daoTest.getWordCount("x()8.."),0);
    }

    @Test
    //as Dao is not responsible for logics, will add any string
    public void testDaoAddSingleWord(){
        List<String> list1 = new ArrayList<>();
        list1.add("X_SD");
        daoTest.addWords(list1);
        assertEquals(daoTest.getAllWords().size(),1);
        list1 = new ArrayList<>(Arrays.asList("..."));
        daoTest.addWords(list1);
        assertEquals(daoTest.getAllWords().size(),2);

        list1 = new ArrayList<>(Arrays.asList(""));
        daoTest.addWords(list1);
        list1 = new ArrayList<>(Arrays.asList("LIQUID"));
        daoTest.addWords(list1);
        list1 = new ArrayList<>(Arrays.asList("test a list of words"));
        daoTest.addWords(list1);
        assertEquals(daoTest.getAllWords().size(),5);
    }

    @Test
    //as Dao is not responsible for logics, will add any string
    public void testDaoAddWords(){
        List<String> list1 = new ArrayList<>();
        list1.add("X_SD");
        list1.add("");
        list1.add("xiKok");
        daoTest.addWords(list1);
        assertEquals(daoTest.getAllWords().size(),3);
        list1.add("999-2375");
        list1.add("extremely...long....Worrrrrrrrrrrrd");
        list1.add("//world");
        daoTest.addWords(list1);
        list1.add("//world");
        daoTest.addWords(list1);
        assertEquals(daoTest.getAllWords().size(),6);
    }

    @Test
    public void testGetWordCount(){
        String[] arr = new String[]{"long__", "W%^","" ,"NORMALLL","NORMALLL","NORMALLL","long__",">englis","java.util.ArrayList"};
        daoTest.addWords(Arrays.asList(arr));
        assertEquals(daoTest.getAllWords().size(),6);
        assertEquals(daoTest.getWordCount("long__"),2);
        assertEquals(daoTest.getWordCount("java.util.ArrayList"),1);
        assertEquals(daoTest.getWordCount("NORMALLL"),3);
        assertEquals(daoTest.getWordCount(">englis"),1);
        assertEquals(daoTest.getWordCount(""),1);
    }
}
