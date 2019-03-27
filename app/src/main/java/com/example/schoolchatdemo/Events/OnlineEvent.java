package com.example.schoolchatdemo.Events;

import org.json.JSONArray;
import org.json.JSONObject;

public class OnlineEvent {
    private JSONArray data;
    public OnlineEvent(JSONArray data){
        this.data=data;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }
}
