package com.example.travel.service.travel;

import com.example.travel.dto.travel.ItemDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceImplTest {
    @Autowired
    ItemService itemService;

    @Test
    @DisplayName("아이템 저장하기")
    public void save(){
        ItemDTO build = ItemDTO.builder()
                .categoryId(40L)
                .itemInfo("item.getItemInfo()")
                .itemTime("item.getItemTime()")
                .id("item.getId()")
                .placeName("item.getPlaceName()")
                .placeTime("item.getPlaceTime()")
                .placeUrl("item.getPlaceUrl()")
                .phone("item.getPhone()")
                .addressName("item.getAddressName()")
                .roadAddressName("item.getRoadAddressName()")
                .x("item.getX()")
                .y("item.getY()")
                .distance("item.getDistance()")
                .build();

        ItemDTO itemDTO = itemService.itemSave(build);
        System.out.println(itemDTO);
    }


}