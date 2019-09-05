package com.zzh.web;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Administrator.
 *
 * @date: 2019/9/5
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: ZHAutoSize.git
 * @since 1.0
 */
public class RetrofitUtils {
    private static String BASE_URL = "";

    private static boolean isDebug = false;

    /**
     * 初始化方法
     *
     * @param url 将url地址传入
     */
    public static void init(String url) {
        init(url, false);
    }

    /**
     * 初始化方法
     *
     * @param url   将url地址传入
     * @param debug 是否输出日志
     */
    public static void init(String url, boolean debug) {
        BASE_URL = url;
        isDebug = debug;
    }


    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
        Request request = chain.request();
        Response proceed = chain.proceed(request);
        MediaType mediaType = proceed.body().contentType();
        String result = proceed.body().string();
        Response.Builder builder = proceed.newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Connection", "keep-alive")
                .body(ResponseBody.create(mediaType, result));
        return builder.build();
    }).addInterceptor(new HttpLoggingInterceptor().setLevel(isDebug ?
            HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
            .writeTimeout(360, TimeUnit.SECONDS).retryOnConnectionFailure(false).build();

    /**
     * @param clz
     * @param <T> 泛型
     * @return 此泛型的实例
     */
    public static <T> T Api(Class<T> clz) {
        if (TextUtils.isEmpty(BASE_URL)) {
            throw new NullPointerException(" ----RetrofitUtils 必须进行初始化，才能调用此方法-----");
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient)
                    .build();
        }
        return retrofit.create(clz);
    }
}
