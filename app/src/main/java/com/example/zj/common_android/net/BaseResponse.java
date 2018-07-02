package com.example.zj.common_android.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zj on 2018/1/12.
 * 数据返回
 */

public class BaseResponse<T> {
    @SerializedName("success")
    public Boolean success;
    @SerializedName("msg")
    public String msg;
    @SerializedName("isLogin")
    public String isLogin;
    @SerializedName("code")
    public  int code;
    @SerializedName("data")
    public T data;

}
