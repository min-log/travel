package com.example.travel.service.travel;

import com.example.travel.domain.Category;
import com.example.travel.domain.Item;
import com.example.travel.dto.travel.ItemDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ItemService {

    ItemDTO itemSave(ItemDTO itemDTO);
    List<ItemDTO> itemList(int itemDay,Long categoryNo);
    ItemDTO itemGet(Long itemNo);
    boolean itemDelete(Long itemNo);

    //아이템 리스트 순서 변경
    void itemListOrderBySet(List<ItemDTO> itemList);


    // entity <-> dto
    default Item itemDtoToEntity(ItemDTO item){

        Category category = Category.builder().categoryNo(item.getCategoryId()).build();
        Item result = Item.builder()
                .category(category)
                .itemNo(item.getItemNo())
                .itemDay(item.getItemDay())
                .itemDayName(item.getItemDayName())
                .itemNumber(item.getItemNumber())
                .itemAccount(item.getItemAccount())
                .itemInfo(item.getItemInfo())
                .itemTime(item.getItemTime())
                .id(item.getId())
                .placeName(item.getPlaceName())
                .placeTime(item.getPlaceTime())
                .placeUrl(item.getPlaceUrl())
                .phone(item.getPhone())
                .addressName(item.getAddressName())
                .roadAddressName(item.getRoadAddressName())
                .x(item.getX())
                .y(item.getY())
                .distance(item.getDistance())
                .build();
        System.out.println(result);
        return result;
    }


    default ItemDTO itemEntityToDto(Item item){

        ItemDTO result = ItemDTO.builder()
                .categoryId(item.getCategory().getCategoryNo())
                .itemNo(item.getItemNo())
                .itemDay(item.getItemDay())
                .itemDayName(item.getItemDayName())
                .itemNumber(item.getItemNumber())
                .itemAccount(item.getItemAccount())
                .itemInfo(item.getItemInfo())
                .itemTime(item.getItemTime())
                .id(item.getId())
                .placeName(item.getPlaceName())
                .placeTime(item.getPlaceTime())
                .placeUrl(item.getPlaceUrl())
                .phone(item.getPhone())
                .addressName(item.getAddressName())
                .roadAddressName(item.getRoadAddressName())
                .x(item.getX())
                .y(item.getY())
                .distance(item.getDistance())
                .build();

        return result;
    }
    
}
