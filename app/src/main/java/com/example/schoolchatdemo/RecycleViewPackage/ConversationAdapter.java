//package com.example.schoolchatdemo.RecycleViewPackage;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.ImageView;
//
//
///**
// * 使用进一步封装的Conversation,教大家怎么自定义会话列表
// */
//public class ConversationAdapter extends BaseRecyclerAdapter<Conversation> {
//
//    private SwipeLayoutListener swipeLayoutListener;
//
//    public ConversationAdapter(Context context, IMutlipleItem<Conversation> items, Collection<Conversation> datas) {
//        super(context,items,datas);
//    }
//
//
//
//    /**
//     * 获取指定会话类型指定会话id的会话位置
//     * @param type
//     * @param targetId
//     * @return
//     */
//    public int findPosition(BmobIMConversationType type, String targetId) {
//        int index = this.getCount();
//        int position = -1;
//        while(index-- > 0) {
//            if((getItem(index)).getcType().equals(type) && (getItem(index)).getcId().equals(targetId)) {
//                position = index;
//                break;
//            }
//        }
//        return position;
//    }
//
//    @Override
//    public void bindView(BaseRecyclerHolder holder, Conversation conversation, final int position) {
//        SwipeLayout sample2 = (SwipeLayout) holder.itemView.findViewById(R.id.swipe);
//        sample2.setShowMode(SwipeLayout.ShowMode.LayDown);
//        sample2.addDrag(SwipeLayout.DragEdge.Right, sample2.findViewWithTag("Bottom"));
//        ImageView delete=(ImageView) holder.itemView.findViewById(R.id.trash);
//        sample2.getSurfaceView().setOnClickListener(getOnSurfaceClick(position));
//        delete.setOnClickListener(getOnDeleteClick(position));
//        holder.setText(R.id.tv_recent_msg,conversation.getLastMessageContent());
//        holder.setText(R.id.tv_recent_time, TimeUtil.getChatTime(false,conversation.getLastMessageTime()));
//        //会话图标
//        Object obj = conversation.getAvatar();
//        if(obj instanceof String){
//            String avatar=(String)obj;
//            holder.setImageView(avatar, R.mipmap.add_friend, R.id.iv_recent_avatar);
//        }else{
//            int defaultRes = (int)obj;
//            holder.setImageView(null, defaultRes, R.id.iv_recent_avatar);
//        }
//        //会话标题
//        holder.setText(R.id.tv_recent_name, conversation.getcName());
//        //查询指定未读消息数
//        long unread = conversation.getUnReadCount();
//        if(unread>0){
//            holder.setVisible(R.id.tv_recent_unread, View.VISIBLE);
//            holder.setText(R.id.tv_recent_unread, String.valueOf(unread));
//        }else{
//            holder.setVisible(R.id.tv_recent_unread, View.GONE);
//        }
//    }
//
//    public View.OnClickListener getOnSurfaceClick(final int position){
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                swipeLayoutListener.onItemClick(position);
//            }
//        };
//    }
//
//    public View.OnClickListener getOnDeleteClick(final int position){
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                swipeLayoutListener.onDeleteClick(position);
//            }
//        };
//    }
//}