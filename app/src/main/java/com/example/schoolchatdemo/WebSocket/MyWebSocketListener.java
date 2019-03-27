package com.example.schoolchatdemo.WebSocket;

import android.util.Log;

import com.example.schoolchatdemo.Util.OtherUtil;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MyWebSocketListener extends WebSocketListener {
    public MyWebSocketListener() {
        super();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        Log.d("msg","is_onOpen");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        Log.d("msg",text);
        Log.d("msg","is_onMessage");
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        Log.d("msg","is_onMessage");
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        Log.d("msg","is_onClosing");
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        Log.d("msg","is_onClosed");
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t,Response response) {
        super.onFailure(webSocket, t, response);
        Log.d("msg","is_onFailure");
    }
}
