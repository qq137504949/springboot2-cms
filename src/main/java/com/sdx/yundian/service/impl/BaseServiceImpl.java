package com.sdx.yundian.service.impl;

import com.sdx.yundian.dao.BaseRepository;
import com.sdx.yundian.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public class BaseServiceImpl<T, ID extends Serializable> implements BaseService <T, ID> {

    @Autowired
    protected BaseRepository <T, ID> baseRepository;


    @Override
    public T save(T t) {
        return baseRepository.save(t);
    }

    @Override
    public T update(T t) {

        return baseRepository.saveAndFlush(t);
    }

    @Override
    public T get(Integer id) {
        return baseRepository.getOne((ID) id);
    }


    @Override
    public void delete(T t) {
        baseRepository.delete(t);
    }


    @Override
    public long count() {
        return baseRepository.count();
    }

    @Override
    public List findAll() {
        return baseRepository.findAll();
    }


    @Override
    public Page <T> findPage(Pageable pageable) {

        return baseRepository.findAll(pageable);
    }


    @Override
    public Page <T> findAllByLikeName(String model_column, String name, int page, int count, Sort sort) {
        return baseRepository.findAllByLikeName(model_column, name == null ? "" : name, page, count, sort);
    }
}
