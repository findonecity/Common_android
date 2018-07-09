package com.example.zj.common_android.base


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.*
import android.widget.Toast
import com.example.zj.common_android.R
import com.example.zj.common_android.net.ProgressDialogHandler
import kotlinx.android.synthetic.main.toast.view.*

/**
 * Created by andy on 17-6-20.
 */

abstract class BaseFragment<T : BasePresenter<BaseView>> : Fragment(), BaseView {

    protected var mPresenter: T? = null
    protected var rootView: View? = null
    var loadDialog: ProgressDialogHandler? = null
    var toast: Toast? = null
    var toastView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater!!.inflate(getLayout(), null)
        initFontScale()

        createPresenter()
        mPresenter?.attachView(this)

        loadDialog = ProgressDialogHandler(context, null, true)
        initEventAndData()
        return rootView
    }

    private fun initFontScale() {
        var configuration = resources.configuration;
        configuration.fontScale = 1.0F
        //0.85 小, 1 标准大小, 1.15 大，1.3 超大 ，1.45 特大
        var metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        activity?.baseContext?.resources?.updateConfiguration(configuration, metrics);
    }

    protected abstract fun getLayout(): Int

    protected abstract fun initEventAndData()

    protected abstract fun createPresenter()

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun closeLoading() {
        dismissProgressDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter!!.detachView()
    }

    override fun beginActivity(goToClass: Class<*>, bundle: Bundle, requestCode: Int) {
        var intent = Intent(activity, goToClass)
        intent.putExtras(bundle)
        if (requestCode == -1000) {
            startActivity(intent)
        } else {
            startActivityForResult(intent, requestCode)
        }
    }

    fun backbtn(v: View) {
        activity?.finish()
    }


    override fun showCustomToast(msg: String) {
        if (toast == null || toastView == null) {
            toastView = LayoutInflater.from(activity).inflate(R.layout.toast, null)
            toastView!!.toast_msg.text = msg
            toast = Toast(activity)
            toast!!.setGravity(Gravity.CENTER, 0, 0)
            toast!!.duration = Toast.LENGTH_SHORT
            toast!!.view = toastView
        } else {
            toastView!!.toast_msg.text = msg
        }
        toast!!.show()
    }

    fun showProgressDialog() {
        loadDialog?.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG)?.sendToTarget()

    }

    fun dismissProgressDialog() {
        loadDialog?.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)?.sendToTarget()
        loadDialog = null
    }

}
