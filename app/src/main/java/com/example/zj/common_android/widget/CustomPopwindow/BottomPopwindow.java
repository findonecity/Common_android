package com.example.zj.common_android.widget.CustomPopwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.zj.common_android.R;


/**
 * Created by zhengjie on 2017-12-14.
 */

public class BottomPopwindow extends PopupWindow{

    private boolean focus;
    private Context mContext;

    private View view;

    public BottomPopwindow(Context mContext,View view,boolean focus) {
        this.mContext=mContext;
        this.view=view;
        this.focus=focus;

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框

    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(focus);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.BottomToTopAnim);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode()==66||event.getKeyCode()==18){
            switch (event.getAction()){
                case KeyEvent.ACTION_DOWN:
                    break;
                case KeyEvent.ACTION_UP:
                    break;
            }
            return true;
        }
        return true;
    }
}
