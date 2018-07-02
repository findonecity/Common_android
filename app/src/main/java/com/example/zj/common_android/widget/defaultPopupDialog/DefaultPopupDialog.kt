package com.example.zj.common_android.widget.defaultPopupDialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.graphics.drawable.BitmapDrawable
import com.example.zj.common_android.R
import kotlinx.android.synthetic.main.dialog_default_popup.*


/**
 * Created by a12345 on 17/6/29.
 */
class DefaultPopupDialog(private val mContext: Context) : Dialog(mContext, R.style.dialog), View.OnClickListener {
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_ok -> {
                onConfirmListener?.confirm()
                this.dismiss()
            }
            R.id.btn_cancel -> {
                onCancelListener?.cancel()
                this.dismiss()
            }
        }
    }

    private var onConfirmListener: OnConfirmListener? = null
    private var onCancelListener: OnCancelListener? = null

    init {
        setContentView(R.layout.dialog_default_popup)
        window!!.setBackgroundDrawable(BitmapDrawable())
        window.setWindowAnimations(R.style.LeftToRight)
        var lp = window.attributes

        lp.width = getPhoneWidthAndHeight()[0] / 5 * 4
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        setCanceledOnTouchOutside(false)
        btn_ok.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        btn_ok.isFocusable = true
        btn_ok.isFocusableInTouchMode = true
        btn_ok.requestFocus()
        btn_ok.requestFocusFromTouch()
        this.window.setBackgroundDrawableResource(android.R.color.transparent)

    }

    fun setOnConfirmListener(onConfirmListener: OnConfirmListener?) {
        this.onConfirmListener = onConfirmListener
    }

    fun getOnConfirmListener(): OnConfirmListener? {
        return onConfirmListener
    }

    fun setOnCancelListener(onCancelListener: OnCancelListener?) {
        this.onCancelListener = onCancelListener
    }

    fun getOnCancelListener(): OnCancelListener? {
        return onCancelListener
    }

    fun getPhoneWidthAndHeight(): IntArray {
        val wm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width
        val height = wm.defaultDisplay.height
        return intArrayOf(width, height)
    }

    fun setMsg(msg: String) {
        popup_msg.text = msg
    }

    fun setImage(r: Int) {
        if (r == -1) {
            popup_iv.visibility = View.GONE
        } else {
            popup_iv.setImageResource(r)
        }
    }

    fun setBtnCancelText(msg: String) {
        btn_cancel.text = msg
    }

    fun setBtnOkText(msg: String) {
        btn_ok.text = msg
    }

    fun setTextBold(b: Boolean) {
        popup_msg.paint.isFakeBoldText = b
    }

    fun setBtnCancelVisibility(b: Boolean) {
        if (b) {
            btn_cancel.visibility = View.VISIBLE
        } else {
            btn_cancel.visibility = View.GONE
        }
    }


}