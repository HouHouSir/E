package open.wow.aaron.com.eyepetizer.found.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.found.model.bean.FoundBean;
import open.wow.aaron.com.eyepetizer.framework.GlideApp;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/13
 * 功能描述: 发现页面的Adapter
 */

public class FoundAdapter extends RecyclerView.Adapter<FoundAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<FoundBean> mList;

    public FoundAdapter(ArrayList<FoundBean> arrayList, Context context) {
        mContext = context;
        mList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_found, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //图片
        GlideApp.with(mContext)
                .load(mList.get(position).getBgPicture())
                .placeholder(R.drawable.ic_default)
                .into(holder.mIvFoundItem);
        //名称
        holder.mTvFoundType.setText("#"+mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvFoundItem;
        private TextView mTvFoundType;

        ViewHolder(View itemView) {
            super(itemView);
            mIvFoundItem = itemView.findViewById(R.id.iv_found_item);
            mTvFoundType =  itemView.findViewById(R.id.tv_found_type);
        }
    }
}
