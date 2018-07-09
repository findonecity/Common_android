package com.example.zj.common_android.base


/**
 * Created by zhengjie on 17-10-30.
 * mvp模式c层
 */

class BaseContract {
    //test
    interface TestView : BaseView


    interface TestPresenter : BasePresenter<BaseView>

    /**
     * 首页
     */
    interface MainView : BaseView

    interface MainPresenter:BasePresenter<BaseView>

    /**
     * 购彩大厅
     */
    interface HallView : BaseView

    interface HallPresenter: BasePresenter<BaseView>

    /**
     * 开奖记录
     */
    interface RecordView : BaseView

    interface RecordPresenter: BasePresenter<BaseView>

    /**
     * 充值提现
     */
    interface RechargeView : BaseView

    interface RechargePresenter: BasePresenter<BaseView>

    /**
     * 我的
     */
    interface MeView : BaseView

    interface MePresenter: BasePresenter<BaseView>
}