package com.example.travel.repository;

import com.example.travel.repository.member.UserImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserImageRepositoryTest {
    @Autowired
    UserImageRepository userImageRepository;


    @Test
    public void removeImage(){
//        Image byId = userImageRepository.getById(1L);
//        Long id = byId.getId();
//        System.out.println(id);
        int i = userImageRepository.deleteByUserImage(1L);
        //Image result = userImageRepository.getById(1L);
        System.out.println(i);


    }

}