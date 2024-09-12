package com.example.demo.controller;

import com.example.demo.dto.FileInfoDto;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
public class DownloadController {
    @Autowired
    private FileService service;
    @Value("${file.path}")
    private String filePath;
    @GetMapping("/file/download")
    public ResponseEntity<Resource> download(@RequestParam("fileNum")Long fileNum) throws MalformedURLException {
        FileInfoDto dto = service.select(fileNum);
        String saveFilename = dto.getSaveFilename();
        String orgFilename = dto.getOrgFilename();
        //파일명이 한글일 경우 깨지지 않도록 인코딩
        String fileName = UriUtils.encode(orgFilename, StandardCharsets.UTF_8);
        UrlResource resource = new UrlResource("file:" +filePath + saveFilename); //예외처리하기
        String contentDisposition = "attachment;filename=\"" + fileName +"\"";
        return  ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
                .body(resource);
    }
}
