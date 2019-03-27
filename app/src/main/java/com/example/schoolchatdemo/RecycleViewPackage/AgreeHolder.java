package com.example.schoolchatdemo.RecycleViewPackage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.Message.Message;

import java.text.SimpleDateFormat;

import butterknife.BindView;

/**
 * 同意添加好友的agree类型
 */
public class AgreeHolder extends BaseViewHolder implements View.OnClickListener, View.OnLongClickListener {

  @BindView(R.id.tv_time)
  TextView tv_time;
  @BindView(R.id.tv_message)
  TextView tv_message;

  public AgreeHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
    super(context, root, R.layout.item_chat_agree, listener);
  }

  @Override
  public void bindData(Object o) {
    final Message message = (Message) o;
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//    String time = dateFormat.format(message.getCreateTime());
    String content = message.getContent();
    tv_message.setText(content);
//    tv_time.setText(time);
  }

  public void showTime(boolean isShow) {
    tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
  }
}
