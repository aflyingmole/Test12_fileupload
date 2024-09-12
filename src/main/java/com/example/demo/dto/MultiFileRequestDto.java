package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MultiFileRequestDto {
    private Long fileNum;
    private String writer;
    private String title;
    private String content;
    private List<MultipartFile> file1;
}
