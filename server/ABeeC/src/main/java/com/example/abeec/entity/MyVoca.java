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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private String english;

    private String korean;

    // 이미지 저장 변수
    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] image;



}
