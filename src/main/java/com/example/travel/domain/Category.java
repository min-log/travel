package com.example.travel.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user_id")
@Getter
@Entity
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user_id;  //작성자 id
    private String category_name; // 카테고리 이름
    private String category_area; // 시/도
    private String category_area_details; // 군구

    private boolean category_open; // 카테고리 외부 공개 여부


}
