package com.example.travel.service;

import com.example.travel.domain.CategoryImage;
import com.example.travel.domain.UserImage;
import com.example.travel.repository.travel.CategoryImageRepository;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
@Log4j2
public class BoardFileService{

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    private CategoryImageRepository categoryImageRepository;

    public JsonObject createImage(MultipartFile uploadFile,String folderPath) {
        log.info("일반 컨텐츠 이미지 저장 ==========================");

        //0. 저장 후 전달할 객체
        JsonObject jsonObject = new JsonObject();

        //1. 파일 경로 폴더를 생성
        String folderPathMake = makeFolder(folderPath);


        //2. 경로와 이름을 나눠야함.
        //실제 파일 이름 ie 나 edge는 전체 경로가 전달된다.
        String uploadFileName = uploadFile.getOriginalFilename();
        String fileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1 );
        log.info("fileName : " + fileName);

        //UUID
        String uuid = UUID.randomUUID().toString();
        String originalName = uuid + "_"+ fileName;

        //저장할 파일 이름 중간에 _ 를 이용하여 구분
        String saveName = uploadPath + File.separator + folderPathMake + File.separator + originalName;
        String url =  "/" + "upload" +  "/" + folderPathMake  + "/" + originalName;
        Path savePath=  Paths.get(saveName);
        try {
            //실제 이미지 저장
            uploadFile.transferTo(savePath);
            //화면에 전달할 DTO 생성
            jsonObject.addProperty("url", url);
            jsonObject.addProperty("responseCode", "succcess");

        } catch (IOException e) {
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
            log.warn("업로드 폴더 생성 실패: " + e.getMessage());
        }


        return jsonObject;
    }
    protected String makeFolder(String folder) {
        String folderPath = folder.replace("/", File.separator);
        File uploadPathFolder = new File(uploadPath,folderPath);
        if (uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }




    //파일 제거
    public ResponseEntity<Boolean> removeFile(String fileName){
        log.info("이미지 제거 로직");
        String srcFileName = null;

        try {
            log.info(fileName);

            srcFileName = URLDecoder.decode(fileName,"utf-8");
            File file = new File(uploadPath+File.separator+srcFileName);
            log.info(file);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(),"s_"+file.getName());
            result = thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
