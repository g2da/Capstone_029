package com.example.abeec.repository;

import com.example.abeec.entity.MyVoca;
import com.example.abeec.entity.User;
import com.example.abeec.entity.Voca;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class MyVocaRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyVocaRepository myVocaRepository;

    @Autowired
    private VocaRepository vocaRepository;



   // @Test
    void dbTest(){
        Voca voca = new Voca();
        voca.setEnglish("banana");
        voca.setKorean("바나나");
        vocaRepository.save(voca);

        vocaRepository.findAll().forEach(System.out::println);
    }

 //   @Test
    @Transactional
    @Rollback(false) //test 롤백 막기
    void myvocaTest(){
        MyVoca myVoca = new MyVoca();
        myVoca.setEnglish("banana");
        myVoca.setKorean("바나나");
        myVoca.setUserId("yoojinjangjang");

        myVocaRepository.save(myVoca);

        MyVoca myVoca1  = new MyVoca();
        myVoca1.setEnglish("apple");
        myVoca1.setKorean("사과");
        myVoca1.setUserId("yoojinjangjang");
        myVocaRepository.save(myVoca1);




    }

   // @Test
    @Transactional
    void userTest(){
        User user = userRepository.findById("yoojinjangjang").get();
        user.getMyVocas().forEach(myVoca->{
            System.out.println(myVoca.getEnglish());
            System.out.println(myVoca.getKorean());

        });
    }


    @Test
    void test(){
        List<MyVoca> myVocaList = myVocaRepository.findAllByUserIdAndEnglish("yoojinjangjang","pencil");

        System.out.println(myVocaList);
    }


}
