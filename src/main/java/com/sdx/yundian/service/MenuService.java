package com.sdx.yundian.service;

import com.sdx.yundian.entity.Menu;
import com.sdx.yundian.entity.User;

import java.util.List;
import java.util.Set;

public interface MenuService extends BaseService<Menu,Integer> {

    /**
     * 递归整理数据
     * @param list
     * @return
     */
    List<Menu> getRecursionList(List<Menu> list);
    List<Menu> getRecursionList(List<Menu> list,int parent_id);

    /**
     * 根据用户获取权限
     * @param user
     * @return
     */
    List<Menu> getUserMenus(User user);

    /**
     * 根据父类ID获取列表
     * @param parent_id 父类ID
     * @return
     */
    List<Menu> getParentId(int parent_id);
}
