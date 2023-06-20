package com.example.travel.repository;

import com.example.travel.domain.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserImageRepositoryTest {
    @Autowired
    UserImageRepository userImageRepository;


    @Test
    public void removeImage(){
//        Image byId = userImageRepository.getById(1L);
//        Long id = byId.getId();
//        System.out.println(id);
        boolean b = userImageRepository.deleteByUserImage(1L);
        //Image result = userImageRepository.getById(1L);
        System.out.println(b);


    }

}