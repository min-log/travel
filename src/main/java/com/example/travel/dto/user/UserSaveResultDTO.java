package com.example.travel.dto.user;


import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Validated
public class UserSaveResultDTO {
    @NotNull
    private Long userNo;
    @NotNull(message = "아이디를 입력하세요.")
    private String userId;
    @NotBlank(message = "이메일을 입력하세요.")
    private String userEmail;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String userPassword;

    @NotBlank(message = "이름을 입력하세요.")
    private String userName;
    @Pattern(regexp = "/^[0-9]{3}[-]+[0-9]{4}[-]+[0-9]{4}$/")
    @NotBlank(message = "핸드폰번호를 입력하세요.")
    private String userPhone;
    private String userBirthday;
    //주소
    private String addressPostcode;
    private String address;
    private String addressDetail;
    private String addressExtra;

    private String userGender;
    private String userImg;



}
