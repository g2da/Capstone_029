package com.example.abeec.controller;

import com.example.abeec.Service.JoinService;
import com.example.abeec.dto.UserDto;
import com.example.abeec.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.http.HttpRequest;
@Validated
@RestController
@RequestMapping("/abeec")
public class JoinController {

    @Autowired
    private JoinService joinService;



    /* ID 중복검사 : localhost:8080/abeec/join/{id}의 get요청
    request : String 형태의 id - path variable
    response : 중복시 "another id is required", 중복이 아닐시 id
     id 의 정보를 db에서 검사한 후 있을 경우와 없는 경우를 확인한다.
    */
    @GetMapping("/join/{id}")
    public String idCheck(@PathVariable String id){
        if(joinService.idCheck(id)==1){
            return id;
        }else{
            return "another id is required";
        }
    }



    /*  회원가입 : localhost:8080/abeec/join 의 post요청
    request : json 형태의 user 정보 ( id,password,age,phone,name )
    response: json 형태의 user 정보 ( id,password,age,phone,name,level,words_count )
    user정보를 db에 저장한후 level과 words_count를 포함한 user 정보를 json형태로 반환한다.
     */
    @PostMapping("/join")
    public ResponseEntity join(@Valid @RequestBody UserDto userReq, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ //validation 오류에 대한 설명을 표시해주기 위한 부분
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(error->{
                FieldError field = (FieldError) error;
                String message = error.getDefaultMessage();

                sb.append("field : ").append(field.getField()).append("\n");
                sb.append("message : ").append(message);

            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb);
        }

        var result = joinService.saveUser(userReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }




    /* 로그인 : localhost:8080/abeec/login?id=''&password='' 의 get요청
    request : String id 와 String password - query parameter
    response : id와 password 일치시 - 1 , 불일치시 - -1
    넘어온 id값으로 해당 user를 찾은 후 password를 비교하여 로그인 가능여부를 반환한다.
    */
    @GetMapping("/login")
    public int login(
                    @RequestParam  @NotNull String id,
                     @RequestParam String password
                    ){
        System.out.println(id);
        return joinService.loginCheck(id,password);
    }

}
