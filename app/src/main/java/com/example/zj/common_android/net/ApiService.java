package com.example.zj.common_android.net;



import com.example.zj.common_android.util.Constant;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 *请求地址
 */

public interface ApiService {
    @POST(Constant.url.TEST)
    Observable<BaseResponse<String>> test(@Body RequestBody requestBody);
}

