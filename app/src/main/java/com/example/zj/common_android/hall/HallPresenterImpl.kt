package com.example.zj.common_android.hall

import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.base.BaseView

/**
 * Created by zj on 2018/7/9.
 * is use for: 购彩大厅
 */
class HallPresenterImpl : BaseContract.HallPresenter{
    private var mView: BaseContract.HallView? = null
    override fun attachView(view: BaseView?) {
        mView = view as BaseContract.HallView
    }

    override fun detachView() {
        mView = null
    }
}