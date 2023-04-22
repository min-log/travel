package com.example.travel.dto.user;


import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Validated
public class UserSaveDTO {
    private Long userNo;
    @NotNull(message = "아이디를 입력하세요.")
    private String userId;
    @Email
    @NotBlank(message = "이메일을 입력하세요.")
    private String userEmail;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String userPassword;

    @NotBlank(message = "이름을 입력하세요.")
    private String userName;

    @NotBlank(message = "핸드폰번호를 입력하세요.")
    private String userPhone;
    private String userBirthday;
    private String userAddress;
    private String userGender;
    private String userImg;
    private Boolean userAgree;


}