package com.example.travel.service.travel;

import com.example.travel.domain.CategoryBoard;
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

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryBoardServiceImpl implements CategoryBoardService {
    final CategoryBoardRepository categoryBoardRepository;
    final BoardFileService boardFileService;



    @Override
    public CategoryBoardDTO createCategoryBoard(CategoryBoardDTO categoryBoardDTO, MultipartFile file) {
        log.info("저장 로직 --------------------");
        CategoryBoard categoryBoard = categoryBoardDtoToEntity(categoryBoardDTO);
        log.info("저장 하려는 객체 값 : {}",categoryBoard );


        CategoryBoard save = categoryBoardRepository.save(categoryBoard);

        log.info("저장된 객체 : {}",save );

        CategoryBoardDTO result = categoryBoardEntityToDto(save);

        if (file == null) {
            log.info("썸네일 없음");
            return result;
        }

        Long categoryNo = save.getCategoryNo();
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
}
