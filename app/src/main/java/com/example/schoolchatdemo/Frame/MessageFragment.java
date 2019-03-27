package com.example.schoolchatdemo.Frame;

import android.widget.TextView;

import com.example.schoolchatdemo.R;

import butterknife.BindView;

public class MessageFragment extends BaseFragment{

    @BindView(R.id.title)
    TextView titletext;
    private String mTitle;

    public static BaseFragment getInstance(String title) {
        MessageFragment sf = new MessageFragment();
        sf.mTitle = title;
        return sf;
    }
    @Override
    public int getContentViewId() {
        return R.layout.message_fragment_layout;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        titletext.setText(this.mTitle);
    }
}
