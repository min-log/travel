package com.example.travel.dto.travel;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryImageDTO {
    private MultipartFile fileDate;
    private String callback;
    private String callbackFunc;


}
