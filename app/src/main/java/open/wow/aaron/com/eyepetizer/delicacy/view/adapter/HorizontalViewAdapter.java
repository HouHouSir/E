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
import open.wow.aaron.com.eyepetizer.framework.utils.DensityUtils;
import open.wow.aaron.com.eyepetizer.framework.utils.TimeUtils;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/5/15
 * 功能描述:
 */

public class HorizontalViewAdapter extends RecyclerView.ViewHolder {
    private static final String TAG = HorizontalViewAdapter.class.getSimpleName();
    //private ImageView iv_item_horizontal_title;//单个web图片
    private RecyclerView horizontal_recycler_view;
    private Context context;

    public HorizontalViewAdapter(View itemView) {
        super(itemView);
        //iv_item_horizontal_title = (ImageView) itemView.findViewById(iv_item_horizontal_title);
        horizontal_recycler_view = (RecyclerView) itemView.findViewById(R.id.horizontal_recycler_view);
    }

    public void setData(List<DelicacyChoiceBean.ItemListBean> itemListWBeen, Context context, int position, RecyclerView.RecycledViewPool pool) {
        this.context = context;

        //DelicacyChoiceBean.ItemListBean itemListBean = itemListWBeen.get(position);
        DelicacyChoiceBean.ItemListBean itemListBean = itemListWBeen.get(position);

        //RecyclerView设置layout
        LinearLayoutManager layout = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(layout);
        //RecyclerView设置adapter
        horizontal_recycler_view.setAdapter(new HorizontalViewHolderOneAdapter(itemListBean));
        //设置RecyclerViewPool
        //layout.setRecycleChildrenOnDetach(true);
//        if (pool != null) {
//            horizontal_recycler_view.setRecycledViewPool(pool);
//        }

        //Log.e(TAG, "itemListBean" + itemListBean.toString());
        //标题URL地址
        //String cover = itemListBean.getData().getHeaderBean().getCover();

        //Log.e(TAG,"position = " + position);
        DelicacyChoiceBean.ItemListBean.DataBean data = itemListWBeen.get(position).getData();
        //Log.e(TAG,data.toString());
//        DelicacyChoiceBean.ItemListBean.DataBean.HeaderBean headerBean = data.getHeaderBean();
        //Log.e(TAG,itemListWBeen.toString());

//        if (itemListBean != null) {
//            DelicacyChoiceBean.ItemListBean.DataBean data = itemListBean.getData();
//            String cover = data.getHeaderBean().getCover();
//            Log.e(TAG, "标题URL地址,cover= " + cover);
//            Glide.with(this.context)
//                    .load(cover)
//                    .centerCrop().into(iv_item_horizontal_title);
//        }


        //这里HeaderBean传过来的是null只能用一个固定的URL代替了
//        Glide.with(this.context)
//                .load("http://img.kaiyanapp.com/7e5b0d4ffe458733919d9d701a715218.jpeg?imageMogr2/quality/60/format/jpg")
//                .into(iv_item_horizontal_title);
        //备用URL
        //http://img.kaiyanapp.com/7e5b0d4ffe458733919d9d701a715218.jpeg?imageMogr2/quality/60/format/jpg
        //web网址
        //http://www.eyepetizer.net/videos_article.html?nid=234&shareable=true
    }

    //Adapter
    private class HorizontalViewHolderOneAdapter extends RecyclerView.Adapter<ViewHolder> {

        //private List<DelicacyChoiceBean.ItemListBean> listBeanList;
        private List<DelicacyChoiceBean.ItemListBean> itemList;

        //构造函数得到数据
        HorizontalViewHolderOneAdapter(DelicacyChoiceBean.ItemListBean itemListBean) {
            //listBeanList = itemListBean.getData().getItemList();

            itemList = itemListBean.getData().getItemList();

//            Log.e("TAG","listBeanList == " + listBeanList);
//
//            Log.e("TAG","itemListBean.getData() == " + itemListBean.getData());
//
//            DelicacyChoiceBean.ItemList.Data data = itemListBean.getData();
//
//            Log.e("TAG","data.getCount " + data.getCount());
//            Log.e("TAG","data.getHeaderBean " + data.getHeaderBean());


//            Log.e("TAG", "Cover " + listBeanList.get(0).getData().getCover());
//            Log.e("TAG", "Cover " + listBeanList.get(1).getData().getCover());
//            Log.e("TAG", "Cover " + listBeanList.get(2).getData().getCover());
//            Log.e("TAG", "Cover " + listBeanList.get(3).getData().getCover());
//            Log.e("TAG", "Cover " + listBeanList.get(4).getData().getCover());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_horizontal_one, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            //Log.e("TAG", "position == " + position);
            //DelicacyChoiceBean.ItemListBean itemListBean = listBeanList.get(position);

            //DelicacyChoiceBean.ItemListBean.DataBean.ItemList itemListBean = this.itemList.get(position);
            DelicacyChoiceBean.ItemListBean itemListNBean = itemList.get(position);
            //共享元素的Intent
            final Intent intent = new Intent(context, DetailActivity.class);

            if (itemListNBean != null) {

                //DelicacyChoiceBean.ItemListBean.DataBean data = itemListBean.getData();

                //DelicacyChoiceBean.ItemListBean.DataBean.ItemList.Data data = itemListBean.getData();
                DelicacyChoiceBean.ItemListBean.DataBean data = itemListNBean.getData();
                intent.putExtra("itemListWBean", new Gson().toJson(itemListNBean));

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
                            .into(holder.iv_horizontal_item_view);

                    //启动DetailActivity携带的图片URL地址
                    intent.putExtra("imageUrl", detailUrl);
                }

                //标题
                String title = data.getTitle();
                if (title != null) {
                    holder.tv_horizontal_item_title.setText(title);
                }

                /**
                 * 类型 + 时长
                 */
                String category = data.getCategory();
                String duration = TimeUtils.duration(data.getDuration());

                if (category != null && duration != null) {
                    holder.tv_horizontal_item_category.setText("#" + data.getCategory());
                    holder.tv_horizontal_item_duration.setText(" " + "/ " + duration);
                }
            }


            holder.iv_horizontal_item_view.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    // 这里指定了共享的视图元素
                    if (android.os.Build.VERSION.SDK_INT > 20) {
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                (Activity) context, v,
                                context.getString(R.string.share_animation)
                        );
                        context.startActivity(intent, optionsCompat.toBundle());
                    }else {
                        context.startActivity(intent);
                    }



                    //context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            //三元运算符
            return itemList == null ? 0 : itemList.size();
        }
    }

    //横向RecyclerView的item的布局视图
    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_horizontal_item_view;//图片
        private TextView tv_horizontal_item_title;//标题
        private TextView tv_horizontal_item_category;//类型
        private TextView tv_horizontal_item_duration;//时长

        ViewHolder(View itemView) {
            super(itemView);

            iv_horizontal_item_view = (ImageView) itemView.findViewById(R.id.iv_horizontal_item_view);
            tv_horizontal_item_title = (TextView) itemView.findViewById(R.id.tv_horizontal_item_title);
            tv_horizontal_item_category = (TextView) itemView.findViewById(R.id.tv_horizontal_item_category);
            tv_horizontal_item_duration = (TextView) itemView.findViewById(R.id.tv_horizontal_item_duration);
        }
    }
}
