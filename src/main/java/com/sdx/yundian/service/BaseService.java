package com.sdx.yundian.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T, ID extends Serializable> {

    T save(T t);

    T update(T t);

    T get(Integer id);

    void delete(T t);


    long count();

    List <T> findAll();

    Page<T> findPage(Pageable pageable);

    /**
     *
     * @param model_column 模型字段
     * @param name  keywords
     * @param page  当前页
     * @param count 显示调试
     * @param sort 排序
     * @return
     */
    Page<T> findAllByLikeName(final String model_column, String name, int page, int count, Sort sort);

}
