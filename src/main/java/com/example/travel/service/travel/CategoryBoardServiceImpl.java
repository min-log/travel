package com.example.travel.service.travel;

import com.example.travel.domain.Category;
import com.example.travel.domain.CategoryBoard;
import com.example.travel.domain.CategoryImage;
import com.example.travel.domain.Item;
import com.example.travel.dto.travel.CategoryBoardDTO;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.repository.travel.CategoryBoardRepository;
import com.example.travel.repository.travel.CategoryImageRepository;
import com.example.travel.repository.travel.CategoryRepository;
import com.example.travel.repository.travel.ItemRepository;
import com.example.travel.service.BoardContentFileService;
import com.example.travel.service.BoardFileService;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryBoardServiceImpl implements CategoryBoardService {

    final CategoryService categoryService;
    final CategoryBoardRepository categoryBoardRepository;
    final CategoryImageRepository categoryImageRepository;
    final BoardFileService boardFileService; // 이미지 파일 저장
    final BoardContentFileService boardContentFileService; // txt 파일 저장

    private String folderPath = "categoryBoard";


    @Override
    public CategoryBoardDTO createCategoryBoard(CategoryBoardDTO categoryBoardDTO, MultipartFile file) {
        log.info("CategoryBoard 저장 로직 --------------------");
        String title = categoryBoardDTO.getBoardTit();

        log.info("화면에서 가져온 객체 : {}",categoryBoardDTO);
        //파일 명으로 저장될 타이틀 공백 앞 뒤 제거 및 변경
        String img_title = title.trim().replace(" ", "_");
        categoryBoardDTO.setBoardTit(img_title);
        //태그 특수문자로 변경하여 저장
        String replace = getReplace(categoryBoardDTO.getBoardContent());
        categoryBoardDTO.setBoardContent(replace);

        // txt 파일 저장
        String contentSave = boardContentFileService.createBoardContent(categoryBoardDTO, folderPath);
        if (contentSave == null){
            log.info("컨텐츠 저장이 실패했습니다.");
            return null;
        }

        // 컨텐츠 저장
        categoryBoardDTO.setBoardTit(title);
        categoryBoardDTO.setBoardContent(contentSave);
        CategoryBoard categoryBoard = categoryBoardDtoToEntity(categoryBoardDTO);
        CategoryBoard save = categoryBoardRepository.save(categoryBoard);

        // 카테고리 업데이트
        log.info("categoryBoardDTO.getBoardCategoryNo() : {}",categoryBoardDTO.getBoardCategoryNo());
        CategoryDTO category = categoryService.getCategory(categoryBoardDTO.getBoardCategoryNo());
        category.setBoardExistence(true);
        categoryService.categoryUpdate(category);
        CategoryBoardDTO result = categoryBoardEntityToDto(save);

        if (file == null) {
            log.info("썸네일 없음");
            return result;
        }

        JsonObject categoryThumbnail = boardFileService.createImageThumbnail(file, "categoryThumbnail",save);
        if (categoryThumbnail == null) {
            // 썸네일 저장 되지 않고 오류 생길 경우 --저장된 게시글도 제거
            log.info("이미지 저장 실패");
            categoryBoardRepository.delete(save);
            return null;
        }
        log.info("이미지 저장 성공");
        String asString = categoryThumbnail.get("url").getAsString();
        result.setBoardImg(asString);

        return result;
    }


    @Override
    public CategoryBoardDTO getCategoryBoard(Long categoryNo,int dayNo) {
        Optional<CategoryBoard> board = categoryBoardRepository.getGategoryBoardVer(categoryNo,dayNo);
        if (board.isPresent()){
            CategoryBoard categoryBoard = board.get();
            String content = boardContentFileService.readBoardContent(folderPath, categoryBoard.getBoardContent());
            CategoryBoardDTO resultCategoryBoard = findResultCategoryBoard(categoryBoard);
            resultCategoryBoard.setBoardContent(content);

            return resultCategoryBoard;
        }else{
            log.info("찾는 게시물이 없습니다.");
            return null;
        }
    }

    @Override
    public boolean updateCategoryBoard(Long boardNo){
        Optional<CategoryBoard> entity = categoryBoardRepository.findById(boardNo);
        if (entity.isPresent()){
            log.info("업데이트 될 객체 : {}",boardNo);
            CategoryBoard categoryBoard = entity.get();
            CategoryBoardDTO categoryBoardDTO = categoryBoardEntityToDto(categoryBoard);
            // 1. 컨텐츠 txt파일 제거
            boardContentFileService.removeFile(folderPath,categoryBoard.getBoardContent());

            return true;
        }

        return false;
    }



    @Override
    public List<CategoryBoardDTO> getCategoryBoardList(Long categoryNo) {


        return null;
    }


    @Override
    public boolean deleteCategoryBoard(Long categoryNo, int dayNo) {
        log.info("카테고리 후기 제거 ------------");
        CategoryBoardDTO categoryBoardDTO = getCategoryBoard(categoryNo, dayNo);

        if (categoryBoardDTO == null){
            return false;
        }

        CategoryBoard categoryBoard = categoryBoardDtoToEntity(categoryBoardDTO);
        categoryBoardRepository.delete(categoryBoard);

        List<CategoryBoard> list = categoryBoardRepository.getCategoryBoardByBoardCategoryNo(categoryNo);
        if (list.isEmpty()){
            log.info("저장된 후기가 없다면");
            CategoryDTO category = categoryService.getCategory(categoryNo);
            category.setBoardExistence(false); // 카테고리 후기 존재 수정
            categoryService.categoryUpdate(category);
        }
        return true;
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
