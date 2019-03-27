//package com.example.schoolchatdemo.RecycleViewPackage;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.example.makemoneyforsoftware.AddFriendsPackage.FriendInfoActivity;
//import com.example.makemoneyforsoftware.R;
//import com.example.makemoneyforsoftware.bean.MyUser;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class SearchResultUserAdpter extends RecyclerView.Adapter<SearchResultUserAdpter.ViewHolder> {
//    private List<MyUser> myUserList;
//    private Context mContext;
//    private OnRecyclerViewListener mRecyclerViewListener;
//
//    public SearchResultUserAdpter(Context context){
//        mContext=context;
//    }
//    public void setMyUserList(List<MyUser> list){
//        myUserList=list;
//    }
//
//    public void setmRecyclerViewListener(OnRecyclerViewListener mRecyclerViewListener) {
//        this.mRecyclerViewListener = mRecyclerViewListener;
//    }
//
//    public Context getmContext() {
//        return mContext;
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        @BindView(R.id.userhead)
//        CircleImageView userheadimg;
//        @BindView(R.id.usertext)
//        TextView name;
//        @BindView(R.id.check)
//        Button add;
//
//        private OnRecyclerViewListener recyclerViewListener;
//
//        public ViewHolder(View view){
//            super(view);
//            ButterKnife.bind(this,itemView);
//            itemView.setOnClickListener(this);
//        }
//
//        public void setRecyclerViewListener(OnRecyclerViewListener recyclerViewListener) {
//            this.recyclerViewListener = recyclerViewListener;
//        }
//
//        @Override
//        public void onClick(View v) {
//            recyclerViewListener.onItemClick(getAdapterPosition());
//        }
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.searchresultuseritemlayout,parent,false);
//        ViewHolder viewHolder=new ViewHolder(view);
//        viewHolder.setRecyclerViewListener(mRecyclerViewListener);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final MyUser myUser=getItem(position);
//        Glide.with(holder.itemView).load(myUser.getUrl()).into(holder.userheadimg);
//        holder.name.setText(myUser.getRealname());
//        holder.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("u", myUser);
//                Intent intent=new Intent(getmContext(), FriendInfoActivity.class);
//                intent.putExtras(bundle);
//                getmContext().startActivity(intent);
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if(myUserList==null){
//            return 0;
//        }
//        return myUserList.size();
//    }
//
//    public MyUser getItem(int position){
//        return myUserList.get(position);
//    }
//
//
//
//
//}
