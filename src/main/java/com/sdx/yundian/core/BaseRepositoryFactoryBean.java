package com.sdx.yundian.core;

import com.sdx.yundian.dao.impl.BaseRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class BaseRepositoryFactoryBean<R extends JpaRepository <T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean <R, T, I> {

    public BaseRepositoryFactoryBean(Class <? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new PrvBaseRepository(entityManager);
    }


    private static class PrvBaseRepository<T, I extends Serializable> extends JpaRepositoryFactory {

        private final EntityManager em;

        public PrvBaseRepository(EntityManager entityManager) {
            super(entityManager);
            this.em = entityManager;
        }

        // 设置具体的实现类是BaseRepositoryImpl
        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
//            return new BaseRepositoryImpl <T, I>(information.getDomainType(), em);
            Class <T> clazz = (Class <T>) information.getDomainType();
            JpaEntityInformation <T, I> entityInformation = getEntityInformation(clazz);
            return new BaseRepositoryImpl <T, I>(entityInformation, em);
        }

        // 设置具体的实现类的class
        protected Class <?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;

        }
    }
}
