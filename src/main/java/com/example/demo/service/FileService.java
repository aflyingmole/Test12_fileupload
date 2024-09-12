package com.example.demo.service;

import com.example.demo.dto.FileInfoDto;
import com.example.demo.entity.FileInfo;
import com.example.demo.repository.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FileService {
    @Value("${file.path}")
    private String filePath;

    @Autowired
    private FileInfoRepository fr;

    public FileInfoDto insert(FileInfoDto dto) {
        return new FileInfoDto(fr.save(dto.toEntity()));
    }

    public List<FileInfo> findAll() {
        List<FileInfo> list = fr.findAll();
        return list;
    }

    public void delete(Long fileNum) {
        FileInfoDto fileInfo = select(fileNum);
        fr.deleteById(fileNum);

        String path = filePath + fileInfo.getSaveFilename();

        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }

    }

    public FileInfoDto select(Long fileNum) {
        return new FileInfoDto(fr.findById(fileNum).get());
    }

    public FileInfoDto update(FileInfoDto dto) {
    FileInfo file = fr.findById(dto.getFileNum()).get();
        file.setContent(dto.getContent());
        file.setFilesize(dto.getFilesize());
        file.setOrgFilename(dto.getOrgFilename());
        file.setSaveFilename(dto.getSaveFilename());
        file.setTitle(dto.getTitle());
        file.setWriter(dto.getWriter());
        return new FileInfoDto(file);
    }
}
