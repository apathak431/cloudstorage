package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService extends BaseUserRelatedService<File> {
    File uploadFile(MultipartFile file) throws IOException;
}
