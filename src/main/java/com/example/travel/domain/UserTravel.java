package com.example.travel.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString(exclude = {"roleSet", "userImg"})
public class UserTravel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    private String userId;
    private String userEmail;
    private String password;
    private String name;
    private String userBirthday;
    private int userAge;
    private String userGender;
    private String userPhone;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private UserImage userImg;//프로필 사진

    //주소
    private String addressPostcode;
    private String address;
    private String addressDetail;
    private String addressExtra;

    private Boolean userAgree;//개인정보 동의

    private Boolean userSocial;//소셜회원 정보


    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> roleSet = new HashSet<>();
    public void roleAdd(UserRole userRole){
        roleSet.add(userRole);
    }


    //이미지 업데이트 시 사용
    public void updateUserImage(UserImage userImg) {
        this.userImg = userImg;
    }



}
