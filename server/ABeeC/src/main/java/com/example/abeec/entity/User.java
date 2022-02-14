package com.example.abeec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@AllArgsConstructor
@DynamicInsert
public class User {
    public User(String id, String name, String password, int age, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.phone = phone;
    }

    @Id
    private String id;

    private String name;

    private String password;

    private int age;

    private Integer wordsCount;

    private Integer level;

    private String phone;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<MyVoca> myVocas;

}
