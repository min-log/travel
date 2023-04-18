package com.example.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Entity
public class Item {
    @Id
    @GeneratedValue
    private Long id;


    private Long travelId; //카카오 api id

    private String place_name; //카카오 api id
    private String road_address_name; //카카오 api id
    private String address_name; //카카오 api id
    private String place_url; //카카오 api id
    private String phone; //카카오 api id



}
