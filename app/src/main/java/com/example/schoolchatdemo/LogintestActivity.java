package com.example.schoolchatdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.schoolchatdemo.UserPackage.UserCallBackListener;
import com.example.schoolchatdemo.UserPackage.UserModel;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogintestActivity extends AppCompatActivity {


    @OnClick({R.id.login,R.id.test,R.id.clear})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                final UserModel um=UserModel.getInstance();
                um.loginUser(LogintestActivity.this,"liyongxiang", "123456", new UserCallBackListener() {
                    @Override
                    public void onFinish(String jsonString) {
                        Log.d("msg", "jsonString:" + jsonString);

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
                break;
            case R.id.test:
                UserModel.getInstance().test(LogintestActivity.this,new UserCallBackListener() {
                    @Override
                    public void onFinish(String jsonString) {
                        Log.d("msg", "jsonString:" + jsonString);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


}
