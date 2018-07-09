package com.example.zj.common_android.record

import com.example.zj.common_android.R
import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.base.BaseFragment

/**
 * Created by zj on 2018/7/9.
 * is use for:
 */
class RecordFragment: BaseFragment<RecordPresenterImpl>(), BaseContract.RecordView {
    override fun getLayout(): Int {
        return R.layout.fragment_record
    }

    override fun initEventAndData() {
    }

    override fun createPresenter() {
        mPresenter = RecordPresenterImpl()
    }
}