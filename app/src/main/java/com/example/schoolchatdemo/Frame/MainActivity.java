package com.example.schoolchatdemo.Frame;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.schoolchatdemo.Addfriends.FriendInfoActivity;
import com.example.schoolchatdemo.BaseActivity;
import com.example.schoolchatdemo.Events.AddfriendEvent;
import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.UserPackage.User;
import com.example.schoolchatdemo.UserPackage.UserModel;
import com.example.schoolchatdemo.Message.Message;
import com.example.schoolchatdemo.WebSocket.MyWsStatusListener;
import com.example.schoolchatdemo.WebSocket.WsManager;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    private User requestuser;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    String[] title=new String[]{"联系人","我的信息"};

    private WsManager wsManager;

    @BindView(R.id.badge)
    CircleImageView badge;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    SegmentTabLayout tabLayout;

    @OnClick(R.id.request)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.request:
                if(badge.getVisibility()==View.GONE){
                    toast("您没有好友申请");
                }else {
                    Intent intent=new Intent(MainActivity.this, FriendInfoActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("requestuser",requestuser);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,123);
                }
                break;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = MainActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.skyblue));
        }
    }

    @Override
    public void initData() {
        wsManager=UserModel.wsConnect(MainActivity.this,new MyWsStatusListener());
        Log.d("msg","wsManager in Main :"+wsManager);

        mFragments.add(new AddressListFragment());
        mFragments.add(new MeFragment());
        tabLayout.setTabData(title);
        MyFragmentAdapter adapter=new MyFragmentAdapter(getSupportFragmentManager(),mFragments,title);
        viewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEventMainThread(AddfriendEvent message) {
//        msg.setText((String)message.getData());
        badge.setVisibility(View.VISIBLE);
        Message msg=message.getMessage();
        requestuser=new User();
        requestuser.setU_name(msg.getFrom());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 123:
                if(resultCode==RESULT_OK){
                    Log.d("msg","onActivityResult");
                    badge.setVisibility(View.GONE);
                }
        }
    }

}
