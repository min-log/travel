package com.example.travel.dto.item;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ItemResultDto {
    private Long travelId;
    private Long travelDay;
    private String travelContent; // 내용
    //카카오 api
    private Long item_id; // id
    private String place_name; // 장소이름
    private String road_address_name; //주소1
    private String address_name; //주소2
    private String place_url; //url
    private String phone; //연락처
}
