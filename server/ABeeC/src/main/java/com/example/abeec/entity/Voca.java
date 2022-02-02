package com.example.abeec.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Voca {

    @Id
    @GeneratedValue
    private Long id;

    private String english;

    private String korean;
}
