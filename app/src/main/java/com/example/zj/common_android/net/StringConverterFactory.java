package com.example.zj.common_android.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class StringConverterFactory extends Converter.Factory {

    private final Gson gson;

    private StringConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static StringConverterFactory create() {
        return create(new Gson());
    }

    public static StringConverterFactory create(Gson gson) {
        return new StringConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new StringResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new StringRequestBodyConverter<>(gson, adapter);
    }

    /*final class StringResponseBodyConverter implements Converter<ResponseBody, BaseResponse<String>> {

        Type type;

        public StringResponseBodyConverter(Type type) {
            this.type = type;
        }

        @Override
        public BaseResponse convert(ResponseBody value) throws IOException {
            try {
                JSONObject jsonObject = new JSONObject(value.string().replace("\"data\":null, ", ""));
                BaseResponse<String> baseResponse = new BaseResponse<>();
                baseResponse.success = jsonObject.getBoolean("success");
                baseResponse.code = jsonObject.getInt("code");
                baseResponse.msg = jsonObject.getString("msg");
                String data = jsonObject.getString("data");
                if (type ==)
                    baseResponse.data =;
                return baseResponse;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                value.close();
            }
            return new BaseResponse<>();
        }
    }*/

}