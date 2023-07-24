package com.example.travel.service.user;

import com.example.travel.domain.UserImage;
import com.example.travel.dto.user.ImageDTO;
import org.springframework.stereotype.Service;

@Service
public class UserImageService {
    public ImageDTO entityToDto(UserImage e){
        ImageDTO result = ImageDTO.builder()
                .id(e.getId())
                .originFileName(e.getOriginFileName())
                .path(e.getPath())
                .fileName(e.getFileName())
                .uuid(e.getUuid())
                .build();
        return result;
    }


    public UserImage dtoToEntity(ImageDTO e){
        UserImage result = UserImage.builder()
                .id(e.getId())
                .originFileName(e.getOriginFileName())
                .path(e.getPath())
                .fileName(e.getFileName())
                .uuid(e.getUuid())
                .build();
        return result;
    }
}
