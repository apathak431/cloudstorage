package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.BaseEntity;
import java.util.List;
import java.util.Optional;

public interface BaseMapper<E extends BaseEntity> {
    List<E> findAll();
    Optional<E> findById(Long id);
    void save(E entity);
    void update(E entity, Long id);
    void delete(Long id);
}
