package com.sdx.yundian.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "xm_gadmin")
public class Gadmin implements Serializable {


    private Integer gid;


    @NotEmpty(message = "角色名称不能为空")
    private String gname;
    private Set <Menu> menus = new HashSet <Menu>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "xm_role_menu",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    public Set <Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set <Menu> menus) {
        this.menus = menus;
    }

    @Id
    @Column(name = "gid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    @Basic
    @Column(name = "gname")
    public String getGname() {
        return gname;
    }


    public void setGname(String gname) {
        this.gname = gname;
    }

    public Gadmin(Integer gid) {
        this.gid = gid;
    }

    public Gadmin() {
    }

    public Gadmin(Integer gid, String gname) {
        this.gid = gid;
        this.gname = gname;
    }
}
