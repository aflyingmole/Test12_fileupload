package com.example.demo.controller;

import com.example.demo.entity.FileInfo;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FileLIstController {
    @Autowired
    private FileService service;
    @GetMapping("/file/list")
    public String list(Model model) {
        List<FileInfo> all = service.findAll();
        model.addAttribute("list", all);
        return "file/list";
    }
}
