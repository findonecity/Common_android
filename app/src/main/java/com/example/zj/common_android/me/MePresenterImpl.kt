package com.example.zj.common_android.me

import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.base.BaseView

/**
 * Created by zj on 2018/7/9.
 * is use for: 我的
 */
class MePresenterImpl: BaseContract.MePresenter{
    private var mView: BaseContract.MeView? = null
    override fun attachView(view: BaseView?) {
        mView = view as BaseContract.MeView
    }

    override fun detachView() {
        mView = null
    }
}