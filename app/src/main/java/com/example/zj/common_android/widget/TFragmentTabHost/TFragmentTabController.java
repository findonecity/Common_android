package com.example.zj.common_android.widget.TFragmentTabHost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.zj.common_android.main.MainActivity;
import com.example.zj.common_android.R;
import com.example.zj.common_android.application.CommonApp;
import com.example.zj.common_android.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djtao on 2016/6/6.
 */

public class TFragmentTabController {


    public interface TFragmentTabControlable {
        //获取FragmentActivity实例
        public FragmentActivity getActivity();

        //获取FragmentTabHost实例
        public TFragmentTabHost getTabHost();

        //获取TabCount
        public int getTabCount();

        //获取tabView，如果返回null，调用默认的显示view
        public View getTabView(int tabIndex);
    }

    //attatch到的activity
    private FragmentActivity mActivity;

    private LayoutInflater mLayoutInflater;
    //定义FragmentTabHost对象
    private TFragmentTabHost mTabHost;
    //选项卡列表
    private List<TFragmentTabItem> tabItems;
    //选项卡的个数
    private int mTabCount;
    //选项卡控制接口
    private TFragmentTabControlable instance;

    public TFragmentTabController(TFragmentTabControlable instance) {
        this.instance = instance;
    }

    /**
     * 初始化tab选项卡
     *
     * @param tabSelectorStyle tab选项卡选择时图片更换效果的selector的列表
     * @param textViewArray    tab选项卡对应的文字列表
     * @param fragmentArray    tab选项卡对应的fragment列表
     */
    public void setupTab(int[] tabSelectorStyle, String[] textViewArray, Class[] fragmentArray) {
        if (tabSelectorStyle == null || textViewArray == null || fragmentArray == null) {
            throw new NullPointerException();
        }
        mActivity = instance.getActivity();
        mTabHost = instance.getTabHost();
        mLayoutInflater = LayoutInflater.from(mActivity);

        mTabCount = instance.getTabCount();
        tabItems = new ArrayList<>();
        if (mTabCount <= 0) {
            return;
        }
        for (int i = 0; i < mTabCount; i++) {
            TFragmentTabItem tabItem = new TFragmentTabItem();
            tabItem.setName(textViewArray[i]);
            tabItem.setFragment(fragmentArray[i]);
            tabItem.setIconResourceId(tabSelectorStyle[i]);
            tabItems.add(tabItem);
        }

        Bundle bundle = new Bundle();
        bundle.putString("tag", "收到参数");

        for (int i = 0; i < mTabCount; i++) {
            TFragmentTabItem tabItem = tabItems.get(i);
            if (tabItem == null)
                break;
            TabHost.TabSpec tab = mTabHost.newTabSpec(tabItem.getName()).setIndicator(setUpTabItemView(i));
            mTabHost.addTab(tab, tabItem.getFragment(), bundle);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.white);
            mTabHost.getTabWidget().setDividerDrawable(null);
//            if (SharedPreferencesManager.getInstance().getData(CommonApp.Companion.getInstance(), "user", "userId").isEmpty()) {
//                if (i != 0) {
//                    mTabHost.getTabWidget().getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(CommonApp.Companion.getInstance(), MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            CommonApp.Companion.getInstance().startActivity(intent);
//                        }
//                    });
//                }
//            }


        }
    }

    public void setArgment(int tag, Bundle bundle) {

    }

    protected View setUpTabItemView(int index) {
        //   View v=instance.getTabView(index);

        return getDefaultTabItemView(index);


    }

    protected View getDefaultTabItemView(final int index) {
        final TFragmentTabItem tabItem = tabItems.get(index);
        final View view = mLayoutInflater.inflate(R.layout.item_main_tab, null);
        ImageView img = view.findViewById(R.id.item_main_tab_img);

        img.setImageResource(tabItem.getIconResourceId());


        TextView tv = view.findViewById(R.id.item_main_tab_tv);
        tv.setText(tabItem.getName());
        return view;
    }

}
