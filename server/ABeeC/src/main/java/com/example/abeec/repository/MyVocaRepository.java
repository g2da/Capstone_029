package com.example.abeec.repository;

import com.example.abeec.entity.MyVoca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface MyVocaRepository extends JpaRepository<MyVoca,Long> {

    List<MyVoca> findAllByUserId(String userId);

    List<MyVoca> findAllByUserIdAndEnglish(String userId,String english);
}
