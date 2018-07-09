package com.example.zj.common_android.me

import com.example.zj.common_android.R
import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.base.BaseFragment

/**
 * Created by zj on 2018/7/9.
 * is use for:
 */
class MeFragment : BaseFragment<MePresenterImpl>(), BaseContract.MeView {
    override fun getLayout(): Int {
        return R.layout.fragment_me
    }

    override fun initEventAndData() {
    }

    override fun createPresenter() {
        mPresenter = MePresenterImpl()
    }
}