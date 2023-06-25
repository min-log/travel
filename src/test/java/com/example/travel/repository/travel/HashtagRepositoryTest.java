package com.example.travel.repository.travel;

import com.example.travel.domain.Hashtag;
import com.example.travel.domain.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HashtagRepositoryTest {
    @Autowired
    HashtagRepository hashtagRepository;
    @Autowired
    TagRepository tagRepository;

    @Test
    @DisplayName("해쉬태그 리스트 가져오기")
    public void test1(){
        Optional<Hashtag> hashtag = hashtagRepository.findById(3L);
        System.out.println(hashtag);
    }
    @Test
    @DisplayName("해쉬태그 제거")
    @Transactional
    public void test(){
        Optional<Hashtag> hashtag = hashtagRepository.findById(2L);
        System.out.println("Optional hashtag : "+ hashtag);

        if (hashtag.isPresent()){
            Hashtag hashtag1 = hashtag.get();
            System.out.println("hashtag1 : "+ hashtag1);
            Long categoryId = hashtag1.getCategoryId();

            List<Tag> tag = hashtag1.getTag();
            if (!tag.isEmpty()){
                for(int i=0;i<tag.size();i++){
                    Tag tag1 = tag.get(i);
                    int t = hashtagRepository.deleteByHashIdAndTag(tag1.getId());
                    System.out.println(t);
                    tagRepository.delete(tag1);
                }
            }

            hashtagRepository.delete(hashtag1);

        }
    }
}