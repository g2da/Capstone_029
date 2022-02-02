package com.example.abeec.dto;

import com.example.abeec.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class UserDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min=4,max=15)
    private String password;

    @NotNull
    @Max(100)
    private int age;

    @NotBlank
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$",message = "전화번호 형식과 맞아야 합니다.")
    private String phone;

    private Integer wordsCount;

    private Integer level;

    public User toEntity(){ //dto -> entity
        return new User(id,name,password,age,wordsCount,level,phone);
    }

}
