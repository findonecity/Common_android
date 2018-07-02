package com.example.zj.common_android.net;

import android.util.Log;

import com.example.zj.common_android.application.CommonApp;
import com.example.zj.common_android.util.Constant;
import com.example.zj.common_android.util.SharedPreferencesManager;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 公共参数统一处理
 */

public class ParamsInterceptor implements Interceptor {
    private static final String TAG = "request params————————";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request orgRequest = chain.request()
            .newBuilder()
            //请求头设置
            .addHeader("Accept-Charset", "utf-8")
    //                .addHeader("Accept-Language", "zh-CN;q=0.5")
    //                .addHeader("Accept-Encoding", "gzip")
    //                .addHeader("Connection", "keep-alive")
    //                .addHeader("Accept", "application/json")
    //                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Content-Type",	"application/json")
                //token管理
            .addHeader("cookie", SharedPreferencesManager.getInstance().getData(CommonApp.Companion.getInstance(),"user","cookie"))
            .build();

        RequestBody body = orgRequest.body();
        //收集请求参数，方便调试
        StringBuilder paramsBuilder = new StringBuilder();
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = body.contentType();

        if (contentType != null) {
            charset = contentType.charset(Charset.forName("utf-8"));
        }
        String paramsStr = buffer.readString(charset);
        Log.e(TAG,body+"---body");
        if (body != null) {
            RequestBody newBody = null;
            if (body instanceof FormBody) {
                newBody = addParamsToFormBody((FormBody) body, paramsBuilder);
            } else if (body instanceof MultipartBody) {
                newBody = addParamsToMultipartBody((MultipartBody) body, paramsBuilder);
            }else {
                HttpUrl.Builder authorizedUrlBuilder = orgRequest.url()
                        .newBuilder()
                        .scheme(orgRequest.url().scheme())
                        .host(orgRequest.url().host())
                        .addQueryParameter("ver", Constant.url.Ver);
                Request newRequest = orgRequest.newBuilder()
                        .method(orgRequest.method(), orgRequest.body())
                        .url(authorizedUrlBuilder.build())
                        .build();
                return chain.proceed(newRequest);
            }
            if (null != newBody) {
                //打印参数
                Request newRequest = orgRequest.newBuilder()
                        .url(orgRequest.url())
                        .method(orgRequest.method(), newBody)
                        .build();
                return chain.proceed(newRequest);
            }
        }
        return chain.proceed(orgRequest);

    }

    /**
     * 为MultipartBody类型请求体添加参数
     *
     * @param body
     * @param paramsBuilder
     * @return
     */
    private MultipartBody addParamsToMultipartBody(MultipartBody body, StringBuilder paramsBuilder) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

//        builder.addFormDataPart("ver", Constant.url.Ver);


//        paramsBuilder.append("ver=" +  Constant.url.Ver);

        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }
        return builder.build();
    }

    /**
     * 为FormBody类型请求体添加参数
     *
     * @param body
     * @param paramsBuilder
     * @return
     */
    private FormBody addParamsToFormBody(FormBody body, StringBuilder paramsBuilder) {
        FormBody.Builder builder = new FormBody.Builder();

//        builder.add("ver",  Constant.url.Ver);

//        paramsBuilder.append("ver=" + Constant.url.Ver);


        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addEncoded(body.encodedName(i), body.encodedValue(i));
            paramsBuilder.append("&");
            paramsBuilder.append(body.name(i));
            paramsBuilder.append("=");
            paramsBuilder.append(body.value(i));
        }
        return builder.build();
    }


    /**
     * 为FormBody类型请求体添加参数
     *
     * @param body
     * @param paramsBuilder
     * @return
     */
    private FormBody addParamsToBody(FormBody body, StringBuilder paramsBuilder) {
        FormBody.Builder builder = new FormBody.Builder();

        builder.add("ver", Constant.url.Ver);

        paramsBuilder.append("ver=" + Constant.url.Ver);


        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addEncoded(body.encodedName(i), body.encodedValue(i));
            paramsBuilder.append("&");
            paramsBuilder.append(body.name(i));
            paramsBuilder.append("=");
            paramsBuilder.append(body.value(i));
        }
        return builder.build();
    }
}

