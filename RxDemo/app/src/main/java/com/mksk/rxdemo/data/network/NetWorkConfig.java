package com.mksk.rxdemo.data.network;

import static com.mksk.rxdemo.utils.Constant.URL_BASE;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkConfig {
    private static NetWorkConfig INSTANCE;
    private final AppApi api;

    public static NetWorkConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetWorkConfig();
        }
        return INSTANCE;
    }

    private NetWorkConfig() {
        Gson gson = new GsonBuilder().create();

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(logger);
        OkHttpClient client = httpClientBuilder
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(AppApi.class);
    }

    public AppApi getApi() {
        return api;
    }
}
