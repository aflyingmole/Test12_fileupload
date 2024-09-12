package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
public class DataURLController {
    @Value("${file.path}")
    private String filePath;
//    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource showImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + filePath + filename);
    }
}
