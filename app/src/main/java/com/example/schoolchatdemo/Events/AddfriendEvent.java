package com.example.schoolchatdemo.Events;

import com.example.schoolchatdemo.Message.Message;

public class AddfriendEvent {
    private Message message;

    public AddfriendEvent(Message message){
        this.message=message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
