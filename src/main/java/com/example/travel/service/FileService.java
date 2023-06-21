package com.example.travel.service;

import com.example.travel.domain.UserImage;
import com.example.travel.dto.ImageDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
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
    public UserImage createImageDTO(MultipartFile uploadFile) {
        log.info("이미지 저장 ==========================");
        if(uploadFile.getContentType().startsWith("image") == false) {
            log.warn("이미지 파일이 아닙니다.");
            return UserImage.builder().build();
        }
        //2. 경로와 이름을 나눠야함.
        //실제 파일 이름 ie 나 edge는 전체 경로가 전달된다.
        String originalName = uploadFile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1 );
        log.info("fileName : " + fileName);
        //3. 날짜 폴더를 생성
        String folderPath = makeFolder();
        //UUID
        String uuid = UUID.randomUUID().toString();
        //저장할 파일 이름 중간에 _ 를 이용하여 구분
        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
        Path savePath=  Paths.get(saveName);
        log.info(0);
        try {
            log.info(1);
            //실제 이미지 저장
            uploadFile.transferTo(savePath);
            //화면에 전달할 DTO 생성
            UserImage memberImage = UserImage.builder()
                    .originFileName(originalName)
                    .uuid(uuid)
                    .fileName(fileName)
                    .path(folderPath)
                    .build();

            //썸네일 생성
            String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
            File thumbnailFile = new File(thumbnailSaveName);
            //썸네일 저장
            Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,200,200);
            log.info(2);


            return memberImage;
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("업로드 폴더 생성 실패: " + e.getMessage());
            return null;
        }
    }


    // 날짜 폴더 생성
    private String makeFolder() {
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
