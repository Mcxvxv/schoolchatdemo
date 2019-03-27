package com.example.schoolchatdemo.Frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolchatdemo.Events.OnlineEvent;
import com.example.schoolchatdemo.Message.ChatActivity;
import com.example.schoolchatdemo.Message.Friend;
import com.example.schoolchatdemo.Message.Message;
import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.RecycleViewPackage.ContactAdapter;
import com.example.schoolchatdemo.RecycleViewPackage.IMutlipleItem;
import com.example.schoolchatdemo.RecycleViewPackage.OnRecyclerViewListener;
import com.example.schoolchatdemo.UserPackage.User;
import com.example.schoolchatdemo.UserPackage.UserCallBackListener;
import com.example.schoolchatdemo.UserPackage.UserModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddressListFragment extends BaseFragment{

    @BindView(R.id.recyclerview)
    RecyclerView rc_view;
    @BindView(R.id.refresh)
    SmartRefreshLayout sw_refresh;
    ContactAdapter adapter;
    LinearLayoutManager layoutManager;
    private View rootView;
    List<User> friendslist = new ArrayList<>();

    private User currentUser;

    @Override
    public int getContentViewId() {
        return R.layout.addresslist_fragment_layout;
    }

    @Override
    public void initData() {
        currentUser=UserModel.getInstance().getCurrentUser(getContext());
        IMutlipleItem<User> mutlipleItem=new IMutlipleItem<User>() {
            @Override
            public int getItemLayoutId(int viewtype) {
                return R.layout.item_contact;
            }

            @Override
            public int getItemViewType(int postion, User user) {
                return ContactAdapter.TYPE_ITEM;
            }

            @Override
            public int getItemCount(List<User> list) {
                return list.size();
            }
        };
        adapter = new ContactAdapter(getActivity(), mutlipleItem, null);
        rc_view.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rc_view.setLayoutManager(layoutManager);
        sw_refresh.setRefreshHeader(new ClassicsHeader(getActivity()));
        sw_refresh.setEnabled(true);
        query();
        setListener();
    }

    @Override
    public void initView() {

    }

    private void setListener() {
        sw_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                if(friendslist.get(position).getIsOnline()==0){
                    Toast.makeText(getContext(),"该用户暂未登录，请登录后再进行聊天",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(getActivity(),ChatActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("friend",friendslist.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(final int position) {
//                log("长按" + position);
//                if (position == 0) {
//                    return true;
//                }
                return true;
            }
        });
    }

    public void query(){
        UserModel.getInstance().queryAllOnlineUser(getContext(), new UserCallBackListener() {
            @Override
            public void onFinish(String jsonString) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("msg","onStart");
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().register(this);
    }


//    @Override
//    public void onStop() {
//        Log.d("msg","onStop");
//        EventBus.getDefault().unregister(this);
//        super.onStop();
//    }


    @Subscribe
    public void onEventMainThread(OnlineEvent onlineEvent) {
        Log.d("msg","收到通知在线");
        final JSONArray isOnlineArray=onlineEvent.getData();
        Log.d("msg","onlineEvent.data:"+isOnlineArray);
        UserModel.getInstance().queryUserFriendsByUsername(getContext(), currentUser.getU_name(), new UserCallBackListener() {
            @Override
            public void onFinish(String jsonString) {
                friendslist.clear();
                Log.d("msg","friends:"+jsonString);
                try {
                    JSONObject jsonObject=new JSONObject(jsonString);
                    String friends=jsonObject.getString("data");
                    Log.d("msg","friends:"+friends);
                    JSONArray jsonArray=new JSONArray(friends);
                    for(int i=0;i<jsonArray.length();i++){
                        User user=new User();
                        JSONObject jo=jsonArray.getJSONObject(i);
                        user.setU_name(jo.getString("u_name"));
                        user.setU_avatar(jo.getString("u_avatar"));
                        friendslist.add(user);
                    }
                    try {
                        for(int i=0;i<isOnlineArray.length();i++){
                            JSONObject onlineuser=isOnlineArray.getJSONObject(i);
                            String name = onlineuser.getString("username");
                            int online=onlineuser.getInt("online");
                            Log.d("msg","friendslist:"+friendslist.size());
                            for (int j=0;j<friendslist.size();j++){
                                User user=friendslist.get(j);
                                if(user.getU_name().equals(name)&&online==1){
                                    user.setIsOnline(1);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.bindDatas(friendslist);
                    adapter.notifyDataSetChanged();
                    sw_refresh.finishRefresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Exception e) {
                Log.d("msg",e.getMessage());
            }
        });

//        msg.setText((String)message.getData());
    }
}
