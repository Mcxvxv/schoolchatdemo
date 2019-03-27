package com.example.schoolchatdemo.UserActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.schoolchatdemo.BaseActivity;
import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.UserPackage.UserCallBackListener;
import com.example.schoolchatdemo.UserPackage.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText con_psd;

    @OnClick({R.id.register,R.id.back})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
            case R.id.register:
                String un=username.getText().toString();
                String psd=password.getText().toString();
                String c_psd=con_psd.getText().toString();
                if(TextUtils.isEmpty(un)||TextUtils.isEmpty(psd)||TextUtils.isEmpty(c_psd)){
                    toast("请确保用户名、密码、确认密码都已输入");
                }else {
                    if(psd.equals(c_psd)){
                        UserModel.getInstance().regesterUser(this, un, psd, new UserCallBackListener() {
                            @Override
                            public void onFinish(String jsonString) {
                                JSONObject jsonObject= null;
                                try {
                                    jsonObject = new JSONObject(jsonString);
                                    JSONObject data = new JSONObject(jsonObject.getString("data"));
                                    String result = (String) data.getString("register");
                                    Log.d("msg","register:"+result);
                                    if(result.equals("success")){
                                        toast("注册成功");
                                        finish();
                                    }else {
                                        toast("注册失败");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                    }else {
                        toast("请确保用户名,密码一致");
                    }
                }
                break;
        }
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
}
