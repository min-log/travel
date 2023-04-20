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
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category_id;

    private String item_day; //여행 DAY
    private int item_account; // 비용
    private String item_content; //내용

    //kakao api
    private String id; // 장소 ID
    private String place_name; // 장소 명
    private String phone; // 전화번호
    private String place_url; //url
    private String address_name; // 지번 주소
    private String road_address_name; // 도로명 주소
    private String x; //x 좌표값
    private String y; // y 좌표값
    private String distance; // 중심좌표까지의 거리

}
