package com.example.travel.service;

import com.example.travel.domain.UserImage;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Log4j2
public class FileService {
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Transactional
    public UserImage createImageDTO(MultipartFile uploadFile, String folderPath) { // 파일, 파일 경로
        log.info("이미지 저장 ==========================");
        if(uploadFile.getContentType().startsWith("image") == false) {
            log.warn("이미지 파일이 아닙니다.");
            return UserImage.builder().build();
        }

        //1. 파일 경로 폴더를 생성
        //String folderPathMake = makeDateFolder(); // 날짜 폴더 생성
        String folderPathMake = makeFolder(folderPath);


        //2. 경로와 이름을 나눠야함.
        //실제 파일 이름 ie 나 edge는 전체 경로가 전달된다.
        String uploadFileName = uploadFile.getOriginalFilename();
        String fileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1 );
        log.info("fileName : " + fileName);




        //UUID
        String uuid = UUID.randomUUID().toString();
        String originalName = uuid + "_"+ fileName;
        String thumbnailName ="s_" + uuid + "_"+ fileName;

        //저장할 파일 이름 중간에 _ 를 이용하여 구분
        String saveName = uploadPath + File.separator + folderPathMake + File.separator + originalName;
        Path savePath=  Paths.get(saveName);
        try {
            //실제 이미지 저장
            uploadFile.transferTo(savePath);
            //화면에 전달할 DTO 생성
            UserImage memberImage = UserImage.builder()
                    .originFileName(originalName)
                    .thumbnailName(thumbnailName)
                    .uuid(uuid)
                    .fileName(fileName)
                    .path(folderPath)
                    .build();

            //썸네일 생성
            String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + thumbnailName;
            File thumbnailFile = new File(thumbnailSaveName);
            //썸네일 저장
            Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,200,200);


            return memberImage;
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("업로드 폴더 생성 실패: " + e.getMessage());
            return null;
        }
    }


    private String makeFolder(String folder) {
        String folderPath = folder.replace("/", File.separator);
        File uploadPathFolder = new File(uploadPath,folderPath);
        if (uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }


    // 날짜 폴더 생성
    private String makeDateFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

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
