package com.example.demo.dto;

import com.example.demo.entity.FileInfo;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FileInfoDto {
    private Long fileNum;
    private String writer;
    private String title;
    private String content;
    private String orgFilename;
    private String saveFilename;
    private long filesize;
    public FileInfoDto(FileInfo info) {
        this.fileNum = info.getFileNum();
        this.writer = info.getWriter();
        this.title = info.getTitle();
        this.content = info.getContent();
        this.orgFilename = info.getOrgFilename();
        this.saveFilename = info.getSaveFilename();
        this.filesize = info.getFilesize();
    }
    public FileInfo toEntity() {
        FileInfo info = FileInfo.builder()
                .fileNum(fileNum)
                .writer(writer)
                .title(title)
                .content(content)
                .orgFilename(orgFilename)
                .saveFilename(saveFilename)
                .filesize(filesize)
                .build();
        return info;
    }
}
