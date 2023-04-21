package com.example.travel.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "userNo , items")
@Getter
@Entity
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    private User userNo;  //작성자 id
    private String categoryName; // 카테고리 이름
    private String categoryArea; // 시/도
    private String categoryAreaDetails; // 군구
    private boolean categoryOpen; // 카테고리 외부 공개 여부

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id")
    private List<Item> items;



}
