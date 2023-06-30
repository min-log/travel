package com.example.travel.controller.api;

import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.ItemDTO;
import com.example.travel.service.travel.CategoryService;
import com.example.travel.service.travel.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardApi {
    final CategoryService categoryService;
    final ItemService itemService;

    @GetMapping("/categoryTemList")
    public List<CategoryDTO> categoryTemList(@RequestParam("tem") Long temVel){

        List<CategoryDTO> categoryTemList = categoryService.getCategoryTemList(temVel);

        if(categoryTemList.size() != 0){
            log.info("임시저장된 게시물이 있으면 전달");
            log.info(categoryTemList);
        }

        return categoryTemList;
    }
    @GetMapping("/categoryDelete")
    public boolean categoryDelete(@RequestParam("no") Long no){
        log.info("카테고리 제거 ----------------------");
        boolean result = categoryService.categoryDelete(no);
        return result;
    }


    @PostMapping("/itemSave")
    public boolean itemSave(@ModelAttribute(value = "item") ItemDTO itemDTO){
        log.info("아이템 저장 -----------------");
        log.info(itemDTO);
        ItemDTO itemDTO1 = itemService.itemSave(itemDTO);
        if (itemDTO1 == null){
            return false;
        }

        return true;
    }

    @GetMapping("/itemList")
    public List<ItemDTO> itemList(@RequestParam("itemDay") int itemDay,@RequestParam("cateNo") Long category){
        log.info(itemDay);
        log.info(category);
        List<ItemDTO> itemDTOS = itemService.itemList(itemDay, category);
        log.info(itemDTOS);
        return itemDTOS;
    }


    @GetMapping("/itemGet")
    public ItemDTO itemGet(@RequestParam("no") Long no){
        log.info(no);
        ItemDTO itemDTO = itemService.itemGet(no);
        log.info("itemDTO : {}",itemDTO);
        return itemDTO;
    }

    @GetMapping("/itemDelete")
    public boolean itemDelete(@RequestParam("no") Long no){
        log.info("번호:{}",no);
        boolean result = itemService.itemDelete(no);
        
        return result;
    }


    @GetMapping("/StorageSave")
    public boolean itemStorageSave(@RequestParam("jsonData") String itemString){
        log.info("임시 저장--------------------- ");
        log.info(itemString);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<ItemDTO> itemList = objectMapper.readValue(itemString, new TypeReference<List<ItemDTO>>() {});
            itemService.itemListOrderBySet(itemList);
            for (ItemDTO item : itemList) {
                System.out.println("itemNo: " + item.getItemNo());
                System.out.println("itemNumber: " + item.getItemNumber());
                System.out.println();
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return true;
    }


}
