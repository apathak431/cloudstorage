package com.udacity.jwdnd.course1.cloudstorage.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseUserRelatedEntity extends BaseEntity {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user) {
        this.userId = user;
    }
}
