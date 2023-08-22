package com.example.travel.domain;


import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    private Boolean boardExistence; // 카테고리 후기 존재 유무
    private String boardImg; //카테고리 후기 이미지



}
