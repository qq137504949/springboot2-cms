package com.sdx.yundian.dao.impl;

import com.sdx.yundian.dao.BaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository <T, ID>
        implements BaseRepository <T, ID> {

    /**
     * 持久化上下文
     */
    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;


    @SuppressWarnings("all")
    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.entityInformation = entityInformation;
    }


    @Override
    public Page <T> findAllByLikeName(String model_column, String name, int page, int count, Sort sort) {
        Specification <T> specification = new Specification <T>() {
            @Override
            public Predicate toPredicate(Root <T> root, CriteriaQuery <?> query, CriteriaBuilder criteriaBuilder) {

                Path <String> _name = root.get(model_column);
                Predicate _key = criteriaBuilder.like(_name, "%" + name + "%");

                return criteriaBuilder.and(_key);
            }
        };

        Pageable pageable = PageRequest.of(page - 1, count, sort);
        return findAll(specification, pageable);
    }


    /**
     * 通用save方法 ：新增/选择性更新
     */
    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        //获取ID
        ID entityId = (ID) this.entityInformation.getId(entity);
        T managedEntity;
        T mergedEntity;
        if(entityId == null){
            em.persist(entity);
            mergedEntity = entity;
        }else{
            managedEntity = this.findById(entityId).get();
            if (managedEntity == null) {
                em.persist(entity);
                mergedEntity = entity;
            } else {
                BeanUtils.copyProperties(entity, managedEntity, getNullProperties(entity));
                em.merge(managedEntity);
                mergedEntity = managedEntity;
            }
        }
        return entity;
    }

    /**
     * 获取对象的空属性
     */
    private static String[] getNullProperties(Object src) {
        //1.获取Bean
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        //2.获取Bean的属性描述
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        //3.获取Bean的空属性
        Set<String> properties = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : pds) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = srcBean.getPropertyValue(propertyName);
            if (StringUtils.isEmpty(propertyValue)) {
                srcBean.setPropertyValue(propertyName, null);
                properties.add(propertyName);
            }
        }
        return properties.toArray(new String[0]);
    }


}
