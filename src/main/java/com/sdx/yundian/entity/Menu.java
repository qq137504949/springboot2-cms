package com.sdx.yundian.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "xm_admin_menus")
public class Menu implements Serializable {
    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuId;
    @Column(name = "menu_name",length = 50)
    @NotEmpty(message = "菜单名称不能为空")
    private String menuName;
    @Column(name = "menu_icon")
    private String menuIcon;
    @Column(name = "menu_link")
    private String menuLink;
    @Column(name = "parent_id",length = 11)
    private Integer parentId = 0;


    @Transient
    private List<Menu> children;

    public Menu() {
    }

    public Menu(Integer menuId) {
        this.menuId = menuId;
    }

    public Menu(String menuName, String menuIcon, String menuLink, Integer parentId, List <Menu> children) {
        this.menuName = menuName;
        this.menuIcon = menuIcon;
        this.menuLink = menuLink;
        this.parentId = parentId;
        this.children = children;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List <Menu> getChildren() {
        return children;
    }

    public void setChildren(List <Menu> children) {
        this.children = children;
    }


}

