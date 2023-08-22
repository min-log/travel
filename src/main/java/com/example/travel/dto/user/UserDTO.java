package com.example.travel.dto.user;


import com.example.travel.domain.UserRole;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
@Builder
@Validated
public class UserDTO {
    private Long userNo;
    @NotNull(message = "아이디 : 아이디를 입력하세요.")
    private String userId;
    @Email(message = "이메일 : 올바른 형식의 이메일주소를 입력해주세요.")
    @NotBlank(message = "이메일 : 이메일을 입력하세요.")
    private String userEmail;


    //@NotBlank(message = "비밀번호 : 비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    @Pattern(regexp="[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String password;

    @NotBlank(message = "이름 : 이름을 입력하세요.")
    private String name;

    @NotBlank(message = "연락처 : 연락처를 입력하세요.")
    private String userPhone;

    @NotBlank(message = "생년월일 : 생년월일 8자리를 입력하세요.")
    @Length(min = 8,max = 8,message = "생년월일 : 생년월일 6자리를 입력하세요.")
    private String userBirthday;

    private int userAge; //나이

    //주소
    private String addressPostcode;
    private String address;
    private String addressDetail;
    private String addressExtra;


    private String userGender;
    private MultipartFile userImg;




    @NotNull(message = "개인정보 동의를 체크해주세요.")
    private Boolean userAgree; //개인정보 동의

//    @Max(value = 0, message = "0 이하만 가능합니다.")
//    private int idCk;

    private Boolean userSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> roleSet = new HashSet<>();
    public void roleAdd(UserRole userRole){
        roleSet.add(userRole);
    }

}
