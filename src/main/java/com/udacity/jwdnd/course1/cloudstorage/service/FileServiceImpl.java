package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class FileServiceImpl extends BaseUserRelatedServiceImpl<File> implements FileService {

        private FileMapper fileMapper;

        @Autowired
        public FileServiceImpl(FileMapper fileMapper) {
                super(fileMapper);
                this.fileMapper = fileMapper;
        }

        @Override
        public File uploadFile(MultipartFile file) throws IOException {
                File fileEntity = new File();
                fileEntity.setFilename(file.getOriginalFilename());
                fileEntity.setContentType(file.getContentType());
                fileEntity.setFileSize(getSizeFromLong(file.getSize()));
                fileEntity.setFileData(file.getBytes());

                return save(fileEntity);
        }

        private String getSizeFromLong(Long longSize) {
                int kiloByteValue = 1024*1024;
                int megaByteValue = kiloByteValue*1024;

                if (longSize >= megaByteValue) {
                        return longSize / megaByteValue + "MB";
                } else if (longSize >= kiloByteValue) {
                        return longSize / kiloByteValue + "KB";
                } else return String.valueOf(longSize);
        }
}
