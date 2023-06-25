package com.example.travel.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "userNo, categoryId")
@Getter
@Builder
@Entity
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemNo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoryNo;

    private int itemDay; //여행 DAY
    private int itemNumber; // DAY 안에서 순서
    private int itemAccount; // 비용
    private String itemInfo; // 정보 내용
    private String itemContent; // 회원 컨텐츠
    private LocalDateTime itemTime; // 도착 시간

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
