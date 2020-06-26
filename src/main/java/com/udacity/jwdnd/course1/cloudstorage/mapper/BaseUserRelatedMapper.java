package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.BaseUserRelatedEntity;
import java.util.List;

public interface BaseUserRelatedMapper<E extends BaseUserRelatedEntity> extends BaseMapper<E> {

    List<E> findAllByUserId(Long id);
}
