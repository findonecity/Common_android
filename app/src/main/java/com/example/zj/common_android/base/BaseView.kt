package com.example.zj.common_android.base

import android.os.Bundle


/**
 * Created by zhengjie on 17-10-30.
 * 一般的Activity中要用到View操作无非是显示加载框、影藏加载框、显示出错信息、显示当数据为空的时候的view之类的
 */

interface BaseView {

    fun showToast(message: String)
    fun showCustomToast(message: String)
    fun showLoading()
    fun closeLoading()
    fun beginActivity(goToClass: Class<*>, bundle: Bundle = Bundle(), requestCode: Int = -1000)

}
