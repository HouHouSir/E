package open.wow.aaron.com.eyepetizer.delicacy.view.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
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
 * 时间: 2018/5/15
 * 功能描述:已弃用
 */

public class HorizontalViewTowAdapter extends RecyclerView.ViewHolder {
    private static final String TAG = HorizontalViewTowAdapter.class.getSimpleName();
    private Context context;
    private RecyclerView horizontal_recycler_view_tow;

    public HorizontalViewTowAdapter(View itemView) {
        super(itemView);
        horizontal_recycler_view_tow = (RecyclerView) itemView.findViewById(R.id.horizontal_recycler_view_tow);
    }

    public void setData(List<DelicacyChoiceBean.ItemListBean> itemListWBeen, Context context, int position) {
        this.context = context;

        DelicacyChoiceBean.ItemListBean itemListWBean = itemListWBeen.get(position);

        Log.e("TAG", "position: " + position);
        LinearLayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view_tow.setAdapter(new HorizontalTowAdapter(itemListWBean));
        horizontal_recycler_view_tow.setLayoutManager(layout);

        //设置RecyclerViewPool
//        layout.setRecycleChildrenOnDetach(true);
//        if (pool != null) {
//            horizontal_recycler_view_tow.setRecycledViewPool(pool);
//        }
    }

    private class HorizontalTowAdapter extends RecyclerView.Adapter<ViewHolder> {
        private DelicacyChoiceBean.ItemListBean mItemListBean;

        public HorizontalTowAdapter(DelicacyChoiceBean.ItemListBean itemListWBean) {
            this.mItemListBean = itemListWBean;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_horizontal_tow, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //DelicacyChoiceBean.ItemListBean itemListBean = mItemListBean.getData().getItemList().get(position);
            DelicacyChoiceBean.ItemListBean itemListBean = mItemListBean.getData().getItemList().get(position);

            //共享元素的Intent
            final Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("itemListWBean", new Gson().toJson(itemListBean));

            if (itemListBean != null) {
                //1.根据分类处理,这里因为第二页横向RecycleView,类型为squareCardCollection,跟其它不一样,需单独处理
//                String type = itemListBean.getType();
//                if (type != null && "squareCardCollection".equals(type)) {
//
//                    return;
//                }
                //2.这里处理 videoCollectionOfFollow 类型
                //DelicacyChoiceBean.ItemListBean.DataBean data = itemListBean.getData();
                DelicacyChoiceBean.ItemListBean.DataBean data = itemListBean.getData();

                Log.e(TAG, "itemListBean= " + itemListBean.toString());
                DelicacyChoiceBean.ItemListBean.DataBean.CoverBean cover = data.getCover();
                if (cover == null) return;
                //图片
                String detailUrl = cover.getDetail();
                if (detailUrl != null) {

                    //缓存图片,详情中从缓存中读取
                    GlideApp.with(context)
                            .load(detailUrl)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(holder.iv_horizontal_item_view_tow);

                    //启动DetailActivity携带的图片URL地址
                    intent.putExtra("imageUrl", detailUrl);

                }

                //标题
                String title = data.getTitle();
                if (title != null) {
                    holder.tv_horizontal_item_title_tow.setText(title);
                }

                /**
                 * 类型 + 时长
                 */
                String category = data.getCategory();
                String duration = TimeUtils.duration(data.getDuration());

                if (category != null && duration != null) {
                    holder.tv_horizontal_item_category_tow.setText("#" + data.getCategory());
                    holder.tv_horizontal_item_duration_tow.setText(" " + "/ " + duration);
                }
            }


            holder.iv_horizontal_item_view_tow.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    // 这里指定了共享的视图元素

                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) context, v,
                            context.getString(R.string.share_animation)
                    );
                    context.startActivity(intent, optionsCompat.toBundle());


                    //context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            //三元运算符
            return mItemListBean == null ? 0 : mItemListBean.getData().getItemList().size();
        }
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_horizontal_item_view_tow;//图片
        private TextView tv_horizontal_item_title_tow;//标题
        private TextView tv_horizontal_item_category_tow;//类型
        private TextView tv_horizontal_item_duration_tow;//时长

        public ViewHolder(View itemView) {
            super(itemView);

            iv_horizontal_item_view_tow = (ImageView) itemView.findViewById(R.id.iv_horizontal_item_view_tow);
            tv_horizontal_item_title_tow = (TextView) itemView.findViewById(R.id.tv_horizontal_item_title_tow);
            tv_horizontal_item_category_tow = (TextView) itemView.findViewById(R.id.tv_horizontal_item_category_tow);
            tv_horizontal_item_duration_tow = (TextView) itemView.findViewById(R.id.tv_horizontal_item_duration_tow);
        }

    }
}
