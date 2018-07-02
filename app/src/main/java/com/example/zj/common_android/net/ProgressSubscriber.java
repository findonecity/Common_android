package com.example.zj.common_android.net;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.example.zj.common_android.application.CommonApp;
import com.example.zj.common_android.util.SharedPreferencesManager;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by zhengjie on 2017/11/1.
 * SubscriberOnNextListener 用于处理统一返回
 * SubscriberOnNextListener1 用于处理当有前端界面判断err时调动
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{

    private View mLayout;
    private SubscriberOnNextListener mListener;
    private SubscriberOnNextListener1 mListener1;
    private Activity mContext;
    private ProgressDialogHandler mHandler;
    private int tag;
    private int requestCode;


    public ProgressSubscriber(SubscriberOnNextListener listener, Activity context, int tag, int requestCode, View mLayout){
        this.mListener = listener;
        this.mContext = context;
        this.tag=tag;
        this.mLayout=mLayout;
        this.requestCode=requestCode;
        if (tag==1){
            mHandler = new ProgressDialogHandler(context,this,true);
        }
    }

    public ProgressSubscriber(SubscriberOnNextListener1 listener, Activity context, int tag, int requestCode, View mLayout){
        this.mListener1 = listener;
        this.mContext = context;
        this.tag=tag;
        this.requestCode=requestCode;
        this.mLayout=mLayout;
        if (tag==1){
            mHandler = new ProgressDialogHandler(context,this,true);
        }
    }

    private void showProgressDialog(){
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    //统一拦截rxJava onError情况
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(CommonApp.Companion.getInstance(), "连接超时！！！", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(CommonApp.Companion.getInstance(), "无法连接到服务器，请检查网络！！！", Toast.LENGTH_SHORT).show();
        }else if (e instanceof FileNotFoundException){
            Toast.makeText(CommonApp.Companion.getInstance(), "服务器搬家了，请稍后再试！！！", Toast.LENGTH_SHORT).show();
        }else {
            e.printStackTrace();
            Toast.makeText(CommonApp.Companion.getInstance(), "服务器错误，请联系管理人员！！！" , Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog();
        if (tag==5){
           // SharedPreferencesManager.getInstance().out(mContext,"user");
            //指向登录界面
        }
    }

    //请求成功并且数据成功解析后 对code的判断
    @Override
    public void onNext(T t) {
        BaseResponse baseResponse= (BaseResponse) t;
            if (baseResponse.success){
                if (mListener != null){
                    mListener.onNext(baseResponse,requestCode);
                }
                if (mListener1 != null){
                    mListener1.onNext(baseResponse,requestCode);
                }
            }else {
                if (baseResponse.code==880104){//token过期的code码，根据项目具体定义
                    SharedPreferencesManager.getInstance().out(mContext,"user");
                    //跳转向登录界面
                    Toast.makeText(mContext,"登录已过期，请重新登录", Toast.LENGTH_SHORT).show();
                }else {
                    if (mListener1 != null){
                        mListener1.err(baseResponse,requestCode);
                    }
                }
            }
        }

    @Override
    public void onCancelProgress(){
        if (!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
