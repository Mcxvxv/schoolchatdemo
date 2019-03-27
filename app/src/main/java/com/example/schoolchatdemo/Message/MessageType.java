package com.example.schoolchatdemo.Message;

public enum MessageType {
    TEXT(1, "txt"),
    IMAGE(2, "image"),
    VOICE(3, "sound"),
    LOCATION(4, "location"),
    VIDEO(5, "video");

    String type;
    int v;

    private MessageType(int v, String type) {
        this.type = type;
        this.v = v;
    }

    public static final int getMessageTypeValue(String t) {
        MessageType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            MessageType type = var1[var3];
            if (t.equals(type.getType())) {
                return type.getValue();
            }
        }

        return 0;
    }

    public String getType() {
        return this.type;
    }

    public int getValue() {
        return this.v;
    }
}

