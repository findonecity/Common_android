package com.example.zj.common_android.widget.CustomPopwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


/**
 * Created by ZhengJie on 2018/1/10.
 * is use for:
 */

public class PopWindow extends PopupWindow {
    private Context mContext;

    private View view;

    public PopWindow(Context mContext,View view) {
        this.mContext=mContext;
        this.view=view;

        // 设置外部可点击
//        this.setOutsideTouchable(false);

    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(false);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }
}
