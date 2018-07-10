package com.example.zj.common_android.bean;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by zj on 2018/7/10.
 * is use for: 首页广告轮播
 */

public class BannerBean implements Serializable{
    public String urlPath;

    public BannerBean(@NotNull String urlPath) {
        this.urlPath = urlPath;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}
