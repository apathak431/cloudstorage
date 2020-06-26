package com.udacity.jwdnd.course1.cloudstorage.exception.entity;

import java.io.Serializable;
import java.util.Date;

public class ErrorMessage implements Serializable {

    private int status;
    private String message;
    private Date timestamp;

    public ErrorMessage(int status, String message, Date timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
