package com.example.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ImageDTO {

    private String fileName;
    private String originFileName;
    private String uuid;
    private String fileUrl;

}
