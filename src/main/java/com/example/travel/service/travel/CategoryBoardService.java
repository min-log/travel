package com.example.travel.service.travel;

import com.example.travel.domain.CategoryBoard;
import com.example.travel.dto.travel.CategoryBoardDTO;
import org.springframework.web.multipart.MultipartFile;


public interface CategoryBoardService {

    public CategoryBoardDTO createCategoryBoard(CategoryBoardDTO categoryBoardDTO, MultipartFile file); // 저장

    public CategoryBoardDTO getCategoryBoard(Long boardNo); // 저장 된 카테고리 게시판 가져오기
    public CategoryBoardDTO getCagetgoryBoardPost(Long categoryNo,int dayNo); // 썸네일 이미지 찾기
    public CategoryBoardDTO getImgCategoryBoard(Long categoryNo); // 썸네일 이미지 찾기



    public boolean deleteAllCategoryBoard(Long categoryNo); //카테고리 관련 게시글 전체 삭제
    public boolean deleteCategoryBoard(Long categoryNo, int number); // 게시글 하나 삭제




    default CategoryBoardDTO categoryBoardEntityToDto(CategoryBoard board){
        CategoryBoardDTO categoryBoardDTO = CategoryBoardDTO.builder()
                .boardNo(board.getBoardNo())
                .boardCategoryNo(board.getBoardCategoryNo())
                .boardItemDay(board.getBoardItemDay())
                .boardTravelDate(board.getBoardTravelDate())
                .boardTit(board.getBoardTit())
                .boardContent(board.getBoardContent())
                .createdAt(board.getCreatedAt())
                .build();

        return categoryBoardDTO;
    }
    default CategoryBoard categoryBoardDtoToEntity(CategoryBoardDTO board){
        CategoryBoard categoryBoard = CategoryBoard.builder()
                .boardNo(board.getBoardNo())
                .boardCategoryNo(board.getBoardCategoryNo())
                .boardItemDay(board.getBoardItemDay())
                .boardTravelDate(board.getBoardTravelDate())
                .boardTit(board.getBoardTit())
                .boardContent(board.getBoardContent())
                .build();
        return categoryBoard;
    }

    
}
