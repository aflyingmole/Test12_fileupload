package com.example.demo.controller;

import com.example.demo.dto.FileInfoDto;
import com.example.demo.service.FileService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileInfoController {
    @Autowired
    private FileService service;
    @GetMapping("/file/info")
    public String fileInfo(@RequestParam("fileNum")Long fileNum, Model model) {
        model.addAttribute("dto", service.select(fileNum));
        return "file/info";
    }
}
