package com.example.zj.common_android.widget.bottomPhotoDialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.example.zj.common_android.R
import kotlinx.android.synthetic.main.dialog_buttom_photo.*


/**
 * Created by andy on 17-6-22.
 */

class BottomPhotoDialog(mContext: Context) : Dialog(mContext, R.style.buttom_menu_dialog), View.OnClickListener {

    private var onItemChooseListener: OnItemChooseListener? = null

    init {
        setContentView(R.layout.dialog_buttom_photo)
        window.setWindowAnimations(R.style.BottomToTopAnim)
        var lp = window.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.BOTTOM

        setCanceledOnTouchOutside(true)

        mBtnTakePhoto.setOnClickListener(this)
        mBtnChoosePhoto.setOnClickListener(this)
        mBtnCancel.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.mBtnTakePhoto ->{
                onItemChooseListener?.takePhoto()
                this.dismiss()
            }
            R.id.mBtnChoosePhoto ->{
                onItemChooseListener?.choosePhoto()
                this.dismiss()
            }
            R.id.mBtnCancel ->{
                this.dismiss()
            }
        }
    }

    interface OnItemChooseListener {
        fun takePhoto()
        fun choosePhoto()
    }

    fun setOnItemChooseListener(onItemChooseListener: OnItemChooseListener){
        this.onItemChooseListener = onItemChooseListener
    }

    fun getOnItemChooseListener(): OnItemChooseListener?{
        return onItemChooseListener
    }

}
