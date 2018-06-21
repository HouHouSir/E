package open.wow.aaron.com.eyepetizer.detail.found.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.framework.GlideApp;
import open.wow.aaron.com.eyepetizer.framework.ui.GlideCircleTransform;
import open.wow.aaron.com.eyepetizer.framework.utils.DensityUtils;
import open.wow.aaron.com.eyepetizer.framework.utils.TimeUtils;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/19
 * 功能描述: 发现详情adapter
 */

public class FoundDetailAdapter extends RecyclerView.Adapter<FoundDetailAdapter.FoundDetailViewHolder> {
    private List<DelicacyChoiceBean.ItemListBean> mItemList;
    private Context mContext;

    public FoundDetailAdapter(DelicacyChoiceBean delicacyChoiceBean, Context context) {
        mContext = context;
        mItemList = delicacyChoiceBean.getItemList();
    }


    @Override
    public FoundDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FoundDetailViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_found_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(FoundDetailViewHolder holder, int position) {
        DelicacyChoiceBean.ItemListBean.DataBean data = mItemList.get(position).getData();
        if (data != null) {
            //大图
            String imageUrl = data.getCover().getDetail();
            GlideApp.with(mContext)
                    .load(imageUrl)
                    //.placeholder(R.drawable.ic_default)
                    .skipMemoryCache(true)
                    .diskCacheStrategy( DiskCacheStrategy.NONE)
                    .into(holder.iv_item_view);
            //设置文字
            String title = data.getTitle();
            holder.tv_item_title.setText("# " + title);

            //类型
            String category = data.getCategory();
            //设置时长
            String duration = TimeUtils.duration(data.getDuration());

            //设置类型和时长
            if (category != null && duration != null) {
                holder.tv_item_category.setText("#" + category);
                holder.tv_item_duration.setText(" " + "/ " + duration);
            }

            //作者
            DelicacyChoiceBean.ItemListBean.DataBean.AuthorBean authorBean = data.getAuthor();
            if (authorBean != null) {
                //作者背景
                holder.ll_detail_author.setBackgroundResource(R.color.color_white);
                //作者名称
                String author = authorBean.getName();
                holder.tv_detail_author_name.setText(author);
                holder.tv_detail_author_name.setTextColor(mContext.getResources().getColor(R.color.color_black));

                //作者视频条数
                int videoNum = authorBean.getVideoNum();
                holder.tv_detail_video_num.setText(videoNum + "个视频");
                holder.tv_detail_video_num.setTextColor(mContext.getResources().getColor(R.color.color_charcoal_grey));

                //作者描述信息
                String description = authorBean.getDescription();
                holder.tv_detail_author_description.setText(description);
                holder.tv_detail_video_num.setTextColor(mContext.getResources().getColor(R.color.color_charcoal_grey));

                //作者头像 设置成圆形
                String homepageUrl = authorBean.getIcon();
                GlideApp.with(mContext)
                        .load(homepageUrl)
                        .centerCrop()
                        .transform(new GlideCircleTransform())
                        .override(DensityUtils.dip2px(mContext, 40), DensityUtils.dip2px(mContext, 40))
                        .into(holder.iv_detail_homepage);
            }

        }


    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    class FoundDetailViewHolder extends RecyclerView.ViewHolder {

        //大图
        ImageView iv_item_view;//图片
        TextView tv_item_title;//标题名称
        TextView tv_item_category;//类型
        TextView tv_item_duration;//时长
        TextView tv_item_author;//作者
        //作者
        LinearLayout ll_detail_author;//整个作者的背景
        ImageView iv_detail_homepage;//头像图片
        TextView tv_detail_author_name;//作者名称
        TextView tv_detail_video_num;//作品条数
        TextView tv_detail_author_description;//作品描述
        ImageView iv_detail_right;//右侧按钮

        public FoundDetailViewHolder(View itemView) {
            super(itemView);

            iv_item_view = (ImageView) itemView.findViewById(R.id.iv_item_view);
            tv_item_title = (TextView) itemView.findViewById(R.id.tv_item_title);
            tv_item_duration = (TextView) itemView.findViewById(R.id.tv_item_duration);
            tv_item_category = (TextView) itemView.findViewById(R.id.tv_item_category);
            tv_item_author = (TextView) itemView.findViewById(R.id.tv_item_author);
            ll_detail_author = (LinearLayout) itemView.findViewById(R.id.ll_detail_author);
            iv_detail_homepage = (ImageView) itemView.findViewById(R.id.iv_detail_homepage);
            tv_detail_author_name = (TextView) itemView.findViewById(R.id.tv_detail_author_name);
            tv_detail_video_num = (TextView) itemView.findViewById(R.id.tv_detail_video_num);
            tv_detail_author_description = (TextView) itemView.findViewById(R.id.tv_detail_author_description);
            iv_detail_right = (ImageView) itemView.findViewById(R.id.iv_detail_right);
        }
    }
}
