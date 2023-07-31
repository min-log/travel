package com.example.travel.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Entity
public class CategoryBoard extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo;
    private Long boardCategoryNo;
    private int boardItemDay;
    private String boardTit;
    private String boardTravelDate;
    private String boardContent;


}
