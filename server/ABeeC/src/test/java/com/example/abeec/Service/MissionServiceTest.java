package com.example.abeec.Service;

import com.example.abeec.dto.MissionResDto;
import org.junit.jupiter.api.Test;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MissionServiceTest {
    @Autowired
    private MissionService missionService;

   // @Test
    void listenigtest(){
        MissionResDto mission = missionService.getMissionList("yoojinjangjang");

        System.out.println(mission.getListening());
        System.out.println(mission.getCamera());
    }
}