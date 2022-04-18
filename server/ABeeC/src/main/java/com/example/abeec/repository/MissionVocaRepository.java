package com.example.abeec.repository;

import com.example.abeec.entity.MissionVoca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionVocaRepository extends JpaRepository<MissionVoca,Long> {

    @Query(value = "select * from mission_voca order by rand() limit 1",nativeQuery = true)
    MissionVoca findRandom();
}
