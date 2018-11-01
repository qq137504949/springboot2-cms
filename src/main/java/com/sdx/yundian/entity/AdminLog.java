package com.sdx.yundian.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "xm_admin_log")
public class AdminLog implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content", columnDefinition = "varchar(100) comment '日志内容'")
    private String content;
    @Column(name = "admin_id", columnDefinition = "int(11) default 0 comment '管理员ID'")
    private Integer adminId;
    @Column(name = "admin_name", columnDefinition = "varchar(20) comment '管理员名字'")
    private String adminName;
    @Column(name = "ip", columnDefinition = "varchar(20) comment 'IP地址'")
    private String ip;

    @Column(name = "created_at")
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
