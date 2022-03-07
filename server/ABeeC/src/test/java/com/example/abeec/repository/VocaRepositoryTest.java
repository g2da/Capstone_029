package com.example.abeec.repository;

import com.example.abeec.VocaDBInsert;
import com.example.abeec.entity.Voca;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.sql.SQLOutput;
import java.util.ArrayList;

@SpringBootTest
public class VocaRepositoryTest {

    @Autowired
    private VocaRepository vocaRepository;



   // @Test
    @Transactional
    @Rollback(false)
    void insertVocaDB(){
        ArrayList<String> vocaList = VocaDBInsert.vocaDB();
        ArrayList<String> eng = new ArrayList<>();
        ArrayList<String> kor = new ArrayList<>();
        vocaList.forEach(str->{
            String[] module = str.split(",");
            eng.add(module[0]);
            kor.add(module[1]);

        });

        ArrayList<Voca> vocas = new ArrayList<>();
        for(int i = 0 ; i< 1000; i++){
            Voca voca = new Voca();
            voca.setKorean(kor.get(i));
            voca.setEnglish(eng.get(i));
            vocas.add(voca);
        }

        vocaRepository.saveAll(vocas);


    }


}
