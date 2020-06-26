package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.BaseEntity;
import com.udacity.jwdnd.course1.cloudstorage.mapper.BaseMapper;
import javassist.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class BaseServiceImpl<E extends BaseEntity> implements BaseService<E> {

    private BaseMapper<E> baseMapper;

    public BaseServiceImpl(BaseMapper<E> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public List<E> fetchAll() {
        return baseMapper.findAll();
    }

    @Override
    public E fetchById(Long id) throws NotFoundException {
        Optional<E> entity = baseMapper.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        } else throw new NotFoundException(String.format("Resource with id=%d not found", id));
    }

    @Override
    public E save(E entity) {
        baseMapper.save(entity);
        return entity;
    }

    @Override
    public E update(E entity, Long id) throws NotFoundException {
        fetchById(id);
        baseMapper.update(entity, id);

        return entity;
    }

    @Override
    public Boolean delete(Long id) throws NotFoundException {
        fetchById(id);
        baseMapper.delete(id);

        return true;
    }
}
