package com.example.zj.common_android.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.example.zj.common_android.widget.defaultPopupDialog.DefaultPopupDialog
import com.example.zj.common_android.widget.defaultPopupDialog.OnCancelListener
import com.example.zj.common_android.widget.defaultPopupDialog.OnConfirmListener
import com.example.zj.common_android.R
import com.example.zj.common_android.net.ProgressDialogHandler
import com.example.zj.common_android.util.ChangeStatusBarDark
import com.example.zj.common_android.widget.customToast.CustomToast


/**
 * Created by zhengjie on 17-10-30.
 * 基类Activity的封装
 */

abstract class BaseActivity<T : BasePresenter<BaseView>> : AppCompatActivity(), BaseView {

    protected open var mPresenter: T? = null
    protected open var mContext: Activity? = null

    var loadDialog: ProgressDialogHandler? = null

//    abstract val  StatusBarUtil: Any

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        //        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        //锁定竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

//        val window = window
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            //透明导航栏
//            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //透明状态栏/导航栏
//            window.statusBarColor = Color.TRANSPARENT
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏/导航栏
            //            window.getStatusBarColor() = Color.TRANSPARENT;
            //            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //设置状态栏颜色
            window.statusBarColor = resources.getColor(R.color.blue_right)

        }

        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        initFontScale()
        mContext = this
////     StatusBarUtil.setColor(mContext, Color.parseColor("#1886e3"))//设置状态栏背景颜色为appbg
//        StatusBarUtil.setTransparent(mContext)
//         StatusBarCompat.compat(mContext,Color.parseColor("#1886e3"))

        createPresenter()

        mPresenter?.attachView(this)

        loadDialog = ProgressDialogHandler(this, null, true)

        initEventAndData()

    }


    protected abstract fun getLayout(): Int

    protected abstract fun initEventAndData()

    protected abstract fun createPresenter()

    override fun showToast(message: String) {
        showCustomToast(message)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun closeLoading() {
        dismissProgressDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

    private fun initFontScale() {
        var configuration = resources.configuration
        configuration.fontScale = 1.0F
        //0.85 小, 1 标准大小, 1.15 大，1.3 超大 ，1.45 特大
        var metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }

    override fun beginActivity(goToClass: Class<*>, bundle: Bundle, requestCode: Int) {

        var intent = Intent(this, goToClass)
        intent.putExtras(bundle)

        if (requestCode == -1000) {
            startActivity(intent)
        } else {
            startActivityForResult(intent, requestCode)
        }
    }

    fun setStatusBarDark() {
        try {
            ChangeStatusBarDark.setDark(window, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setStatusBarWhite() {
        try {
            ChangeStatusBarDark.setWhite(window, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    open fun backbtn(v: View) {
        this.finish()
    }

    //默认图片的弹框
    override fun showDialog(msg: String, onConfirmListener: OnConfirmListener) {
        val popupDialog = DefaultPopupDialog(this@BaseActivity)
        popupDialog.setMsg(msg)
        popupDialog.setOnConfirmListener(onConfirmListener)
        popupDialog.show()
    }

    //非默认图片的弹框
    override fun showDialog(r: Int, msg: String, onConfirmListener: OnConfirmListener) {
        val popupDialog = DefaultPopupDialog(this@BaseActivity)
        popupDialog.setImage(r)
        popupDialog.setMsg(msg)
        popupDialog.setOnConfirmListener(onConfirmListener)
        popupDialog.show()
    }

    //可设置按钮文字
    override fun showDialog(msg: String, cancelText: String, confirmText: String,
                            onCancelListener: OnCancelListener?, onConfirmListener: OnConfirmListener?) {
        val popupDialog = DefaultPopupDialog(this@BaseActivity)
        if ("" == cancelText) {
            popupDialog.setBtnCancelVisibility(false)
        } else {
            popupDialog.setBtnCancelText(cancelText)
        }
        popupDialog.setBtnOkText(confirmText)
        popupDialog.setMsg(msg)
        popupDialog.setOnConfirmListener(onConfirmListener)
        popupDialog.setOnCancelListener(onCancelListener)
        popupDialog.show()
    }

    //可设置按钮文字 图片
    override fun showDialog(r: Int, msg: String, cancelText: String, confirmText: String,
                            onCancelListener: OnCancelListener?, onConfirmListener: OnConfirmListener?) {
        val popupDialog = DefaultPopupDialog(this@BaseActivity)
        popupDialog.setImage(r)
        popupDialog.setBtnCancelText(cancelText)
        popupDialog.setBtnOkText(confirmText)
        popupDialog.setMsg(msg)
        popupDialog.setOnConfirmListener(onConfirmListener)
        popupDialog.setOnConfirmListener(onConfirmListener)
        popupDialog.show()
    }

    //自定义的toast
    override fun showCustomToast(msg: String) {
        CustomToast().showToast(this, msg)
    }

    fun showProgressDialog() {
        loadDialog?.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG)?.sendToTarget()

    }

    fun dismissProgressDialog() {
        loadDialog?.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)?.sendToTarget()
        loadDialog = null
    }
}
