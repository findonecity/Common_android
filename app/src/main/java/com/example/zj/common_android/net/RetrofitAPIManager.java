package com.example.zj.common_android.net;

import com.example.zj.common_android.util.Constant;

import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhengjie on 2017/10/30.
 * is use for:处理请求的封装
 */

public class RetrofitAPIManager {
    private ApiService mApiService;
    private String ver;
    private static RetrofitAPIManager mInstance;
    private static final int CONN_TIMEOUT = 10;//连接超时时间,单位  秒
    private static final int READ_TIMEOUT = 20;//读写超时时间,单位  秒

    private Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    RequestBody body;

    private RetrofitAPIManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.url.BASE)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(genericClient())
                .build();

        mApiService = retrofit.create(ApiService.class);
        ver = Constant.url.Ver;
    }

    public static RetrofitAPIManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitAPIManager.class) {
                mInstance = new RetrofitAPIManager();
            }
        }
        return mInstance;
    }

    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new ParamsInterceptor())
                .addNetworkInterceptor(new HttpLoggingInterceptor())
                .build();
        return httpClient;
    }

    /**
     * 测试
     * @param subscriber
     */
    public void test(Subscriber<BaseResponse<String>> subscriber) {
    JSONObject jsonObject = new JSONObject();
    try {
        Observable observable=mApiService.test(getRequestBody(jsonObject.toString()));
        toSubscribe(observable, subscriber);
    } catch (Exception e) {
        e.printStackTrace();
    }

}

    // * subscribeOn(): 指定 subscribe() 发生在 IO 线程
    // * observeOn(): 指定 Subscriber 的回调发生在主线程
    //添加线程管理并订阅
    private void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public RequestBody getRequestBody(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return body;
    }
}
