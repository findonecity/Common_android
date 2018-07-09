package com.example.zj.common_android.recharge

import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.base.BaseView

/**
 * Created by zj on 2018/7/9.
 * is use for:
 */
class RechargePresenterImpl : BaseContract.RechargePresenter{
    private var mView: BaseContract.RechargeView? = null
    override fun attachView(view: BaseView?) {
        mView = view as BaseContract.RechargeView
    }

    override fun detachView() {
        mView = null
    }
}