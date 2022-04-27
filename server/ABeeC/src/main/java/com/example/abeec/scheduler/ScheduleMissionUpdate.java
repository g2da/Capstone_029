package com.example.abeec.scheduler;

import com.example.abeec.Service.MissionService;
import com.example.abeec.entity.Mission;
import com.example.abeec.entity.User;
import com.example.abeec.repository.MissionRepository;
import com.example.abeec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log
public class ScheduleMissionUpdate {
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    MissionService missionService;
    @Autowired
    UserRepository userRepository;
    @Scheduled(cron = "0 0/10 * * * *") // 10 분 마다 실행 나중엔 cron="0 0 0 0/7 * *" 7일마다 실행
    public void updateMission(){
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            ArrayList<String> camera = missionService.getCameraList(user.getId());
            ArrayList<String> listening = missionService.getListeningList(user.getId());

            Mission mission = new Mission();
            mission.setUserId(user.getId());
            mission.setCamera(camera.toString());
            mission.setListening(listening.toString());

            missionRepository.save(mission);
        });

    }
}
