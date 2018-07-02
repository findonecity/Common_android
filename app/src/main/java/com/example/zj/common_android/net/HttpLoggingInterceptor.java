package com.example.zj.common_android.net;

import android.util.Log;

import com.example.zj.common_android.application.CommonApp;
import com.example.zj.common_android.util.SharedPreferencesManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 请求url，method，header，请求时间和返回参数统一处理
 */

public class HttpLoggingInterceptor implements Interceptor {
    public static final String TAG = "LogInterceptor.java";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        //the request url
        String url = request.url().toString();
        Log.e(TAG,"url="+url);
        //the request method
        String method = request.method();

        long t1 = System.nanoTime();

        Log.e(TAG, String.format(Locale.getDefault(),"Sending %s request [url = %s]",method,url));
        Log.e(TAG, String.format(Locale.getDefault(), "%s %s", method, request.headers()));

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        //the response time
        Log.e(TAG, String.format(Locale.getDefault(),"Received response for [url = %s] in %.1fms",url, (t2-t1)/1e6d));

        if (response.headers().get("Set-Cookie")!=null){
            String[] strings= response.headers().get("Set-Cookie").split(";");
            SharedPreferencesManager.getInstance().putData(CommonApp.Companion.getInstance(),"user","cookie",strings[0]);
        }


        //the response state
     //   Log.d(TAG,String.format(Locale.CHINA,"Received response is %s ,message[%s],code[%d]",response.isSuccessful()?"success":"fail",response.message(),response.code()));

        //the response data
        ResponseBody body = response.body();
        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.defaultCharset();
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String bodyString = buffer.clone().readString(charset);
        Log.e("tagResponse", String.format("Received response json string [%s]",bodyString));
        return response;
    }


}
