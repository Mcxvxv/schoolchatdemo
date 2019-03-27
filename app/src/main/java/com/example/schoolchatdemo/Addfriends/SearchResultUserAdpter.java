package com.example.schoolchatdemo.Addfriends;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.RecycleViewPackage.OnRecyclerViewListener;
import com.example.schoolchatdemo.UserPackage.User;
import com.example.schoolchatdemo.UserPackage.UserCallBackListener;
import com.example.schoolchatdemo.UserPackage.UserModel;
import com.example.schoolchatdemo.WebSocket.WsManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchResultUserAdpter extends RecyclerView.Adapter<SearchResultUserAdpter.ViewHolder> {
    private List<User> myUserList;
    private Context mContext;
    private OnRecyclerViewListener mRecyclerViewListener;

    public SearchResultUserAdpter(Context context) {
        mContext = context;
    }

    public void setMyUserList(List<User> list) {
        myUserList = list;
    }

    public void setmRecyclerViewListener(OnRecyclerViewListener mRecyclerViewListener) {
        this.mRecyclerViewListener = mRecyclerViewListener;
    }

    public Context getmContext() {
        return mContext;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.userhead)
        CircleImageView userheadimg;
        @BindView(R.id.usertext)
        TextView name;
        @BindView(R.id.check)
        Button add;

        private OnRecyclerViewListener recyclerViewListener;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setRecyclerViewListener(OnRecyclerViewListener recyclerViewListener) {
            this.recyclerViewListener = recyclerViewListener;
        }

        @Override
        public void onClick(View v) {
            recyclerViewListener.onItemClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchresultuseritemlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setRecyclerViewListener(mRecyclerViewListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User myUser = getItem(position);
        Glide.with(holder.itemView).load(myUser.getU_avatar()).into(holder.userheadimg);
        holder.name.setText(myUser.getU_name());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel.getInstance().checkisonline(getmContext(), myUser.getU_name(), new UserCallBackListener() {
                    @Override
                    public void onFinish(String jsonString) {
                        try {
                            JSONObject jo = new JSONObject(jsonString);
                            JSONObject data = new JSONObject(jo.getString("data"));
                            int result = data.getInt("isonline");
                            Log.d("msg", "isonline:" + result);
                            if (result == 1) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("addfriendrequest", "yes");
                                String message = myUser.getU_name() + "@" + jsonObject.toString();
                                Log.d("msg", "addfriendrequest:" + message);
                                if (UserModel.wsManager != null) {
                                    WsManager wsManager = UserModel.wsManager;
                                    Log.d("msg", "addfriendrequestwsManager:" + wsManager);
                                    wsManager.sendMessage(message);
                                    Toast.makeText(getmContext(), "请求好友成功", Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(getmContext(), "该用户不在线，请用户上线后再请求", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        if (myUserList == null) {
            return 0;
        }
        return myUserList.size();
    }

    public User getItem(int position) {
        return myUserList.get(position);
    }


}
