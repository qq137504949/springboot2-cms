package com.sdx.yundian.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "xm_system")
public class Systems implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",columnDefinition = "varchar(50) comment '系统名称'")
    private String name;
    @Column(name = "ipc",columnDefinition = "varchar(100) comment '备案号'")
    private String ipc;
    @Column(name = "keywords",columnDefinition = "varchar(255) comment '关键字'")
    private String keywords;
    @Column(name = "description",columnDefinition = "varchar(255) comment '描述'")
    private String description;
    @Column(name = "url",columnDefinition = "varchar(100) comment '域名'")
    private String url;
    @Column(name = "email",columnDefinition = "varchar(50) comment '公司邮箱'")
    private String email;
    @Column(name = "mobile",columnDefinition = "varchar(11) comment '联系电话'")
    private String mobile;
    @Column(name = "address",columnDefinition = "varchar(200) comment '联系地址'")
    private String address;
    @Column(name = "statistics",columnDefinition = "varchar(255) comment '统计代码'")
    private String statistics;
    @Column(name = "logo",columnDefinition = "varchar(255) comment 'LOGO'")
    private String logo;
    @Column(name = "company",columnDefinition = "varchar(20) comment '公司名称'")
    private String company;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

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

    public String getIpc() {
        return ipc;
    }

    public void setIpc(String ipc) {
        this.ipc = ipc;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
