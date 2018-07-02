package com.example.zj.common_android.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by ZhengJie on 2018/2/1.
 * is use for:
 */

public class MyRecyclerView extends RecyclerView{
    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //防止数据显示不全
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
