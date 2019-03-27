package com.example.schoolchatdemo.Addfriends;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.schoolchatdemo.BaseActivity;
import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.UserPackage.User;
import com.example.schoolchatdemo.UserPackage.UserCallBackListener;
import com.example.schoolchatdemo.UserPackage.UserModel;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchFriendActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.search)
    Button search;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.searchresult)
    RecyclerView searchresult;

    @OnClick({R.id.back, R.id.search})
    public void Onclick(View view) {
        switch (view.getId()) {
            case R.id.search:
                query();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private SearchResultUserAdpter adpter;
    private List<User> mlist;

    @Override
    public int getContentViewId() {
        return R.layout.activity_search_friend;
    }

    @Override
    public void initData() {
        mlist=new ArrayList<>();
        adpter = new SearchResultUserAdpter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        searchresult.setLayoutManager(layoutManager);
        searchresult.setAdapter(adpter);
        refreshLayout.setRefreshHeader(new MaterialHeader(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                query();
            }
        });
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = SearchFriendActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    public void query(){
        String name = username.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写用户名", Toast.LENGTH_LONG).show();
            refreshLayout.finishRefresh(false);
        } else {
            UserModel.getInstance().queryUserByUsername(this, name, new UserCallBackListener() {
                @Override
                public void onFinish(String jsonString) {
                    mlist.clear();
                    Log.d("msg","searchresult:"+jsonString);
                    try {
                        JSONObject jsonObject=new JSONObject(jsonString);
                        JSONObject data=jsonObject.getJSONObject("data");
                        User user=new User();
                        user.setU_name(data.getString("u_name"));
                        user.setU_avatar(data.getString("u_avatar"));
                        mlist.add(user);
                        adpter.setMyUserList(mlist);
                        adpter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

    }
}
