package com.example.zj.common_android.hall

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.zj.common_android.R
import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.base.BaseFragment
import com.example.zj.common_android.bean.BannerBean
import com.example.zj.common_android.bean.HallRecyBean
import com.example.zj.common_android.widget.MyRecyclerView
import com.example.zj.common_android.widget.slideview.SlideView
import com.sunfusheng.marqueeview.MarqueeView
import kotlinx.android.synthetic.main.fragment_hall.*


/**
 * Created by zj on 2018/7/9.
 * is use for:
 */
class HallFragment : BaseFragment<HallPresenterImpl>(),BaseContract.HallView{
    val imageUrls = mutableListOf<String>()
    private lateinit var slideView: SlideView
    private lateinit var marqueeView:MarqueeView
    private lateinit var recyclerView:MyRecyclerView
    private var list:MutableList<HallRecyBean> = ArrayList()
    //设置广告业轮播图
    override fun setBannerImg() {
        val urls:MutableList<BannerBean> = ArrayList()
        imageUrls.clear()
//        urls.mapTo(imageUrls) {  }  此处拼接地址
        slideView.setImageUris(imageUrls)
    }


    override fun getLayout(): Int {
        return R.layout.fragment_hall
    }

    override fun initEventAndData() {
        //广播轮播
        slideView = rootView?.findViewById(R.id.svBanner)!!
        marqueeView = rootView?.findViewById(R.id.marqueeView)!!
        recyclerView = rootView?.findViewById(R.id.recyclerView)!!

        val info:MutableList<String> = ArrayList()
        info.add("1.1.恭喜用户大苗是煞笔")
        info.add("2.恭喜用户德坤是煞笔！")
        info.add("3.恭喜用户大苗是煞笔")
        info.add("4.恭喜用户大苗是煞笔")
        info.add("5.恭喜用户大苗是煞笔")
        info.add("6.恭喜用户大苗是煞笔")
        marqueeView.startWithList(info)

        marqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out)

        list.add(HallRecyBean("标题1","热门开奖","内容1",1))
        list.add(HallRecyBean("标题2","热门开奖","内容1",3))
        list.add(HallRecyBean("标题3","热门开奖","内容1",2))
        list.add(HallRecyBean("标题4","热门开奖","内容1",1))
        list.add(HallRecyBean("标题5","热门开奖","内容1",3))
        list.add(HallRecyBean("标题6","热门开奖","内容1",1))

        val mAdapter = HallAdapter(activity as Context, list)
        recyclerView.layoutManager = GridLayoutManager(activity as Context,2)

        recyclerView.adapter = mAdapter
    }

    override fun createPresenter() {
        mPresenter = HallPresenterImpl()
    }
}