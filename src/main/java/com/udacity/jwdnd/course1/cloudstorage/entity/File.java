package com.udacity.jwdnd.course1.cloudstorage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.sql.Timestamp;

@Entity
public class File extends BaseUserRelatedEntity{

    private String filename;
    private String contentType;
    private String fileSize;
    @Lob
    @Column(name = "fileData", columnDefinition="BLOB")
    private byte[] fileData;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String fileName) {
        this.filename = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
