package com.example.zj.common_android.base

/**
 * Created by zhengjie on 17-10-30.
 * MVP框架的简单封装 P处理层
 */
interface BasePresenter<T : BaseView> {

    fun attachView(view: BaseView?)

    fun detachView()
}