package com.example.abeec.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class MyVoca {

    @Id
    @GeneratedValue
    private Long id;


    private String userId;

    @ManyToOne
    private Voca voca;

    // 이미지 저장 변수
    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] image;
}
