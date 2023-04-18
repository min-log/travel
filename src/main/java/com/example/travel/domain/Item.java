package com.example.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "userId, category_id")
@Getter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travel_id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category_id;
    private Long travel_day; // DAY 번호
    private String travel_content; // 내용

    //user
    @ManyToOne(fetch = FetchType.LAZY)
    private User user_no;

    //카카오 api
    private Long item_id; // id
    private String place_name; // 장소이름
    private String road_address_name; //주소1
    private String address_name; //주소2
    private String place_url; //url
    private String phone; //연락처

}
