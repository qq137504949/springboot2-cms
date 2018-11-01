package com.sdx.yundian.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T,ID>{


    /**
     * 分页 like
     * @param model_column  模型字段
     * @param name   关键字
     * @param page   当前页 从1开始
     * @param count  显示数量
     * @param sort 排序
     * @return
     */
    Page<T> findAllByLikeName(final String model_column, String name, int page, int count, Sort sort);
}
