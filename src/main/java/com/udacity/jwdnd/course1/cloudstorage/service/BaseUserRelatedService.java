package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.BaseUserRelatedEntity;
import java.util.List;

public interface BaseUserRelatedService<E extends BaseUserRelatedEntity> extends BaseService<E> {

    List<E> fetchByUserId(Long userId);
}
