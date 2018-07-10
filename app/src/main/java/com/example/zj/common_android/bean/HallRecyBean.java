package com.example.zj.common_android.bean;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by zj on 2018/7/10.
 * is use for:
 */

public class HallRecyBean implements Serializable{
    private String title;
    private String status;
    private String time;
    private Integer type;

    public HallRecyBean( String title, String status, String time, int type) {
        this.title =title;
        this.status =status;
        this.time = time;
        this.type =type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
