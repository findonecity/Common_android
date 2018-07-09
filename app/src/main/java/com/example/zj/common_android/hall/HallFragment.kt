package com.example.zj.common_android.hall

import com.example.zj.common_android.R
import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.base.BaseFragment

/**
 * Created by zj on 2018/7/9.
 * is use for:
 */
class HallFragment : BaseFragment<HallPresenterImpl>(),BaseContract.HallView{
    override fun getLayout(): Int {
        return R.layout.fragment_hall
    }

    override fun initEventAndData() {

    }

    override fun createPresenter() {
        mPresenter = HallPresenterImpl()
    }
}