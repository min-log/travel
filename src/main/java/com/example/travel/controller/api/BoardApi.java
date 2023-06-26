package com.example.travel.controller.api;

import com.example.travel.domain.UserTravel;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.ItemDTO;
import com.example.travel.dto.user.UserDTO;
import com.example.travel.service.travel.CategoryService;
import com.example.travel.service.travel.ItemService;
import com.example.travel.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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



}
