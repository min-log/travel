package com.example.travel.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileApi {


    @PostMapping(value = "/image")
    public String imageUpload(@RequestParam("file") MultipartFile files) {
        log.info("ㅎㅎㅎ");
       return "전달하자";
    }




}
