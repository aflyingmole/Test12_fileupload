package com.example.demo.controller;

import com.example.demo.dto.FileInfoDto;
import com.example.demo.dto.MultiFileRequestDto;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class MultiFileUploadController {
    @Autowired
    private FileService service;
    @Value("${file.path}")
    private String filePath;

    @GetMapping("/file/uploadFiles")
    public String uploadForm() {
        return "file/uploadFiles";
    }

    @PostMapping("/file/uploadFiles")
    public String upload(MultiFileRequestDto dto, Model model) throws IOException {
       List<MultipartFile> list = dto.getFile1();
        File destFile = new File(filePath);
        if (!destFile.exists()) destFile.mkdir();
        for (MultipartFile mf : list) {
            String orgFileName = mf.getOriginalFilename(); //전송된 파일명
            String saveFileName = UUID.randomUUID() + "_" + orgFileName; //저장할 파일명
            long fileSize = mf.getSize(); //파일크기
            try {
                File f = new File(filePath + File.separator + saveFileName); //저장할 정보를 갖는 파일객체
                mf.transferTo(f); //업로드한 파일을 f에 복사하기

                FileInfoDto vo = FileInfoDto.builder()
                        .writer(dto.getWriter())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .saveFilename(saveFileName)
                        .orgFilename(orgFileName)
                        .filesize(fileSize)
                        .build();
                service.insert(vo);
                model.addAttribute("result", "success");

            } catch (IOException e) {
                System.out.println(e.getMessage());
                model.addAttribute("result", "error");
            }
        }

        return "result";

    }
    }
