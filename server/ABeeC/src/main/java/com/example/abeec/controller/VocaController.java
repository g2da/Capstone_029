package com.example.abeec.controller;

import com.example.abeec.Service.VocaService;
import com.example.abeec.dto.MyVocaDto;
import com.example.abeec.entity.MyVoca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/abeec")
public class VocaController {
    @Autowired
    private VocaService vocaService;


    /* 단어장 : localhost:8080/abeec/voca_list/{id} 의 get요청
    request : String id - path variable
    response : json 형태의 단어장 리스트(english,korean,image)
    해당 사용자의 id를 이용하여 학습한 단어의 리스트를 json형태로 반환한다.
    */
    @GetMapping("/voca_list/{id}")
    public ResponseEntity getVocaList(@PathVariable String id){
        List<MyVocaDto> myVocas = vocaService.getMyVocas(id);

        if(myVocas!=null)
            return ResponseEntity.status(HttpStatus.OK).body(myVocas);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
