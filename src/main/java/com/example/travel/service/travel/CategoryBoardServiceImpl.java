package com.example.travel.service.travel;

import com.example.travel.domain.CategoryBoard;
import com.example.travel.domain.CategoryImage;
import com.example.travel.dto.travel.CategoryBoardDTO;
import com.example.travel.repository.travel.CategoryBoardRepository;
import com.example.travel.repository.travel.CategoryImageRepository;
import com.example.travel.service.BoardFileService;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryBoardServiceImpl implements CategoryBoardService {
    final CategoryBoardRepository categoryBoardRepository;
    final CategoryImageRepository categoryImageRepository;
    final BoardFileService boardFileService;



    @Override
    public CategoryBoardDTO createCategoryBoard(CategoryBoardDTO categoryBoardDTO, MultipartFile file) {
        log.info("저장 로직 --------------------");

        //태그 특수문자로 변경하여 저장
        String replace = getReplace(categoryBoardDTO.getBoardContent());
        log.info("replace : "+replace);
        categoryBoardDTO.setBoardContent(replace);

        CategoryBoard categoryBoard = categoryBoardDtoToEntity(categoryBoardDTO);



        CategoryBoard save = categoryBoardRepository.save(categoryBoard);

        log.info("저장된 객체 : {}",save );

        CategoryBoardDTO result = categoryBoardEntityToDto(save);

        if (file == null) {
            log.info("썸네일 없음");
            return result;
        }

        Long categoryNo = save.getBoardCategoryNo();
        JsonObject categoryThumbnail = boardFileService.createImageThumbnail(file, "categoryThumbnail",save);


        if (categoryThumbnail == null) {
            // 썸네일 저장 되지 않고 오류 생길 경우 --저장된 게시글도 제거
            log.info("이미지 저장 실패");
            categoryBoardRepository.delete(save);
            return null;
        }



        log.info("이미지 저장 성공");
        String asString = categoryThumbnail.get("url").getAsString();
        log.info("저장된 이미지 주소 2 : {}", asString);
        result.setBoardImg(asString);

        log.info("이미지 저장 성공 객체 : {}",result );
        return result;
    }

    @Override
    public CategoryBoardDTO getCategoryBoard(Long boardNo) {
        log.info("저장 한 결과 가져가기 -------------------------");
        Optional<CategoryBoard> board = categoryBoardRepository.findById(boardNo);
        if (board.isPresent()){
            CategoryBoard categoryBoard = board.get();
            CategoryBoardDTO resultCategoryBoard = findResultCategoryBoard(categoryBoard);
            return resultCategoryBoard;
        }else{
            log.info("찾는 게시물이 없습니다.");
            return null;
        }
    }


    @Override
    public CategoryBoardDTO getCagetgoryBoardCNo(Long categoryNo) {
        Optional<CategoryBoard> board = categoryBoardRepository.getCategoryBoardByBoardCategoryNo(categoryNo);
        if (board.isPresent()){
            CategoryBoard categoryBoard = board.get();
            CategoryBoardDTO resultCategoryBoard = findResultCategoryBoard(categoryBoard);
            return resultCategoryBoard;
        }else{
            log.info("찾는 게시물이 없습니다.");
            return null;
        }
    }

    @Override
    public CategoryBoardDTO getImgCategoryBoard(Long categoryNo) {
        return null;
    }

    @Override
    public boolean deleteAllCategoryBoard(Long categoryNo) {
        return false;
    }

    @Override
    public boolean deleteCategoryBoard(Long categoryNo, int number) {
        return false;
    }

    //저장 결과 전달
    CategoryBoardDTO findResultCategoryBoard(CategoryBoard categoryBoard){
        CategoryBoardDTO categoryBoardDTO = categoryBoardEntityToDto(categoryBoard);
        if (categoryBoardDTO != null) {
            CategoryBoard categoryBoard1 = categoryBoardDtoToEntity(categoryBoardDTO);
            Optional<CategoryImage> img = categoryImageRepository.getCategoryImageByBoardNo(categoryBoard1);
            if (img.isPresent()){
                CategoryImage categoryImage = img.get();
                log.info(categoryImage);
                categoryBoardDTO.setBoardImg("/upload/" + categoryImage.getPath() + "/"+categoryImage.getThumbnailName());
            }
        }
        return categoryBoardDTO;
    }

    //마크업 변경
    public static String getReplace(String srcString) {
        String rtnStr = null;
        try{
            StringBuffer strTxt = new StringBuffer("");
            char chrBuff;
            int len = srcString.length();
            for(int i = 0; i < len; i++) {
                chrBuff = (char)srcString.charAt(i);
                switch(chrBuff) {
                    case '<':
                        strTxt.append("&lt;");
                        break;
                    case '>':
                        strTxt.append("&gt;");
                        break;
                    case '&':
                        strTxt.append("&amp;");
                        break;
                    default:
                        strTxt.append(chrBuff);
                }
            }


            rtnStr = strTxt.toString();

        }catch(Exception e) {

            e.printStackTrace();

        }


        return rtnStr;

    }

}
