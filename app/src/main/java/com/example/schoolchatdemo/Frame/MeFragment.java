package com.example.schoolchatdemo.Frame;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.schoolchatdemo.Addfriends.SearchFriendActivity;
import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.UserPackage.User;
import com.example.schoolchatdemo.UserPackage.UserModel;

import butterknife.BindView;
import butterknife.OnClick;

public class MeFragment extends BaseFragment{

    private User user;
    @BindView(R.id.me_userheadimg)
    ImageView head;
    @BindView(R.id.me_name)
    TextView name;

    @OnClick({R.id.menu_item1,R.id.menu_item2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.menu_item1:
                Intent intent=new Intent(getActivity(), SearchFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_item2:
                break;
        }
    }


    @Override
    public int getContentViewId() {
        return R.layout.me_fragment_layout;
    }

    @Override
    public void initData() {
        user= UserModel.getInstance().getCurrentUser(getContext());
//        RequestOptions requestOptions=RequestOptions.circleCropTransform();
//        Glide.with(getContext()).load(user.getU_avatar()).apply(requestOptions).into(head);
        name.setText(user.getU_name());
    }

    @Override
    public void initView() {

    }
}
