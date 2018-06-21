package open.wow.aaron.com.eyepetizer.delicacy.view.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.List;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.detail.DetailActivity;
import open.wow.aaron.com.eyepetizer.framework.GlideApp;
import open.wow.aaron.com.eyepetizer.framework.utils.TimeUtils;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/5/15
 * 功能描述:
 */

public class AllRecyclerAdapter extends RecyclerView.Adapter {
    private static final String TAG = AllRecyclerAdapter.class.getSimpleName();
    private RecyclerView.RecycledViewPool mPool;
    private List<DelicacyChoiceBean.ItemListBean> mItemList;
    private Context mContext;

    private final int BANNER = 0;//轮播图banner
    private final int HORIZONTAL_VIEW_ONE = 1;//横向1
    private final int HORIZONTAL_VIEW_TOW = 2;//横向2
    private final int VERTICAL_VIEW = 3;//纵向
    private final int TITLE = 4;//单个纵向
    private final int REPEAT_BANNER = 5;//重复的banner

    private View mHeaderView;

    /**
     * 添加头
     *
     * @param headerView 头部视图
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * 获取头
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    public AllRecyclerAdapter(List<DelicacyChoiceBean.ItemListBean> itemList, Context context, RecyclerView.RecycledViewPool pool) {
        mItemList = itemList;
        mContext = context;
        mPool = pool;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HORIZONTAL_VIEW_ONE:
                return new HorizontalViewOneAdapter(
                        LayoutInflater.from(mContext)
                                .inflate(R.layout.item_horizontal_list_one, parent, false));

//            case HORIZONTAL_VIEW_TOW:
//                return new HorizontalViewTowAdapter(
//                        LayoutInflater.from(mContext)
//                                .inflate(R.layout.item_horizontal_list_tow, parent, false));

            case VERTICAL_VIEW:
                return new VerticalViewHolder(
                        LayoutInflater.from(mContext)
                                .inflate(R.layout.item_layout_view, parent, false));
//            case VERTICAL_VIEW:
//                return new VerticalViewHolderAdapter(
//                        LayoutInflater.from(mContext)
//                                .inflate(R.layout.item_layout_view, parent, false));

            case TITLE:
                return new TitleViewHolderAdapter(LayoutInflater.from(mContext)
                        .inflate(R.layout.item_title_view, parent, false));

            case BANNER:
                return new BannerAdapter(LayoutInflater.from(mContext)
                        .inflate(R.layout.view_banner, parent, false));

            case REPEAT_BANNER:
                return new RepeatBannerAdapter(LayoutInflater.from(mContext)
                        .inflate(R.layout.repeat_banner, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HorizontalViewOneAdapter) {
            //String type = itemList.get(position).getType();
            ((HorizontalViewOneAdapter) holder).setData(mItemList, mContext, position, mPool);
        }


        //横向2移除,共用横向1即可
//        if (holder instanceof HorizontalViewTowAdapter) {
//            ((HorizontalViewTowAdapter) holder).setData(mItemList, mContext, position);
//        }

        if (holder instanceof TitleViewHolderAdapter) {
            ((TitleViewHolderAdapter) holder).setData(mItemList, mContext, position);
        }

        if (holder instanceof BannerAdapter) {
            if (position == 0) {
                ((BannerAdapter) holder).setData(mItemList, mContext, position);
            }
        }

        //竖向改为写在onBindViewHolder()方法内,可以减少卡顿问题
        /*if (holder instanceof VerticalViewHolderAdapter) {
            try {
                ((VerticalViewHolderAdapter) holder).setData(mItemList, mContext, position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        if (holder instanceof VerticalViewHolder) {
            //共享元素的Intent
            final Intent intent = new Intent(mContext, DetailActivity.class);
            DelicacyChoiceBean.ItemListBean itemListBean = mItemList.get(position);
            if (itemListBean == null) return;
            intent.putExtra("itemListWBean", new Gson().toJson(itemListBean));

            final DelicacyChoiceBean.ItemListBean.DataBean data = itemListBean.getData();
            if (data == null) return;

            DelicacyChoiceBean.ItemListBean.DataBean.CoverBean cover = data.getCover();
            if (cover == null) return;

//            boolean isRecyclable = holder.isRecyclable();
//            Log.e(TAG, "isRecyclable = " + isRecyclable);


            String imageUrl = data.getCover().getDetail();
            //加载图片
            if (imageUrl != null) {
                GlideApp.with(mContext)
                        .load(imageUrl)
                        //.placeholder(R.drawable.ic_default)
                        //.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        //.skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(((VerticalViewHolder) holder).iv_item_view);

                //启动DetailActivity携带的图片URL地址
                intent.putExtra("imageUrl", imageUrl);
                //intent.putExtra("imageUrl", itemListWBean.getData().getCover().getDetail());


                //Log.e("TAG", "获取的URL = " + imageUrl);
            }

            //设置标题
            String title = data.getTitle();
            if (title != null) {
                ((VerticalViewHolder) holder).tv_item_title.setText(title);
            }

            //设置类型和时长
            String category = data.getCategory();
            String duration = TimeUtils.duration(data.getDuration());
            if (category != null && duration != null) {
                ((VerticalViewHolder) holder).tv_item_category.setText("#" + category);
                ((VerticalViewHolder) holder).tv_item_duration.setText(" " + "/ " + duration);
            }

            //设置作者
            String author = data.getAuthor().getName();
            if (author != null) {
                ((VerticalViewHolder) holder).tv_item_author.setText(author);
            }


            // 这里指定了共享的视图元素
            ((VerticalViewHolder) holder).iv_item_view.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) mContext, v,
                            mContext.getString(R.string.share_animation)
                    );
                    mContext.startActivity(intent, optionsCompat.toBundle());
                    Log.e(TAG, "position = " + position);
                    Log.e(TAG, "data.getTitle()= " + data.getTitle());
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mItemList != null ? mItemList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        //Log.e("TAG", "type == " + type + " tag == " + tag + " position == " + position);
        String type = mItemList.get(position).getType();
        String tag = mItemList.get(position).getTag();
//        if (position == 0) {
//            return TYPE_HEADER;
//        }

//        if (position == itemList.size() + 1){
//            return TYPE_FOOTER;
//        }

        //竖向RecyclerView
        if ("video".equals(type) && "1".equals(tag)) {
            return VERTICAL_VIEW;
        }

        //轮播图(添加头的方式添加)
        if (position == 0 && "video".equals(type) && "0".equals(tag)) {
            return BANNER;
        } else if (position != 0 && "video".equals(type) && "0".equals(tag)) {
            //这里是重复的轮播图
            return REPEAT_BANNER;
        }

        //单个item
        if ("textFooter".equals(type) || "textHeader".equals(type)) {
            return TITLE;
        }

        //横向One
        if ("videoCollectionWithCover".equals(type)) {
            return HORIZONTAL_VIEW_ONE;
        }

        //横向Tow
        if ("videoCollectionOfFollow".equals(type)) {
            //return HORIZONTAL_VIEW_TOW;
            return HORIZONTAL_VIEW_ONE;
        }

        //第二页数据集合,没有图片
        if ("squareCardCollection".equals(type)){
            return REPEAT_BANNER;
        }

        //广告
//        if (type.contains("banner")) {
//            return VERTICAL_VIEW;
//        }

        return VERTICAL_VIEW;
    }

    /**
     * 竖直方向
     */
    class VerticalViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item_view;//图片
        TextView tv_item_title;//标题名称
        TextView tv_item_category;//类型
        TextView tv_item_duration;//时长
        TextView tv_item_author;//作者

        public VerticalViewHolder(View itemView) {
            super(itemView);

            iv_item_view = (ImageView) itemView.findViewById(R.id.iv_item_view);
            tv_item_title = (TextView) itemView.findViewById(R.id.tv_item_title);
            tv_item_duration = (TextView) itemView.findViewById(R.id.tv_item_duration);
            tv_item_category = (TextView) itemView.findViewById(R.id.tv_item_category);
            tv_item_author = (TextView) itemView.findViewById(R.id.tv_item_author);
        }
    }
}
