package open.wow.aaron.com.eyepetizer.author.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import open.wow.aaron.com.eyepetizer.framework.base.BaseHolder;
import open.wow.aaron.com.eyepetizer.framework.utils.DensityUtils;
import open.wow.aaron.com.eyepetizer.framework.utils.TimeUtils;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/29
 * 功能描述:横向RecycleView
 */
public class AuthorHorizontalHolder extends BaseHolder<DelicacyChoiceBean.ItemListBean.DataBean, Integer> {
    private static final String TAG = AuthorHorizontalHolder.class.getSimpleName();
    private FragmentActivity mFragmentActivity;
    //private DelicacyChoiceBean.ItemListBean.DataBean mData;
    private List<DelicacyChoiceBean.ItemListBean> mListBeans;

    private RecyclerView mRecyclerView;
    private ImageView mIvDetailHomepage;
    private TextView mTvDetailAuthorName;
    private TextView mTvDetailAuthorDescription;
    private TextView mTvDetailVideoNum;
    private Context context;

    public AuthorHorizontalHolder(int viewId, ViewGroup parent, int viewType) {
        super(viewId, parent, viewType);
        Log.e(TAG, "AuthorHorizontalHolder()");
        mRecyclerView = itemView.findViewById(R.id.recycler_view_author);

        mIvDetailHomepage = itemView.findViewById(R.id.iv_detail_homepage);
        mTvDetailAuthorName = itemView.findViewById(R.id.tv_detail_author_name);
        mTvDetailAuthorDescription = itemView.findViewById(R.id.tv_detail_author_description);
        mTvDetailVideoNum = itemView.findViewById(R.id.tv_detail_video_num);
    }

    @Override
    public void refreshData(DelicacyChoiceBean.ItemListBean.DataBean data, Integer position, Context context) {
        super.refreshData(data, position, context);
        this.context = context;
        //this.mData = data;
        mListBeans = data.getItemList();
        Log.e(TAG, "mListBeans.size()= " + mListBeans.size());

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,
                        false));
        mRecyclerView.setAdapter(new AuthorItemHorizontalViewAdapter());

        DelicacyChoiceBean.ItemListBean itemListBean = mListBeans.get(position - 1);
        DelicacyChoiceBean.HeaderBean header = itemListBean.getData().getHeaderBean();
        if (header != null) {
            String icon = header.getIcon();
            if (!TextUtils.isEmpty(icon)) {
                GlideApp.with(context)
                        .load(icon)
                        //.placeholder(R.drawable.ic_default)
                        //.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        //.skipMemoryCache(true)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .override(DensityUtils.dip2px(context, 280), DensityUtils.dip2px(context, 160))
                        .into(mIvDetailHomepage);
            }
            String title = header.getTitle();
            if (!TextUtils.isEmpty(title)) {
                mTvDetailAuthorName.setText(title);
            }
            String description = header.getDescription();
            if (!TextUtils.isEmpty(description)) {
                mTvDetailAuthorDescription.setText(description);
            }

            mTvDetailVideoNum.setVisibility(View.INVISIBLE);

        }
    }


    /**
     * 横向RecycleView的adapter
     */
    class AuthorItemHorizontalViewAdapter extends RecyclerView.Adapter<BaseHolder> {

        @Override
        public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AuthorItemHorizontalViewHolder(R.layout.item_author_horizontal, parent, viewType);
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            Log.e(TAG, "onBindViewHolder()");
            ((AuthorItemHorizontalViewHolder) holder).refreshData(mListBeans.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mListBeans == null ? 0 : mListBeans.size();
        }
    }


    /**
     * 横向RecycleView adapter里的ViewHolder 因为外层继承了BaseHolder,所以这里也需要继承因为外层继承了BaseHolder
     */
    class AuthorItemHorizontalViewHolder extends BaseHolder<DelicacyChoiceBean.ItemListBean, Integer> {


        ImageView mIvHorizontalItemView;////图片
        TextView mTvHorizontalItemTitle;//标题
        TextView mTvHorizontalItemCategory;//类型
        TextView mTvHorizontalItemDuration;//时长


        public AuthorItemHorizontalViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);

            mIvHorizontalItemView = itemView.findViewById(R.id.iv_horizontal_item_view);
            mTvHorizontalItemTitle = itemView.findViewById(R.id.tv_horizontal_item_title);
            mTvHorizontalItemCategory = itemView.findViewById(R.id.tv_horizontal_item_category);
            mTvHorizontalItemDuration = itemView.findViewById(R.id.tv_horizontal_item_duration);
        }


        @Override
        public void refreshData(DelicacyChoiceBean.ItemListBean itemListBean, Integer position, Context context) {
            super.refreshData(itemListBean, position, context);

            //共享元素的Intent
            final Intent intent = new Intent(context, DetailActivity.class);

            if (itemListBean != null) {

                DelicacyChoiceBean.ItemListBean.DataBean data = itemListBean.getData();
                intent.putExtra("itemListWBean", new Gson().toJson(itemListBean));

                //图片
                //String detailUrl = data.getCover().getDetail();
                String detailUrl = data.getCover().getDetail();
                Log.e("TAG", "detailUrl == " + detailUrl);
                if (detailUrl != null) {
                    //缓存图片,详情中从缓存中读取
                    GlideApp.with(context)
                            .load(detailUrl)
                            //.placeholder(R.drawable.ic_default)
                            //.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            //.skipMemoryCache(true)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .override(DensityUtils.dip2px(context, 280), DensityUtils.dip2px(context, 160))
                            .into(mIvHorizontalItemView);


                    //启动DetailActivity携带的图片URL地址
                    intent.putExtra("imageUrl", detailUrl);
                }

                //标题
                String title = data.getTitle();
                if (title != null) {
                    mTvHorizontalItemTitle.setText(title);
                }

                /**
                 * 类型 + 时长
                 */
                String category = data.getCategory();
                String duration = TimeUtils.duration(data.getDuration());

                if (category != null && duration != null) {
                    mTvHorizontalItemCategory.setText("#" + data.getCategory());
                    mTvHorizontalItemDuration.setText(" " + "/ " + duration);
                }
            }
        }
    }
}
