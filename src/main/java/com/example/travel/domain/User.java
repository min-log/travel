package com.example.travel.domain;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    private String userId;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userBirthday;
    private String userGender;
    private String userPhone;


    private String userImg;

    //주소
    private String addressPostcode;
    private String address;
    private String addressDetail;
    private String addressExtra;

    private Boolean userAgree;//개인정보 동의




}
