package com.example.schoolchatdemo.WebSocket;

import android.util.Log;

import com.example.schoolchatdemo.Events.AddfriendEvent;
import com.example.schoolchatdemo.Events.MessageEvent;
import com.example.schoolchatdemo.Events.OnlineEvent;
import com.example.schoolchatdemo.Message.Message;
import com.example.schoolchatdemo.Message.MessageType;
import com.example.schoolchatdemo.UserPackage.User;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;
import okio.ByteString;

public class MyWsStatusListener extends WsStatusListener {
    @Override
    public void onOpen(Response response) {
        super.onOpen(response);
        Log.d("msg","is_onOpen");
    }

    @Override
    public void onMessage(String text) {
        super.onMessage(text);
        Log.d("msg",text);
        Log.d("msg","is_onMessage_text");
        try {
            JSONObject jsonObject=new JSONObject(text);
            Log.d("msg","jsonObject:"+jsonObject);
            JSONObject msg=jsonObject.getJSONObject("msg");
            Log.d("msg","msg:"+msg);
            int type = msg.getInt("type");
            Log.d("msg","message_type:"+type);
            switch (type){
                case 0:
                    //通知在线消息
                    JSONArray isOnline=msg.getJSONArray("data");
                    Log.d("msg","isOnLine:"+isOnline);
                    Log.d("msg","通知在线");
                    OnlineEvent onlineEvent=new OnlineEvent(isOnline);
                    EventBus.getDefault().post(onlineEvent);
                    break;
                case 1:
                    //通知通信消息
                    Message message=new Message();
                    message.setFrom(msg.getString("from"));
                    message.setTo(msg.getString("to"));
                    message.setContent(msg.getString("data"));
                    message.setMsgType(MessageType.TEXT.getType());
                    if(msg.getString("data").equals("{\"addfriendrequest\":\"yes\"}")){
                        Log.d("msg","addfriendrequest");
                        AddfriendEvent messageEvent=new AddfriendEvent(message);
                        EventBus.getDefault().post(messageEvent);
                    }else {
                        MessageEvent messageEvent=new MessageEvent(message);
                        EventBus.getDefault().post(messageEvent);
                    }
                    break;
                case 2:
                    //通知好友消息
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("msg","e:"+e.getMessage());
        }
    }

    @Override
    public void onMessage(ByteString bytes) {
        super.onMessage(bytes);
        Log.d("msg","is_onMessage_bytes");
    }

    @Override
    public void onReconnect() {
        super.onReconnect();
        Log.d("msg","is_onReconnect");
    }

    @Override
    public void onClosing(int code, String reason) {
        super.onClosing(code, reason);
        Log.d("msg","is_onClosing");
    }

    @Override
    public void onClosed(int code, String reason) {
        super.onClosed(code, reason);
        Log.d("msg","is_onClosed");
    }

    @Override
    public void onFailure(Throwable t, Response response) {
        super.onFailure(t, response);
        Log.d("msg","is_onFailure");
    }
}
