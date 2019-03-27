package com.example.schoolchatdemo.RecycleViewPackage;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.UserPackage.User;

import java.util.Collection;


/**联系人
 * 一种简洁的Adapter实现方式，可用于多种Item布局的recycleView实现，不用再写ViewHolder啦
 */
public class ContactAdapter extends BaseRecyclerAdapter<User> {

    public static final int TYPE_NEW_FRIEND = 0;
    public static final int TYPE_ITEM = 1;

    public ContactAdapter(Context context, IMutlipleItem<User> items, Collection<User> datas) {
        super(context,items,datas);
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, User friend, int position) {
        if(holder.layoutId== R.layout.item_contact){
//            //好友头像
            holder.setImageView(friend == null ? null : friend.getU_avatar(), R.mipmap.user, R.id.iv_recent_avatar);
            //好友名称
            holder.setText(R.id.tv_recent_name,friend==null?"未知":friend.getU_name());
            holder.setText(R.id.tv_recent_isonline,friend.getIsOnline()==1?"在线":"离线");
        }
    }

}
