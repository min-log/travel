package com.example.travel.dto;

import com.example.travel.domain.UserImage;
import com.example.travel.dto.user.UserDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDTO {
    private Long id;
    private String uuid;
    private String fileName;
    private String originFileName;
    private String path; // 폴더 생성 날짜



}
