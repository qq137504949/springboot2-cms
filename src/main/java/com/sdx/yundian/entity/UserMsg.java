package com.sdx.yundian.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "xm_user_msg")
public class UserMsg implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "参数错误,姓名")
    @Column(name = "name",columnDefinition = "VARCHAR(50) COMMENT '用户姓名'")
    private String name;

    @NotEmpty(message = "参数错误,邮箱")
    @Column(name = "email",columnDefinition = "VARCHAR(50) COMMENT '邮箱'")
    private String email;
    @NotEmpty(message = "参数错误,手机号码")
    @Column(name = "mobile",columnDefinition = "VARCHAR(12) COMMENT '电话'")
    private String mobile;

    @Column(name = "content",columnDefinition = "TEXT COMMENT '内容'")
    private String content;

    @Column(name = "created_at")
    private Date createdAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
