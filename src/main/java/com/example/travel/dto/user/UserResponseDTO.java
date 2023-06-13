package com.example.travel.dto.user;


import com.example.travel.domain.UserImage;
import com.example.travel.domain.UserRole;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
@Builder
@Validated
public class UserResponseDTO {
    private Long userNo;
    private String userId;
    private String userEmail;
    private String password;
    private String name;
    private String userPhone;
    private String userBirthday;

    //주소
    private String addressPostcode;
    private String address;
    private String addressDetail;
    private String addressExtra;


    private String userGender;
    private UserImage userImg;

    private Boolean userAgree; //개인정보 동의
    private Boolean userSocial;

    private Set<UserRole> roleSet = new HashSet<>();

}
