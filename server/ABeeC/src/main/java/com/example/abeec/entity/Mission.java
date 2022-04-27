package com.example.abeec.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
public class Mission {
    @Id
    private String userId;

    private String camera;

    private String listening;



}
