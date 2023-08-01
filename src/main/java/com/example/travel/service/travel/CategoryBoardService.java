package com.example.travel.service.travel;

import com.example.travel.domain.CategoryBoard;
import com.example.travel.dto.travel.CategoryBoardDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface CategoryBoardService {

    public CategoryBoardDTO createCategoryBoard(CategoryBoardDTO categoryBoardDTO, MultipartFile file); // 저장

    public CategoryBoardDTO getCategoryBoard(Long categoryNo,int dayNo); // 저장 된 카테고리 게시판 가져오기
    public boolean deleteCategoryBoardFile(Long boardNo,boolean imgFile); // 저장 된 카테고리 게시판 가져오기

    public List<CategoryBoard> getCategoryBoardList(Long categoryNo); // 카테고리에 포함된 후기 게시판 리스트

    public boolean deleteCategoryBoard(Long categoryNo, int dayNo); // 게시글 하나 삭제




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
