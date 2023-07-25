package com.example.travel.service;

import com.example.travel.domain.UserImage;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Log4j2
public class BoardFileService{

//    @Value("${spring.servlet.multipart.location}")
//    private String uploadPath;

    public JsonObject createImage(MultipartFile uploadFile,String folderPath) {
        log.info("이미지 저장 ==========================");

        JsonObject jsonObject = new JsonObject();
        String fileRoot = "C:\\summernoteImg\\";
        String originalFileName = uploadFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String saveFileName = UUID.randomUUID()+extension;

        File targetFile = new File(fileRoot+saveFileName);

        try {
            InputStream fileStream = uploadFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            jsonObject.addProperty("url", "/summernoteImg/"+saveFileName);
            jsonObject.addProperty("responseCode", "succcess");
        } catch(IOException e) {
            FileUtils.deleteQuietly(targetFile);
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }
        return jsonObject;

    }

}
