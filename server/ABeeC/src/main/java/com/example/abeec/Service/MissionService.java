package com.example.abeec.Service;

import com.example.abeec.dto.MissionResDto;
import com.example.abeec.dto.MyVocaDto;
import com.example.abeec.entity.Mission;
import com.example.abeec.entity.MissionVoca;
import com.example.abeec.entity.MyVoca;
import com.example.abeec.repository.MissionRepository;
import com.example.abeec.repository.MissionVocaRepository;
import com.example.abeec.repository.MyVocaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionVocaRepository missionVocaRepository;
    private final MyVocaRepository myVocaRepository;
    private final MissionRepository missionRepository;

    // 사용자가 수행해야할 미션의 리스트를 생성하여 반환할 메소드
    public MissionResDto getMissionList(String id) {
        MissionResDto missionResDto = new MissionResDto();

        //ArrayList<String> camera = getCameraList(id); //카메라로 촬영할 단어 리스트
        //ArrayList<String> listening = getListeningList(id); //들을 영단어 리스트
        ArrayList<String> camera = new ArrayList<>();
        ArrayList<String> listening = new ArrayList<>();

        Mission mission =  missionRepository.findById(id).get();

        String cameraS = mission.getCamera().substring(1,mission.getCamera().length()-1);


        for (String s : cameraS.split(",")) {
            camera.add(s);
        }
        if(mission.getListening()!=null) {
            String listeningS = mission.getListening().substring(1, mission.getListening().length()-1);
            for (String s : listeningS.split(",")) {
                listening.add(s);
            }
        }

        missionResDto.setCamera(camera);
        missionResDto.setListening(listening);



        return missionResDto;
    }

    public ArrayList<String> getListeningList(String id) {
        ArrayList<String> listening = new ArrayList<>();

        List<MyVoca> myVocas = myVocaRepository.findAllByUserId(id);

        if(myVocas.size() < 3) return listening;
        Random random = new Random();
        int number = 3;
        for(int i = 0;i<number;i++){
            int randomIndex = random.nextInt(myVocas.size());
            String english = myVocas.get(randomIndex).getEnglish();
            myVocas.remove(randomIndex);

            listening.add(english);
        }

        return listening;
    }

    public ArrayList<String> getCameraList(String id) {
        ArrayList<String> camera  = new ArrayList<>();



        while(camera.size()<7){
            String english = missionVocaRepository.findRandom().getEnglish();
            if(myVocaRepository.findAllByUserIdAndEnglish(id,english).isEmpty()){
                camera.add(english);
            }


        }

        return camera;

    }
}
