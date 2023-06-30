package com.example.travel.dto.travel;


import com.example.travel.domain.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long itemNo; //고유번호
    private Long categoryId;


    // item
    private int itemDay; //여행 DAY
    private int itemNumber; // DAY 안에서 순서
    private int itemAccount; // 비용
    private String itemInfo; // 정보 내용
    private String itemContent; // 회원 컨텐츠
    private String itemTime; // 도착 시간

    //kakao api
    private String id; // 장소 ID
    private String placeName; // 장소 명
    private String placeTime; // 운영 시간
    private String placeUrl; //url
    private String phone; // 전화번호
    private String addressName; // 지번 주소
    private String roadAddressName; // 도로명 주소
    private String x; //x 좌표값
    private String y; // y 좌표값
    private String distance; // 중심좌표까지의 거리




}
