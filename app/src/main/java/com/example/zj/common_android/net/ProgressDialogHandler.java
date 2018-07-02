package com.example.zj.common_android.net;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zj.common_android.R;


/**
 * Created by zhengjie on 2017/10/28.
 */

public class  ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

   // private SweetAlertDialog pd;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;
    Dialog loadingDialog;
    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog(){
        if (loadingDialog == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
            LinearLayout layout = v.findViewById(R.id.dialog_view);// 加载布局
            // main.xml中的ImageView
            ImageView spaceshipImage = v.findViewById(R.id.img);
            TextView tipTextView = v.findViewById(R.id.tipTextView);// 提示文字
            // 加载动画
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                    context, R.anim.load_animation);
            // 使用ImageView显示动画
            spaceshipImage.startAnimation(hyperspaceJumpAnimation);
            tipTextView.setText("加载中");// 设置加载信息

            loadingDialog= new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity= Gravity.CENTER;
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            loadingDialog.setContentView(layout,layoutParams);// 设置布局
            if (cancelable) {
                loadingDialog.setOnDismissListener(dialog -> {
                    if(mProgressCancelListener!=null)
                        mProgressCancelListener.onCancelProgress();
                });
                loadingDialog.setOnCancelListener(dialogInterface -> {
                    if(mProgressCancelListener!=null)
                        mProgressCancelListener.onCancelProgress();
                });
            }

            if (!loadingDialog.isShowing()) {
               if (!((Activity)context).isFinishing()){
                   loadingDialog.show();
               }else {
                   loadingDialog.dismiss();
               }
            }
        }
    }
    private void dismissProgressDialog(){
        if (loadingDialog != null) {
            if (!((Activity)context).isFinishing()){
                loadingDialog.dismiss();
                loadingDialog = null;
            }

        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}