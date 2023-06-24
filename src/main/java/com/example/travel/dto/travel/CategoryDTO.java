package com.example.travel.dto.travel;


import com.example.travel.domain.Item;
import com.example.travel.domain.UserTravel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    // Category
    private Long userTravelNo;  //작성자 아이디
    private String categoryName; // 카테고리 이름
    private String dateStart;
    private String dateEnd;
    private String categoryArea; // 시/도
    private String categoryAreaDetails; // 군구
    private boolean categoryOpen; // 카테고리 외부 공개 여부

    private List<String> tags; // 참여자 리스트






}
