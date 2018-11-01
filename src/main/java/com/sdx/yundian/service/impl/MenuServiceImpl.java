package com.sdx.yundian.service.impl;

import com.sdx.yundian.dao.MenuRepository;
import com.sdx.yundian.entity.Gadmin;
import com.sdx.yundian.entity.Menu;
import com.sdx.yundian.entity.User;
import com.sdx.yundian.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;

@Service(value = "menuService")
public class MenuServiceImpl extends BaseServiceImpl <Menu, Integer> implements MenuService {


    @Autowired
    private MenuRepository menuRepository;
    @Override
    public List <Menu> getRecursionList(List <Menu> list) {
        return this.getRecursionList(list,0);
    }

    @Override
    public List <Menu> getRecursionList(List <Menu> list, int parent_id) {
        List<Menu> new_menu = new ArrayList();

        for (Menu user:list){
            if(user.getParentId() == parent_id){
                List<Menu> children = this.getRecursionList(list,user.getMenuId());
                if(children.size()>0){
                    user.setChildren(children);
                }
                new_menu.add(user);
            }
        }
        return new_menu;
    }

    @Override
    public List <Menu> getUserMenus(User user) {
        Gadmin gadmin = user.getGadmin();
        List<Menu> list = new ArrayList<Menu>(gadmin.getMenus());
        Collections.sort(list, new Comparator <Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
               return o1.getMenuId().compareTo(o2.getMenuId());
            }
        });
        return list;
    }

    @Override
    public List <Menu> getParentId(int parent_id) {
        return menuRepository.getParentId(parent_id);
    }


}
