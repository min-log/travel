package com.example.travel.repository.travel;

import com.example.travel.domain.Category;
import com.example.travel.domain.Item;
import com.example.travel.dto.travel.ItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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
                .itemDay(0)
                .id("테스트")
                .placeName("테스트")
                .build();
        Item save = itemRepository.save(build);
        System.out.println(save);
    }

    @Test
    public void findItemList(){
        List<Item> itemList = itemRepository.findItemList(1,1L);
        for (int i=0;i<itemList.size();i++){
            Item item = itemList.get(i);
            System.out.println(item);
        }
    }


}