package com.example.abeec.Service;

import com.example.abeec.dto.UserDto;
import com.example.abeec.entity.User;
import com.example.abeec.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class JoinServiceTest {

    @Autowired
    private JoinService joinService;
    @Autowired
    private UserRepository userRepository;

/*
    void test(){
        UserDto user = new UserDto();
        user.setName("yoojin");
        user.setAge(25);
        user.setId("yojjo");
        user.setPassword("9801984");
        user.setPhone("010321213");

        System.out.println(user);

        var result = joinService.saveUser(user);

        System.out.println(result);
    }
*/

   /* @Test
    void logintest(){
      //  var result = joinService.loginCheck(null,"980415");
      //  System.out.println(result);
    }*/
}