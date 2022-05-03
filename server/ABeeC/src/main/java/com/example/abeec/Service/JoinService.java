package com.example.abeec.Service;

import com.example.abeec.dto.UserDto;
import com.example.abeec.entity.Mission;
import com.example.abeec.entity.User;
import com.example.abeec.repository.MissionRepository;
import com.example.abeec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    @Autowired
    private EntityManager entityManager;


    //db에서 id가진 user 여부 확인
    @Transactional
    public int idCheck(String id){
        User user = userRepository.findById(id).orElse(null);
        if(user!=null){
            return -1;
        }else{
            return 1;
        }
    }


    //db에 user 저장( 회원가입 정보 db 기록 )
    public UserDto saveUser(UserDto userReq){
        User user = userReq.toEntity();
        userRepository.save(user);
        entityManager.clear(); //캐시 clear --> 데이터베이스와 캐시의 불일치 맞쳐주기
        User resultUser = userRepository.findById(user.getId()).get(); // 반환을 위해 저장한 엔티티 호출

        Mission mission = new Mission();
        mission.setUserId(user.getId());
        mission.setCamera("[apple,banana,pineapple,tissue,cup,tree,curtain,eraser,pencil case,mouse]");
        missionRepository.save(mission);

        return entityToDto(resultUser); // dto로 변환하여 반환
    }


    // user id 와 password 를 비교
    @Transactional
    public int loginCheck(String id,String password){
        User loginUser = userRepository.findById(id).orElse(null);
        if(loginUser!=null && loginUser.getPassword().equals(password)){
            return 1;
        }
        else{
            return -1;
        }

    }

    //해당 user의 정보를 반환
    public UserDto userInfo(String id){
        User user = userRepository.findById(id).orElse(null);
        return entityToDto(user);
    }


    public int updateTotalScore(String id,int totalScore){
        User user = userRepository.findById(id).get();
        user.setTotalScore(totalScore);
        User result = userRepository.save(user);
        if(Objects.equals(result.getId(), id))
            return 1;
        else
            return 0;
    }

    //entity -> dto
    private UserDto entityToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setAge(user.getAge());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        userDto.setLevel(user.getLevel());
        userDto.setTotalScore(user.getTotalScore());

        return userDto;
    }
}
