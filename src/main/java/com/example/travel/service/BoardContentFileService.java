package com.example.travel.service;

import com.example.travel.domain.CategoryBoard;
import com.example.travel.domain.CategoryImage;
import com.example.travel.domain.FileContent;
import com.example.travel.dto.travel.CategoryBoardDTO;
import com.example.travel.repository.FileRepository;
import com.example.travel.repository.travel.CategoryImageRepository;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.*;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class BoardContentFileService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;
    final FileRepository fileRepository;


    public String createBoardContent(CategoryBoardDTO categoryBoardDTO, String folderPath) {
        log.info("일반 컨텐츠 텍스트파일 저장 ==========================");

        //1. 파일 경로 폴더를 생성
        String folderPathMake = makeFolder(folderPath);


        //2. 경로와 이름을 나눠야함.
        //실제 파일 이름 ie 나 edge는 전체 경로가 전달된다.
        String fileName = categoryBoardDTO.getBoardTit() + ".txt";
        //UUID
        String uuid = UUID.randomUUID().toString();
        String originalName = uuid + "_"+ fileName;


        String uploadFileName = uuid + "_" + categoryBoardDTO.getBoardTit() + ".txt";
        //저장할 파일 이름 중간에 _ 를 이용하여 구분
        String saveName = uploadPath + File.separator + folderPathMake + File.separator + uploadFileName;
        File file = new File(saveName);


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(categoryBoardDTO.getBoardContent());
            writer.close();


            FileContent fileContent = FileContent.builder()
                    .fileName(fileName)
                    .originFileName(originalName)
                    .uuid(uuid)
                    .path(folderPath)
                    .build();

            fileRepository.save(fileContent);


            return originalName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public String readBoardContent(String folderPath,String originalName) {
        String fileUrl = uploadPath + File.separator + folderPath + File.separator;
        File file = new File(fileUrl, originalName);
        BufferedReader br;
        String retStr = "";

        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("line: " + line);
                retStr += line + "\n";
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retStr;
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
    public void removeFile(String folderPath,String fileName){
        log.info("파일 제거 로직--------------");
        String srcFileName = null;
        try{
            srcFileName = URLDecoder.decode(fileName,"utf-8");
            File file = new File(uploadPath + File.separator +File.separator + folderPath + File.separator+ File.separator +srcFileName);
            log.info(file);
            if (file.delete()){
                log.info("파일이 삭제되었습니다.");
            }else {
                log.info("파일삭제가 실패했습니다.");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
