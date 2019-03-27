package com.example.schoolchatdemo.Message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


import com.example.schoolchatdemo.R;

import java.util.List;

public class PrivateConversation extends Conversation{

    private Conversation conversation;
    private Message lastMsg;

//    public PrivateConversation(BmobIMConversation conversation){
//        this.conversation = conversation;
//        cType = BmobIMConversationType.setValue(conversation.getConversationType());
//        cId = conversation.getConversationId();
//        if (cType == ConversationType.PRIVATE){
//            cName=conversation.getConversationTitle();
//            if (TextUtils.isEmpty(cName)) {
//                cName = cId;
//            }
//        }else{
//            cName="未知会话";
//        }
//        List<BmobIMMessage> msgs =conversation.getMessages();
//        if(msgs!=null && msgs.size()>0){
//            lastMsg =msgs.get(0);
//        }
//    }

    @Override
    public void readAllMessages() {
//        conversation.updateLocalCache();
    }

    @Override
    public Object getAvatar() {
//        if (cType == ConversationType.PRIVATE){
//            String avatar =  conversation.getConversationIcon();
//            if (TextUtils.isEmpty(avatar)){//头像为空，使用默认头像
//                return R.mipmap.user;
//            }else{
//                return avatar;
//            }
//        }else{
//            return R.mipmap.user;
//        }
        return R.mipmap.user;
    }

    @Override
    public String getLastMessageContent() {
        if(lastMsg!=null){
            String content =lastMsg.getContent();
            if(lastMsg.getMsgType().equals(MessageType.TEXT.getType()) || lastMsg.getMsgType().equals("agree")){
                return content;
            }else{//开发者自定义的消息类型，需要自行处理
                return "[未知]";
            }
        }else{//防止消息错乱
            return "";
        }
    }

    @Override
    public long getLastMessageTime() {
//        if(lastMsg!=null) {
//            return lastMsg.getCreateTime();
//        }else{
//            return 0;
//        }
        return 0;
    }

    @Override
    public int getUnReadCount() {
        //TODO 会话：4.3、查询指定会话下的未读消息数
//        return (int)BmobIM.getInstance().getUnReadCount(conversation.getConversationId());
        return 0;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("c", conversation);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    public void onLongClick(Context context) {
        //TODO 会话：4.5、删除会话，以下两种方式均可以删除会话

    }
}
