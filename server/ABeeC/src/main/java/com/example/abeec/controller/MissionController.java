package com.example.abeec.controller;

import com.example.abeec.Service.MissionService;
import com.example.abeec.dto.MissionResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abeec")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @GetMapping("/mission/{id}")
    MissionResDto getMission(@PathVariable String id){
        MissionResDto missionResDto = missionService.getMissionList(id);



        return missionResDto;
    }
}
