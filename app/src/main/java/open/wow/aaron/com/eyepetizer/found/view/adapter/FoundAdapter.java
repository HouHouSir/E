package open.wow.aaron.com.eyepetizer.found.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.found.model.bean.FoundBean;
import open.wow.aaron.com.eyepetizer.framework.GlideApp;
import open.wow.aaron.com.eyepetizer.framework.utils.DensityUtils;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //图片
        GlideApp.with(mContext)
                .load(mList.get(position).getBgPicture())
                //.placeholder(R.drawable.ic_default)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(DensityUtils.dip2px(mContext, 40), DensityUtils.dip2px(mContext, 40))
                .into(holder.mIvFoundItem);
        //名称
        holder.mTvFoundType.setText("#" + mList.get(position).getName());

        if (mOnItemClickListener != null) {
            //图片点击事件
            holder.mIvFoundItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.mIvFoundItem, position);
                    //Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvFoundItem;
        TextView mTvFoundType;

        ViewHolder(View itemView) {
            super(itemView);
            mIvFoundItem = itemView.findViewById(R.id.iv_found_item);
            mTvFoundType = itemView.findViewById(R.id.tv_found_type);
        }
    }

    /**
     * Item点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
