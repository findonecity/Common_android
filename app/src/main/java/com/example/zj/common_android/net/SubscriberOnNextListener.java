package com.example.zj.common_android.net;


public interface SubscriberOnNextListener {
    void onNext(BaseResponse baseResponse, int requestCode);
    void err(BaseResponse baseResponse, int requestCode);
}
