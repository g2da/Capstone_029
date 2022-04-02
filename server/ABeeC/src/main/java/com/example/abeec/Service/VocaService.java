package com.example.abeec.Service;

import com.example.abeec.dto.MyVocaDto;
import com.example.abeec.entity.MyVoca;
import com.example.abeec.entity.User;
import com.example.abeec.repository.MyVocaRepository;
import com.example.abeec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VocaService {
    private final UserRepository userRepository;

    // getMyVoca 메소드
    // id를 받아서 해당 사용자의 단어장리스트를 반환하는 메소드
    public List<MyVocaDto> getMyVocas(String id){
        User user = userRepository.findById(id).orElse(null);
        List<MyVocaDto> myVocas = new ArrayList<>();
        if(user!=null){
            user.getMyVocas().forEach(myVoca->{
                MyVocaDto myVocaDto = new MyVocaDto();
                myVocaDto.setEnglish(myVoca.getEnglish());
                myVocaDto.setKorean(myVoca.getKorean());
                myVocaDto.setImage(myVoca.getImage());
                myVocas.add(myVocaDto);
            });
            return myVocas;
        }
        return null;
    }
}
