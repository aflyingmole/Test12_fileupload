package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileDeleteController {
    @Autowired
    private FileService service;
    @GetMapping("/file/delete")
    public String delete(@RequestParam("fileNum") Long fileNum) {
        service.delete(fileNum);
        return "redirect:/file/list";
    }
}
