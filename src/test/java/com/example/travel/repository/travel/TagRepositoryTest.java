package com.example.travel.repository.travel;

import com.example.travel.domain.Tag;
import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class TagRepositoryTest {
    @Autowired
    TagRepository tagRepository;

    @Test
    public void findTag(){
        List<Tag> tagByName = tagRepository.findTagByName("minlog.0430@gmail.com");
        if (!tagByName.isEmpty()){
            for (int i=0;i<tagByName.size();i++){
                System.out.println(tagByName.get(i).getName());
            }

        }
    }


}