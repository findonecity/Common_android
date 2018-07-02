package com.example.zj.common_android.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class StringResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    StringResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String result = value.string().replace("\"data\":null,", "");
        JsonReader jsonReader = gson.newJsonReader(new InputStreamReader(
                new ByteArrayInputStream(result.getBytes("UTF-8")), Charset.forName("UTF-8")));
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
