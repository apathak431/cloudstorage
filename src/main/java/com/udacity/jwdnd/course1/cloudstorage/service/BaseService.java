package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.BaseEntity;
import javassist.NotFoundException;

import java.util.List;

public interface BaseService<E extends BaseEntity> {

    List<E> fetchAll();
    E fetchById(Long id) throws NotFoundException;
    E save(E entity);
    E update(E entity, Long id) throws NotFoundException;
    Boolean delete(Long id) throws NotFoundException;
}
