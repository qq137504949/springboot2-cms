package com.sdx.yundian.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "xm_admin")

public class User implements Serializable {
    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    @NotEmpty(message = "用户名不能为空")
    @Column(name = "user_name",columnDefinition="varchar(50) COMMENT '用户名'")
    private String userName;

    @Column(name = "email",columnDefinition = "varchar(50) COMMENT '邮箱'")
    private String email;

    @Length(min = 6, message = "密码长度不能少于6位")
    @Column(name = "password",columnDefinition = "varchar(255) COMMENT '密码'")
    private String password;

    @Column(name = "remember_token",columnDefinition = "varchar(255) COMMENT '登录钥匙'")
    private String rememberToken;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @Column(name = "admin_login_time")
    private Date adminLoginTime;

    @Column(name = "admin_login_num",columnDefinition = "int(11) DEFAULT 0 COMMENT '登录次数'")
    private Integer adminLoginNum = 0;

    @Column(name = "admin_is_super",columnDefinition = "tinyint(1) DEFAULT 0 COMMENT '是否是超级管理与那'")
    private Boolean adminIsSuper = false;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)//由于超级管理账户为0所以要加上这句
    @JoinColumn(name = "admin_gid",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))//不需要添加外键
    private Gadmin gadmin;


    public Gadmin getGadmin() {
        return gadmin;
    }

    public void setGadmin(Gadmin gadmin) {
        this.gadmin = gadmin;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getAdminLoginTime() {
        return adminLoginTime;
    }

    public void setAdminLoginTime(Date adminLoginTime) {
        this.adminLoginTime = adminLoginTime;
    }

    public Integer getAdminLoginNum() {
        return adminLoginNum;
    }

    public void setAdminLoginNum(Integer adminLoginNum) {
        this.adminLoginNum = adminLoginNum;
    }

    public Boolean getAdminIsSuper() {
        return adminIsSuper;
    }

    public void setAdminIsSuper(Boolean adminIsSuper) {
        this.adminIsSuper = adminIsSuper;
    }

}
