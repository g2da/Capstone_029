package com.example.abeec.repository;

import com.example.abeec.entity.Voca;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MissionVocaRepositoryTest {

    @Autowired
    private MissionVocaRepository missionVocaRepository;

    @Autowired
    private VocaRepository vocaRepository;

    @Test
    @Transactional
    void test(){

    }
}