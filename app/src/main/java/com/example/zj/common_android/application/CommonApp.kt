package com.example.zj.common_android.application

import android.content.Context
import android.support.multidex.MultiDexApplication
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Created by zj on 2018/7/2.
 * is use for: 初始化app
 */
class CommonApp : MultiDexApplication() {
    //单例
    var SCREEN_WIDTH = -1
    var SCREEN_HEIGHT = -1
    var DIMEN_RATE = -1.0f
    var DIMEN_DPI = -1
    companion object {
        var instanceTmp: CommonApp? = null
        fun getInstance(): CommonApp? {
            if (instanceTmp == null) {
                instanceTmp = CommonApp()
            }
            return instanceTmp
        }
    }

    override fun onCreate() {
        super.onCreate()
        instanceTmp = this

        getScreenSize()
    }

    //初始化屏幕宽高
    private fun getScreenSize() {
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        val display = windowManager.defaultDisplay
        display.getMetrics(dm)
        DIMEN_RATE = dm.density / 1.0f
        DIMEN_DPI = dm.densityDpi
        SCREEN_WIDTH = dm.widthPixels
        SCREEN_HEIGHT = dm.heightPixels
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            val t = SCREEN_HEIGHT
            SCREEN_HEIGHT = SCREEN_WIDTH
            SCREEN_WIDTH = t
        }
    }
}