package com.example.zj.common_android.main

import android.Manifest
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.zj.common_android.R
import com.example.zj.common_android.base.BaseActivity
import com.example.zj.common_android.base.BaseContract
import com.example.zj.common_android.hall.HallFragment
import com.example.zj.common_android.me.MeFragment
import com.example.zj.common_android.recharge.RechargeFragment
import com.example.zj.common_android.record.RecordFragment
import com.example.zj.common_android.util.permission.PermissionListener
import com.example.zj.common_android.util.permission.PermissionManager
import com.example.zj.common_android.widget.TFragmentTabHost.TFragmentTabController
import com.example.zj.common_android.widget.TFragmentTabHost.TFragmentTabHost
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenterImpl>(), BaseContract.MainView, TFragmentTabController.TFragmentTabControlable {
    private val fragmentArray = arrayOf(HallFragment::class.java,RecordFragment::class.java,RechargeFragment::class.java,MeFragment::class.java)
    private val mImageViewArray = intArrayOf(R.drawable.selector_main_nvi_01,R.drawable.selector_main_nvi_02,R.drawable.selector_main_nvi_03,R.drawable.selector_main_nvi_04)
    private val mTextViewArray = arrayOf("购彩大厅","开奖记录","充值提现","我的")
    var tabhostNumber: TextView? = null
    private var permission: PermissionManager? = null
    private var tabController: TFragmentTabController? = null
    override fun getActivity(): FragmentActivity {
        return this
    }

    override fun getTabHost(): TFragmentTabHost {
        return tabhost
    }

    override fun getTabCount(): Int {
        return fragmentArray.size
    }

    override fun getTabView(tabIndex: Int): View? {
        if (tabIndex == 2) {
            return getTabItemView(2)
        }
        return null
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private fun getTabItemView(index: Int): View {
        val view = LayoutInflater.from(this).inflate(R.layout.item_main_tab, null)
        val img = view.findViewById<ImageView>(R.id.item_main_tab_img)
        img.setImageResource(mImageViewArray[index])

        val llTabHostNumber = view.findViewById<LinearLayout>(R.id.ll_tabhost_number)
        llTabHostNumber.visibility = View.VISIBLE

        if (index == 2) {
            tabhostNumber = view.findViewById(R.id.tabhost_number)
        }

        val tv = view.findViewById<TextView>(R.id.item_main_tab_tv)
        tv.text = mTextViewArray[index]

        return view
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initEventAndData() {
        tabhost?.setup(this, supportFragmentManager, R.id.amMainContent)

        tabController = TFragmentTabController(this)
        tabController?.setupTab(mImageViewArray, mTextViewArray, fragmentArray)

        //权限
        getPermission()

    }

    companion object {
        val WRITE_EXTERNAL_STORAGE = 1
    }

    private fun getPermission() {
        permission = PermissionManager.with(this)
                //添加权限请求码
                .addRequestCode(MainActivity.WRITE_EXTERNAL_STORAGE)
                //设置权限，可以添加多个权限
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                //设置权限监听器
                .setPermissionsListener(object : PermissionListener {

                    override fun onGranted() {
                        //当权限被授予时调用
//                        Toast.makeText(this@MainActivity, "Camera Permission granted",Toast.LENGTH_LONG).show()
                    }

                    override fun onDenied() {
                        //用户拒绝该权限时调用
                        showToast("存储读写权限被拒绝")
                    }

                    override fun onShowRationale(permissions: Array<String>) {
                        //当用户拒绝某权限时并点击`不再提醒`的按钮时，下次应用再请求该权限时，需要给出合适的响应（比如,给个展示对话框来解释应用为什么需要该权限）
                        Snackbar.make(amMainContent, "程序需要存储部分信息到您的存储空间才能正常运行", Snackbar.LENGTH_INDEFINITE)
                                .setAction("获取权限", {
                                    //必须调用该`setIsPositive(true)`方法
                                    permission?.setIsPositive(true)
                                    permission?.request()
                                }).show()
                    }
                })
                //请求权限
                .request()
    }

    override fun createPresenter() {
        mPresenter = MainPresenterImpl()
    }

    private var exitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) run {
                Toast.makeText(applicationContext, "再按一次退出程序！",
                        Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
