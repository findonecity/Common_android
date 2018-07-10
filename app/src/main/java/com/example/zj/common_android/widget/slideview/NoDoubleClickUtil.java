package com.example.zj.common_android.widget.slideview;

/**
 * 防止重复点击工具类
 * Created by ZhangXiaoWei on 2016/5/31.
 */

public class NoDoubleClickUtil {
    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
