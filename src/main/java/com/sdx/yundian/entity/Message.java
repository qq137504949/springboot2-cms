package com.sdx.yundian.entity;

import java.io.Serializable;

public class Message implements Serializable {
    private String data;
    private Integer code;

    public Message( String data, Integer code) {
        this.data = data;
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
