package open.wow.aaron.com.eyepetizer.delicacy.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.detail.DetailActivity;
import open.wow.aaron.com.eyepetizer.framework.utils.GlideImageLoader;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/5/15
 * 功能描述:轮播图
 */

public class BannerAdapter extends RecyclerView.ViewHolder {
    private static final String TAG = BannerAdapter.class.getSimpleName();
    private List<DelicacyChoiceBean.ItemListBean> mItemList;
    private Context mContext;
    private Banner mBanner;

    public BannerAdapter(View itemView) {
        super(itemView);
        mBanner = (Banner) itemView.findViewById(R.id.banner_delicacy_choice);
    }

    void setData(final List<DelicacyChoiceBean.ItemListBean> itemList, final Context context, int position) {
        mItemList = itemList;
        mContext = context;


        final List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        //判断是否是轮播图,轮播图单独用mBanner处理
        for (int i = 0; i < itemList.size(); i++) {
            DelicacyChoiceBean.ItemListBean listBean = itemList.get(i);
            String type = listBean.getType();
            String tag = listBean.getTag();
            if ("video".equals(type) && "0".equals(tag)) {//符合2个条件的为轮播图

                String detailUrl = listBean.getData().getCover().getDetail();
                images.add(detailUrl);
                //Log.e(TAG, "detailUrl" + i + " = " + detailUrl);
                String title = listBean.getData().getTitle();
                titles.add(title);
                //Log.e(TAG, "title" + i + " = " + title);
            }
        }

        setBanner(images,titles);

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                DelicacyChoiceBean.ItemListBean bean = itemList.get(position);
                String imageUrl = bean.getData().getCover().getHomepage();

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("itemListWBean",new Gson().toJson(bean));
                intent.putExtra("imageUrl", imageUrl);
                context.startActivity(intent);
            }
        });
    }

    private void setBanner(List<String> images, List<String> titles) {
        //设置mBanner样式
//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());

        //设置图片集合
        mBanner.setImages(images);
        //设置mBanner动画效果
        setAnimation();
        //设置标题集合（当mBanner样式有显示title时）
        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(false);
        //设置轮播时间
        mBanner.setDelayTime(4500);
        //设置指示器位置（当mBanner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //mBanner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    private void setAnimation() {
        //mBanner.setBannerAnimation(Transformer.Default);//默认动画
        //mBanner.setBannerAnimation(Transformer.Accordion);//推拉窗样式
        //mBanner.setBannerAnimation(Transformer.BackgroundToForeground);//从小变大式
        //mBanner.setBannerAnimation(Transformer.ForegroundToBackground);//从大变小式
        //mBanner.setBannerAnimation(Transformer.CubeIn);//圆形拖拽感
        //mBanner.setBannerAnimation(Transformer.CubeOut);//圆形拖拽感
        //mBanner.setBannerAnimation(Transformer.DepthPage);//重叠翻页感
        //mBanner.setBannerAnimation(Transformer.FlipHorizontal);//横向旋转
        //mBanner.setBannerAnimation(Transformer.FlipVertical);//竖向旋转
        mBanner.setBannerAnimation(Transformer.RotateDown);//卡片式旋转
//        mBanner.setBannerAnimation(Transformer.RotateUp);//卡片式旋转
        //mBanner.setBannerAnimation(Transformer.ScaleInOut);//中间收缩,中间变大
        //mBanner.setBannerAnimation(Transformer.Stack);//推走的感觉
        //mBanner.setBannerAnimation(Transformer.Tablet);//折叠门
        //mBanner.setBannerAnimation(Transformer.ZoomIn);//中间吸入
        //mBanner.setBannerAnimation(Transformer.ZoomOut);//透明变化
//        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);//交卷的感觉
    }

}
