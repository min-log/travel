package com.example.travel.controller.api;

import com.example.travel.domain.UserImage;
import com.example.travel.dto.travel.CategoryBoardDTO;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.CommentsDTO;
import com.example.travel.dto.travel.ItemDTO;
import com.example.travel.security.dto.UserTravelDTO;
import com.example.travel.service.travel.CategoryBoardService;
import com.example.travel.service.travel.CategoryService;
import com.example.travel.service.travel.CommentsService;
import com.example.travel.service.travel.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/boardApi")
public class BoardApi {
    final CategoryService categoryService;
    final CategoryBoardService categoryBoardService;
    final CommentsService commentsService;
    final ItemService itemService;


    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    @GetMapping("/boardImg")
    public ResponseEntity<byte[]> boardImgGet(@RequestParam(value = "url") String url){
        log.info("board 이미지 불러오기");

        ResponseEntity<byte[]> result = null;
        log.info("board images : {}",url);

        File file = new File(uploadPath +  File.separator + url);

        try{
            HttpHeaders headers = new HttpHeaders();
            //MIME타입 처리
            log.info("file --------------------------------");
            headers.add("Content-Type" , Files.probeContentType(file.toPath()));
            // 파일 데이터처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
            return result;
        }catch ( Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


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
        log.info("가져온 아이템 리스트 : {}",itemDTOS);
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
        log.info("리스트 저장--------------------- ");
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




    @GetMapping("/StorageTotalSave")
    public boolean itemStorageTotalSave(@RequestParam("jsonData") String itemString,
                                        @RequestParam("categoryNo") long categoryNo){
        log.info("리스트 및 카테고리 저장--------------------- ");
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

        boolean result = categoryService.categoryTotalSave(categoryNo);
        return result;
    }


    @GetMapping("/likeSave")
    public int categoryLike(
            @RequestParam("no") Long no,
            HttpSession httpSession
    ){
        log.info("카테고리 좋아요 -----------------");
        UserTravelDTO userT = (UserTravelDTO)httpSession.getAttribute("userT");
        if (userT == null){ //유저가 있을때만
            return -1;
        }

        boolean result = categoryService.categoryLike(no, userT.getUserNo());
        log.info("전달할 값 : " + result);

        if (result){
            return 1;
        }else {
            return 0;
        }

    }



    @PostMapping("/postSave")
    public ResponseEntity<CategoryBoardDTO> postWriterSave(@RequestPart(value = "itemCon") CategoryBoardDTO categoryBoardDTO,
                                         @RequestPart(value = "boardFile", required = false) MultipartFile boardFile
    ){
        log.info("게시판 생성 =================================");
        log.info("categoryBoardDTO : {}",categoryBoardDTO);
        log.info("boardFile : {}",boardFile);

        CategoryBoardDTO result = categoryBoardService.createCategoryBoard(categoryBoardDTO, boardFile);
        if (result == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/postDelete")
    public Boolean postDelete(
            @RequestParam(value = "no") long no,
            @RequestParam(value = "day") int dayNo
    ){
        boolean result = categoryBoardService.deleteCategoryBoard(no, dayNo);
        return result;
    }



    @GetMapping("/commList")
    public List<CommentsDTO> commentList(@RequestParam("no") Long no){
        log.info("댓글 가져오기----------------");
        List<CommentsDTO> list = new ArrayList<>();
        list = commentsService.getCommentsList(no);
        return list;
    }


    @PostMapping("/commSave")
    public CommentsDTO commentSave(@ModelAttribute(value = "comment") CommentsDTO commentsDTO){
        log.info("댓글 저장----------------");
        log.info("commentsDTO : {}",commentsDTO);
        CommentsDTO comments = commentsService.createComments(commentsDTO);
        if (comments == null) return null;
        return comments;
    }



    @GetMapping("/commDelete")
    public Long commentDelete(@RequestParam(value = "no") Long commNo){
        log.info("댓글 삭제----------------");
        Long no = commentsService.deleteComments(commNo);
        if (no == null) return null;
        return no;
    }


}
