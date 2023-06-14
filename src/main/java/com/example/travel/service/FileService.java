package com.example.travel.service;

import com.example.travel.dto.ImageDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Log4j2
public class FileService {
    @Value("${spring.servlet.multipart.location}")
    private String imagePathName;

    @Transactional
    public ImageDTO createImageDTO(String originalSourceName, Path path) throws IOException {
        String fileName = originalSourceName.substring(originalSourceName.lastIndexOf("\\") + 1);
        String uuid = UUID.randomUUID().toString();
        String fileUrl = getDirectory(path) + File.separator + uuid + "_" + fileName;
        String originFileName = uuid + "_" + fileName;
        return ImageDTO.builder()
                .fileName(fileName)
                .originFileName(originFileName)
                .uuid(uuid)
                .fileUrl(fileUrl)
                .build();
    }

    private String getDirectory(Path path) throws IOException {
        if(!Files.exists(path)) {
            Files.createDirectory(path);
        }
        return path.toString();
    }
}
