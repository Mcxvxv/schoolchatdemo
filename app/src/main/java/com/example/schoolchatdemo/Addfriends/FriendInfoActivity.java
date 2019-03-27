package com.example.schoolchatdemo.Addfriends;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.schoolchatdemo.BaseActivity;
import com.example.schoolchatdemo.Frame.MainActivity;
import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.UserPackage.User;
import com.example.schoolchatdemo.UserPackage.UserCallBackListener;
import com.example.schoolchatdemo.UserPackage.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendInfoActivity extends BaseActivity {

    private User user;
    private User friend;
    private Intent intent;
    @BindView(R.id.userhead)
    CircleImageView head;
    @BindView(R.id.tv_name)
    TextView name;
    @BindView(R.id.btn_add_friend)
    Button add_friend;
    @BindView(R.id.back)
    ImageView back;
    @OnClick({R.id.btn_add_friend,R.id.back})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.btn_add_friend:
                UserModel.getInstance().addfriends(FriendInfoActivity.this, user.getU_name(), friend.getU_name(), new UserCallBackListener() {
                    @Override
                    public void onFinish(String jsonString) {
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(jsonString);
                            JSONObject data = new JSONObject(jsonObject.getString("data"));
                            String result = (String) data.getString("addfriend");
                            Log.d("msg","addfriend:"+result);
                            if(result.equals("success")){
                                toast("好友添加成功");
                                UserModel.getInstance().queryAllOnlineUser(FriendInfoActivity.this, new UserCallBackListener() {
                                    @Override
                                    public void onFinish(String jsonString) {
                                        setResult(RESULT_OK,intent);
                                        finish();
                                    }

                                    @Override
                                    public void onError(Exception e) {

                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_friend_info;
    }

    @Override
    public void initData() {
        user=UserModel.getInstance().getCurrentUser(this);
        intent=getIntent();
        Bundle bundle=intent.getExtras();
        friend=(User)bundle.getSerializable("requestuser");
        UserModel.getInstance().queryUserByUsername(this, user.getU_name(), new UserCallBackListener() {
            @Override
            public void onFinish(String jsonString) {
                Log.d("msg","userinfo:"+jsonString);
                try {
                    JSONObject jsonObject=new JSONObject(jsonString);
                    JSONObject data=jsonObject.getJSONObject("data");
                    Glide.with(FriendInfoActivity.this).load(data.getString("u_avatar")).into(head);
                    //显示名称
                    name.setText(data.getString("u_name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = FriendInfoActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }
}

