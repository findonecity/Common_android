package com.example.zj.common_android.main

import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.base.BaseView

/**
 * Created by zj on 2018/7/9.
 * is use for:
 */
class MainPresenterImpl : BaseContract.MainPresenter {
    override fun attachView(view: BaseView?) {
        mView = view as BaseContract.MainView
    }

    private var mView: BaseContract.MainView? = null

    override fun detachView() {
        mView = null
    }
}