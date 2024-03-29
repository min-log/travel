package com.example.travel.controller.api;

import com.example.travel.service.BoardFileService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpResponse;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/fileApi")
public class FileApi {
    final BoardFileService boardFileService;
    @PostMapping(value = "/categoryImgSave")
    @ResponseBody
    public String categoryImageUpload(@RequestParam("file") MultipartFile files) {
        log.info("컨텐츠 이미지 저장------------------------");
        JsonObject categoryImg = boardFileService.createImage(files, "categoryImg");
        String txt = categoryImg.toString();
        return txt;

    }




}
