package com.example.zj.common_android.widget.bottomMenuDialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.example.zj.common_android.R
import kotlinx.android.synthetic.main.dialog_buttom_menu.*


/**
 * Created by andy on 17-6-22.
 */

class BottomMenuDialog(mContext: Context) : Dialog(mContext, R.style.buttom_menu_dialog), View.OnClickListener {

    private var onItemChooseListener: OnItemChooseListener? = null

    init {
        setContentView(R.layout.dialog_buttom_menu)
        onItemChooseListener = mContext as OnItemChooseListener
        window.setWindowAnimations(R.style.BottomToTopAnim)
        var lp = window.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.BOTTOM

        setCanceledOnTouchOutside(true)

        dbmBtnCancel.setOnClickListener(this)
        dbmBtnLogout.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.dbmBtnCancel -> this.dismiss()
            R.id.dbmBtnLogout -> {
                onItemChooseListener!!.onClick()
            }
        }
    }

    interface OnItemChooseListener {
        fun onClick()
    }

}
