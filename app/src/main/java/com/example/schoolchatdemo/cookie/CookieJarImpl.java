package com.example.schoolchatdemo.cookie;

import android.util.Log;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * CookieJarImpl
 *
 * @author linzhiyong
 * @email wflinzhiyong@163.com
 * @blog https://blog.csdn.net/u012527802
 * https://github.com/linzhiyong
 * https://www.jianshu.com/u/e664ba5d0800
 * @time 2018/7/20
 * @desc
 */
public class CookieJarImpl implements CookieJar {

    private CookieStore cookieStore;

    public CookieJarImpl(CookieStore cookieStore) {
        if(cookieStore == null) {
            throw new IllegalArgumentException("cookieStore can not be null.");
        }

        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        this.cookieStore.add(url, cookies);
        Log.d("msg","url:"+url+" cookies:"+cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        if(!this.cookieStore.getCookies().isEmpty()){
            Log.d("msg","url and cookies:"+this.cookieStore.getCookies().get(0));
        }
        return this.cookieStore.getCookies();
    }

    public CookieStore getCookieStore() {
        return this.cookieStore;
    }

}
