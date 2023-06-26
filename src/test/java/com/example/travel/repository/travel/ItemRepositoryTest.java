package com.example.travel.repository.travel;

import com.example.travel.domain.Category;
import com.example.travel.domain.Item;
import com.example.travel.dto.travel.ItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    @Test
    public void save(){
        Category category = Category.builder().categoryNo(40L).build();
        Item build = Item.builder().category(category)
                .itemContent("아이템 저장 테스트")
                .itemDay(0)
                .id("테스트")
                .placeName("테스트")
                .build();
        Item save = itemRepository.save(build);
        System.out.println(save);
    }


}