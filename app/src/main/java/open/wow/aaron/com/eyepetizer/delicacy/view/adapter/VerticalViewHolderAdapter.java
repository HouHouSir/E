package open.wow.aaron.com.eyepetizer.delicacy.view.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

public class VerticalViewHolderAdapter extends RecyclerView.ViewHolder {
    private static final String TAG = VerticalViewHolderAdapter.class.getSimpleName();
    private View item_layout_view;//总体布局
    private ImageView iv_item_view;//图片
    private TextView tv_item_title;//标题名称
    private TextView tv_item_category;//类型
    private TextView tv_item_duration;//时长
    private TextView tv_item_author;//作者


    public VerticalViewHolderAdapter(View inflate) {
        super(inflate);

        iv_item_view = (ImageView) inflate.findViewById(R.id.iv_item_view);
        tv_item_title = (TextView) inflate.findViewById(R.id.tv_item_title);
        tv_item_duration = (TextView) inflate.findViewById(R.id.tv_item_duration);
        tv_item_category = (TextView) inflate.findViewById(R.id.tv_item_category);
        tv_item_author = (TextView) inflate.findViewById(R.id.tv_item_author);
    }

    public void setData(List<DelicacyChoiceBean.ItemListBean> itemListWBeen, final Context context, final int position) {

        //共享元素的Intent
        final Intent intent = new Intent(context, DetailActivity.class);
        //intent.putExtra("position", position);

        //DelicacyChoiceBean.ItemListBean itemListBean = itemListWBeen.get(position);
        DelicacyChoiceBean.ItemListBean bean = itemListWBeen.get(position);
        //Log.e(TAG, "bean.toString() " + bean.toString());
        //数据集合
        //DelicacyChoiceBean.ItemListBean.DataBean data = itemListBean.getData();
        final DelicacyChoiceBean.ItemListBean.DataBean data = bean.getData();
        //Log.e(TAG,"data= " + data.toString());


//        Bundle bundle = new Bundle();
//        bundle.putParcelable("itemListWBean",bean);
//        intent.putExtras(bundle);

        intent.putExtra("itemListWBean", new Gson().toJson(bean));

        //类型
        //String dataType = data.getDataType();
        final String dataType = data.getDataType();

        //图片的URL地址
        String imageUrl = null;
        //标题
        String title = null;
        //类型
        String category = null;
        //时长
        String duration = null;
        //作者
        String author = null;

        //为广告页面
        if ("Banner".equals(dataType)) {
            //Log.e(TAG,"web== " + data.getActionUrl());
            //web网址
            final String actionUrl = data.getActionUrl();
            //图片网址
            imageUrl = data.getImage();

            //设置标题
            title = data.getTitle();

            //类型
            category = data.getCategory();

            //点击图片启动WebView
            iv_item_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent webIntent = new Intent(context,BannerWebActivity.class);
//                    webIntent.putExtra("actionUrl", actionUrl);
//                    context.startActivity(webIntent);
//                    return;
                }
            });
        }
        //为视频类
        if ("VideoBeanForClient".equals(dataType)) {
            //DelicacyChoiceBean.ItemListBean.DataBean.CoverBean cover = itemListBean.getData().getCover();
            DelicacyChoiceBean.ItemListBean.DataBean.CoverBean cover = bean.getData().getCover();
            //imageUrl = cover.getHomepage();
            imageUrl = cover.getDetail();

            //设置文字
            title = data.getTitle();

            //类型
            category = data.getCategory();
            //设置时长
            duration = TimeUtils.duration(data.getDuration());

            //作者
            DelicacyChoiceBean.ItemListBean.DataBean.AuthorBean authorBean = data.getAuthor();
            if (authorBean != null) {
                author = authorBean.getName();
            }
            //Log.e("TAG" , "作者 = " + author);

            // 这里指定了共享的视图元素
            iv_item_view.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) context, v,
                            context.getString(R.string.share_animation)
                    );
                    context.startActivity(intent, optionsCompat.toBundle());
                    Log.e(TAG,"position = " + position);
                    Log.e(TAG,"data.getTitle()= " + data.getTitle());
                }
            });
        }

        //加载图片
        if (imageUrl != null) {
            GlideApp.with(context)
                    .load(imageUrl)
                    //.placeholder(R.drawable.ic_default)
                    //.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .diskCacheStrategy( DiskCacheStrategy.NONE)
                    .into(iv_item_view);

            //启动DetailActivity携带的图片URL地址
            intent.putExtra("imageUrl", imageUrl);
            //intent.putExtra("imageUrl", itemListWBean.getData().getCover().getDetail());


            //Log.e("TAG", "获取的URL = " + imageUrl);
        }

        //设置标题
        if (title != null) {
            tv_item_title.setText(title);
        }

        //设置类型和时长
        if (category != null && duration != null) {
            tv_item_category.setText("#" + category);
            tv_item_duration.setText(" " + "/ " + duration);
        }

        //设置作者
        if (author != null) {
            tv_item_author.setText(author);
        }

    }
}
