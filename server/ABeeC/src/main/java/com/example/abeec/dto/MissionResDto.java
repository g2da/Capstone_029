package com.example.abeec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionResDto {
    private ArrayList<String> camera; // camera로 촬영할 영단어 리스트

    private ArrayList<String> listening; // 듣기 미션으로 할당될 영단어 리스트트


}
