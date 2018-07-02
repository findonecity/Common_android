package com.example.zj.common_android.base


/**
 * Created by zhengjie on 17-10-30.
 * mvp模式c层
 */

class BaseContract {
    //test
    interface TestView : BaseView


    interface TestPresenter : BasePresenter<BaseView>

}