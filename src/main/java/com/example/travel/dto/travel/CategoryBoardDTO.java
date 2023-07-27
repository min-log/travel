package com.example.travel.dto.travel;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBoardDTO{

    private Long boardNo;
    private Long boardCategoryNo;
    private int boardItemDay;
    private String boardTit;
    private String boardTravelDate;
    private String boardContent;
    private LocalDateTime createdAt;
    private String boardImg; // 썸내일 이미지 전달 시 사용


}
