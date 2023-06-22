package com.example.travel.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "userNo, categoryId")
@Getter
@Builder
@Entity
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;


    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoryId;

    private String itemDay; //여행 DAY
    private int itemAccount; // 비용
    private String itemContent; //내용

    //kakao api
    private String id; // 장소 ID
    private String placeName; // 장소 명
    private String phone; // 전화번호
    private String placeUrl; //url
    private String addressName; // 지번 주소
    private String roadAddressName; // 도로명 주소
    private String x; //x 좌표값
    private String y; // y 좌표값
    private String distance; // 중심좌표까지의 거리

}
