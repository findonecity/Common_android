package com.example.zj.common_android.widget.customToast

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.example.zj.common_android.R
import kotlinx.android.synthetic.main.toast.view.*

/**
 * Created by a12345 on 17/6/29.
 */
class CustomToast {
    @SuppressLint("WrongConstant")
    fun showToast(mContext: Context, msg: String) {
        val toastRoot = LayoutInflater.from(mContext).inflate(R.layout.toast,null)
        toastRoot.toast_msg.text = msg
        val toast = Toast(mContext)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.duration = 2000
        toast.view = toastRoot
        toast.show()
    }
}