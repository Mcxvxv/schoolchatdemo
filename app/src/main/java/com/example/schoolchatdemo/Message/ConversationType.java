package com.example.schoolchatdemo.Message;

public enum ConversationType {

        NONE(0, "none"),
        PRIVATE(1, "private"),
        GROUP(2, "GROUP");

        private int value = 1;
        private String name = "";

        private ConversationType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public int getValue() {
            return this.value;
        }

        public static ConversationType setValue(int code) {
            ConversationType[] types = values();
            int len = types.length;

            for(int i = 0; i < len; ++i) {
                ConversationType c = types[i];
                if (code == c.getValue()) {
                    return c;
                }
            }

            return PRIVATE;
        }

}
