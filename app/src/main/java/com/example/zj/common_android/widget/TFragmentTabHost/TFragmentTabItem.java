package com.example.zj.common_android.widget.TFragmentTabHost;

/**
 * Created by djtao on 2016/6/6.
 */

public class TFragmentTabItem {
    //选项卡的名称
    private String name;
    //图标的资源文件
    private int iconResourceId;
    //对应内容
    private Class fragment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
