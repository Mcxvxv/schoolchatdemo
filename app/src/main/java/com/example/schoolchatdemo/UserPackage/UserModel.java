package com.example.schoolchatdemo.UserPackage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.schoolchatdemo.Util.OkhttpUtil;
import com.example.schoolchatdemo.WebSocket.MyWebSocketListener;
import com.example.schoolchatdemo.WebSocket.MyWsStatusListener;
import com.example.schoolchatdemo.WebSocket.WsManager;
import com.example.schoolchatdemo.cookie.CookieJarImpl;
import com.example.schoolchatdemo.cookie.PersistentCookieStore;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class UserModel {



    private static UserModel mInstance = new UserModel();

    public static UserModel getInstance(){
        return mInstance;
    }

    public static WsManager wsManager=null;

    public UserModel(){}

    public User getCurrentUser(Context context){
        User user=new User();
        SharedPreferences sp=context.getSharedPreferences("currentUser",MODE_PRIVATE);
        String username=sp.getString("username", "error");
        String password=sp.getString("password", "error");
        user.setU_name(username);
        user.setU_password(password);
        return user;
    }


    public void connect(Context context, MyWebSocketListener listener) {
        Request request = new Request.Builder()
                .url("ws://47.101.211.71:8080/schoolchat/ws")
                .build();
        OkHttpClient okHttpClient= OkhttpUtil.getOkHttpClient(context);
        okHttpClient.newWebSocket(request, listener);
        okHttpClient.dispatcher().executorService().shutdown();
    }

    public static WsManager wsConnect(Context context, MyWsStatusListener listener){
        if(wsManager==null){
             wsManager = new WsManager.Builder(context)
                    .client(new OkHttpClient().newBuilder()
                            .pingInterval(15, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                            .build())
                    .needReconnect(true)
                    .wsUrl("ws://47.101.211.71:8080/schoolchat/ws")
                    .build();
            wsManager.setWsStatusListener(listener);
            wsManager.startConnect();
        }
        return  wsManager;
    }


    public void request(final Context context, String url, RequestBody requestBody, final UserCallBackListener listener){
//        RequestBody requestBody = new FormBody.Builder()
//                .add("search", "Jurassic Park")
//                .build();
        OkHttpClient okHttpClient=OkhttpUtil.getOkHttpClient(context);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.d("okhttp", "onFailure: " + e.getMessage());
                if(listener!=null){
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError(e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.d("okhttp", response.protocol() + " " +response.code() + " " + response.message());
                if(listener!=null){
                    final String responseData=response.body().string();
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(responseData);
                        }
                    });
                }
            }
        });
    }

    public void test(Context context,UserCallBackListener listener){
        String url="http://47.101.211.71:8080/schoolchat/User/test";
        RequestBody requestBody = new FormBody.Builder()
                .build();
        request(context,url,requestBody,listener);
    }


    public void regesterUser(Context context,String username,String password,UserCallBackListener listener){
        String url="http://47.101.211.71:8080/schoolchat/User/register";
        RequestBody requestBody = new FormBody.Builder()
                .add("u_name",username)
                .add("u_password",password)
                .build();
        request(context,url,requestBody,listener);
    }

    public void loginUser(Context context,String username,String password,UserCallBackListener listener){
        String url="http://47.101.211.71:8080/schoolchat/User/login";
        RequestBody requestBody = new FormBody.Builder()
                .add("u_name",username)
                .add("u_password",password)
                .build();
        request(context,url,requestBody,listener);
    }

    public void queryUserByUsername(Context context,String username,UserCallBackListener listener){
        String url="http://47.101.211.71:8080/schoolchat/User/queryUserByName";
        RequestBody requestBody = new FormBody.Builder()
                .add("u_name",username)
                .build();
        request(context,url,requestBody,listener);
    }

    public void queryUserFriendsByUsername(Context context,String username,UserCallBackListener listener){
        String url="http://47.101.211.71:8080/schoolchat/User/queryfriends";
        RequestBody requestBody = new FormBody.Builder()
                .add("u_name",username)
                .build();
        request(context,url,requestBody,listener);
       
    }

    public void queryAllOnlineUser(Context context,UserCallBackListener listener){
        String url="http://47.101.211.71:8080/schoolchat/User/queryOnlineUser";
        RequestBody requestBody = new FormBody.Builder()
                .build();
        request(context,url,requestBody,listener);

    }

    public void addfriends(Context context,String user1,String user2,UserCallBackListener listener){
        String url="http://47.101.211.71:8080/schoolchat/User/addfriend";
        RequestBody requestBody = new FormBody.Builder()
                .add("u_name_1",user1)
                .add("u_name_2",user2)
                .build();
        request(context,url,requestBody,listener);

    }

    public void checkisonline(Context context,String user,UserCallBackListener listener){
        String url="http://47.101.211.71:8080/schoolchat/User/checkisOnline";
        RequestBody requestBody = new FormBody.Builder()
                .add("u_name",user)
                .build();
        request(context,url,requestBody,listener);

    }

}
