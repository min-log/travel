package com.example.travel.domain;


import lombok.*;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "items")
@Getter
@Builder
@Entity
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryNo;
    private Long userTravelNo;  //작성자 No
    private String categoryWriter; //작성자

    private String categoryName; // 카테고리 이름
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private int dateTxt;
    private String categoryArea; // 시/도
    private String categoryAreaDetails; // 군구
    private boolean categorySave; // 임시저장 여부  최종 저장 전에는 false -> true

    private boolean categoryOpen; // 카테고리 외부 공개 여부


    private int categoryTotalPrice; // 카테고리 총비용

    private int viewNum; // 카테고리 조회 수



}
