package com.example.schoolchatdemo.Util;

import android.content.Context;

import com.example.schoolchatdemo.cookie.CookieJarImpl;
import com.example.schoolchatdemo.cookie.MemoryCookieStore;
import com.example.schoolchatdemo.cookie.PersistentCookieStore;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpUtil {

    private static final int HTTP_TIME_OUT = 30;

    public static OkHttpClient getOkHttpClient(Context context) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        return response;

                    }
                })
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        return response;
                    }
                })
                  .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)));
//                .cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }

    /**
     * 清除所有缓存cookie
     */
    public static void clearCookies(OkHttpClient okHttpClient) {
        if (okHttpClient != null && okHttpClient.cookieJar() instanceof CookieJarImpl) {
            CookieJarImpl cookieJar = (CookieJarImpl) okHttpClient.cookieJar();
            cookieJar.getCookieStore().removeAll();
        }
    }
}
