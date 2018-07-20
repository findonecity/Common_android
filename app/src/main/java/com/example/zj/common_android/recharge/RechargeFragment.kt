package com.example.zj.common_android.recharge

import com.example.zj.common_android.R
import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_recharge.*

/**
 * Created by zj on 2018/7/9.
 * is use for: 充值提现
 */
class RechargeFragment : BaseFragment<RechargePresenterImpl>(), BaseContract.RechargeView {
    override fun getLayout(): Int {
        return R.layout.fragment_recharge
    }

    override fun initEventAndData() {
    }

    override fun createPresenter() {
        mPresenter = RechargePresenterImpl()
    }
}