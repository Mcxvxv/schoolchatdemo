package com.example.schoolchatdemo.Message;

import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolchatdemo.BaseActivity;
import com.example.schoolchatdemo.Events.MessageEvent;
import com.example.schoolchatdemo.Events.OnlineEvent;
import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.RecycleViewPackage.ChatAdapter;
import com.example.schoolchatdemo.RecycleViewPackage.OnRecyclerViewListener;
import com.example.schoolchatdemo.UserPackage.User;
import com.example.schoolchatdemo.UserPackage.UserModel;
import com.example.schoolchatdemo.WebSocket.MyWsStatusListener;
import com.example.schoolchatdemo.WebSocket.WsManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;

    @BindView(R.id.rc_view)
    RecyclerView rc_view;

    @BindView(R.id.btn_chat_send)
    Button btn_chat_send;

    @BindView(R.id.edit_msg)
    EditText edit_msg;

    @OnClick({R.id.back,R.id.btn_chat_send})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_chat_send:
                sendMessage();
                break;
        }
    }

    private ChatAdapter adapter;
    protected LinearLayoutManager layoutManager;

    WsManager wsManager;
    private User currentUser;
    private User friend;

    @Override
    public int getContentViewId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ChatActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.skyblue));
        }
    }

    @Override
    public void initData() {
        wsManager= UserModel.wsConnect(this,new MyWsStatusListener());
        Log.d("msg","wsManager in Chat :"+wsManager);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        friend = (User) bundle.getSerializable("friend");
        title.setText(friend.getU_name());
        layoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(this);
        rc_view.setAdapter(adapter);
    }

    /**
     * 发送文本消息
     */
    private void sendMessage() {
        String text = edit_msg.getText().toString();
        if (TextUtils.isEmpty(text.trim())) {
            Toast.makeText(ChatActivity.this,"请输入内容！",Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO 发送文本消息
        currentUser=UserModel.getInstance().getCurrentUser(this);
        Log.d("msg","currentUser:"+currentUser);
        wsManager.sendMessage(friend.getU_name()+"@"+text);
        Message message=new Message();
        message.setFrom(currentUser.getU_name());
        message.setTo(friend.getU_name());
        message.setContent(text);
        message.setMsgType(MessageType.TEXT.getType());
        adapter.addMessage(message);
        edit_msg.setText("");
        scrollToBottom();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Subscribe
    public void onEventMainThread(MessageEvent messageEvent) {
        Log.d("msg","收到消息通知");
        Message message=messageEvent.getMessage();
        adapter.addMessage(message);
        scrollToBottom();
        adapter.notifyDataSetChanged();

    }

    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);
    }

}
