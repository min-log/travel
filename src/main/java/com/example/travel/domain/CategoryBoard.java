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
    @Column(columnDefinition = "varchar(500)", nullable = false)
    private String boardTit;
    private String boardTravelDate;

    @Column(columnDefinition = "varchar(6000)", nullable = false)
    private String boardContent;


}
