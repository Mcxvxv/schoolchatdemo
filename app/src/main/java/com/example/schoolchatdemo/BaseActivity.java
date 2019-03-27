package com.example.schoolchatdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ActionMenuView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getContentViewId()!=0) {
            setContentView(getContentViewId());
        }
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public abstract int getContentViewId();

    public abstract void initView();

    public abstract void initData();

    public  void toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class c,Boolean isfinish){
        Intent intent=new Intent(this,c);
        startActivity(intent);
        if(isfinish){
            finish();
        }
    }

}
