package com.example.demo.controller;

import com.example.demo.dto.FileInfoDto;
import com.example.demo.dto.FileRequestDto;
import com.example.demo.entity.FileInfo;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Controller
public class FileUpdateController {
    @Value("${file.path}")
    private String filePath;

    @Autowired
    private FileService service;

    @GetMapping("/file/update")
    public String updateForm(@RequestParam("fileNum") Long fileNum, Model model) {
        model.addAttribute("update", service.select(fileNum));
        return "file/update";
    }

    @PostMapping("/file/update")
    public String update(FileRequestDto dto, Model model) {
        MultipartFile file1 = dto.getFile1();
        try {
            FileInfoDto fileinfo = service.select(dto.getFileNum());
            if (!file1.isEmpty()) {//수정할 파일이 전송된 경우
                // 기존 파일 삭제
                File f = new File(filePath + File.separator + fileinfo.getSaveFilename());
                if (f.exists() && f.delete()) {
                    System.out.println("파일삭제 완료");
                }
                // 전송된 파일 업로드하고 새로운 파일 정보로 update
                String orgFilename = file1.getOriginalFilename();
                String saveFilename = UUID.randomUUID()+"_" + orgFilename;
                File destFile = new File(filePath + File.separator + saveFilename);
                file1.transferTo(destFile);
                long fileSize = file1.getSize();
                FileInfoDto updateDto = FileInfoDto.builder()
                        .fileNum(dto.getFileNum())
                        .title(dto.getTitle())
                        .writer(dto.getWriter())
                        .content(dto.getContent())
                        .orgFilename(orgFilename)
                        .saveFilename(saveFilename)
                        .filesize(fileSize)
                        .build();
                service.update(updateDto);
            } else {
                //수정할 파일이 전송안된 경우 기존파일 유지
                // 전송된 정보로 update 하지만 파일은 기존 정보를 유지
                FileInfoDto updateDto = FileInfoDto.builder()
                        .fileNum(dto.getFileNum())
                        .title(dto.getTitle())
                        .writer(dto.getWriter())
                        .content(dto.getContent())
                        .orgFilename(fileinfo.getOrgFilename())
                        .saveFilename(fileinfo.getSaveFilename())
                        .filesize(fileinfo.getFilesize())
                        .build();
                service.update(updateDto);
            }
            model.addAttribute("result", "success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("result", "fail");
        }
        return "result";
    }

}
