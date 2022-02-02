package com.example.abeec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Data
@Entity
@AllArgsConstructor
public class User {
    @Id
    private String id;

    private String name;

    private String password;

    private int age;

    private Integer wordsCount;

    private Integer level;

    private String phone;



}
