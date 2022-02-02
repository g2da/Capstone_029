package com.example.abeec.Service;

import com.example.abeec.dto.UserDto;
import com.example.abeec.entity.User;
import com.example.abeec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;

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
    @Transactional
    public UserDto saveUser(UserDto userReq){

        User user = userReq.toEntity();
        user.setLevel(1); // user 의 level 1로 초기화
        user.setWordsCount(0); //user 의 학습 개수 0으로 초기화
        User resultUser = userRepository.save(user);
        return entityToDto(resultUser);
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

    //entity -> dto
    private UserDto entityToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setAge(user.getAge());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        userDto.setLevel(user.getLevel());
        userDto.setWordsCount(user.getWordsCount());
        return userDto;
    }
}
