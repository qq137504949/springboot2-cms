package com.sdx.yundian.dao;

import com.sdx.yundian.entity.Menu;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MenuRepository extends BaseRepository<Menu,Integer> {

    @Query(value = "from Menu m where m.parentId=:parentId")
    List<Menu> getParentId(@Param("parentId") int parentId);
}
