package com.example.travel.dto.user;


import com.example.travel.domain.UserRole;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Validated
public class UserDTO {
    private Long userNo;
    @NotNull(message = "아이디 : 아이디를 입력하세요.")
    private String userId;
    @Email(message = "이메일 : 올바른 형식의 이메일주소를 입력해주세요.")
    @NotBlank(message = "이메일 : 이메일을 입력하세요.")
    private String userEmail;
    @NotBlank(message = "비밀번호 : 비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "이름 : 이름을 입력하세요.")
    private String userName;

    @NotBlank(message = "연락처 : 연락처를 입력하세요.")
    private String userPhone;

    @NotBlank(message = "생년월일 : 생년월일 6자리를 입력하세요.")
    @Length(min = 6,max = 6,message = "생년월일 : 생년월일 6자리를 입력하세요.")
    private String userBirthday;

    //주소
    private String addressPostcode;
    private String address;
    private String addressDetail;
    private String addressExtra;


    private String userGender;
    private String userImg;
    @NotNull(message = "개인정보 동의를 체크해주세요.")
    private Boolean userAgree; //개인정보 동의

    @Max(value = 0, message = "0 이하만 가능합니다.")
    private int idCk;

    private Boolean userSocial;

    @Builder.Default
    private Set<UserRole> roleSet = new HashSet<>();
    public void roleAdd(UserRole userRole){
        roleSet.add(userRole);
    }

}
