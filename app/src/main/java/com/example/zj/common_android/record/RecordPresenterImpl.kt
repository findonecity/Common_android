package com.example.zj.common_android.record

import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.base.BaseView

/**
 * Created by zj on 2018/7/9.
 * is use for: 开奖记录
 */
class RecordPresenterImpl : BaseContract.RecordPresenter {
    private var mView: BaseContract.RecordView? = null
    override fun attachView(view: BaseView?) {
        mView = view as BaseContract.RecordView
    }

    override fun detachView() {
        mView = null
    }
}