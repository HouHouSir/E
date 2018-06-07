package open.wow.aaron.com.eyepetizer.detail;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.delicacy.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.framework.GlideApp;
import open.wow.aaron.com.eyepetizer.framework.base.BaseActivity;
import open.wow.aaron.com.eyepetizer.framework.ui.FadeInTextView;
import open.wow.aaron.com.eyepetizer.framework.ui.GlideCircleTransform;
import open.wow.aaron.com.eyepetizer.framework.utils.DensityUtils;
import open.wow.aaron.com.eyepetizer.framework.utils.TimeUtils;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static open.wow.aaron.com.eyepetizer.R.id.iv_detail_title;
import static open.wow.aaron.com.eyepetizer.R.id.tv_detail_collection;
import static open.wow.aaron.com.eyepetizer.R.id.tv_detail_description;
import static open.wow.aaron.com.eyepetizer.R.id.tv_detail_video_num;

public class DetailActivity extends BaseActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    private FrameLayout mFlVideo;
    private ImageView mIvDetailTitle;
    private ImageView mIvDetailBack;
    private FadeInTextView mTvDetailTitle;
    private TextView mTvDetailCategory;
    private TextView mTvDetailDuration;
    private ImageView mIvDetailUp;
    private ImageView mIvDetailHomepage;
    private TextView mTvDetailAuthorName;
    private TextView mTvDetailAuthorDescription;
    private TextView mTvDetailVideoNum;
    private ImageView mIvDetailRight;
    private FadeInTextView mTvDetailDescription;
    private LinearLayout mLlDetailCollection;
    private TextView mTvDetailCollection;
    private LinearLayout mLlDetailShare;
    private TextView mTvDetailShare;
    private LinearLayout mLlDetailReply;
    private TextView mTvDetailReply;
    private LinearLayout mLlDetailDownLoad;
    private TextView mTvDetailDownLoad;
    private ImageView mIvDetailPlay;
    private Subscription subscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        setImageVideo();
        setMessage();
        setBack();
        setDelayLoad();
    }

    /**
     * 延迟显示
     */
    private void setDelayLoad() {
        subscribe = Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mIvDetailBack.setVisibility(View.VISIBLE);
                        mIvDetailPlay.setVisibility(View.VISIBLE);
                        mIvDetailUp.setVisibility(View.VISIBLE);
                    }
                });
    }

    /**
     * 设置两个按钮点击结束界面(共享动画)
     */
    private void setBack() {
        mIvDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewBackFinish();
            }
        });
        mIvDetailUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewBackFinish();
            }
        });
    }

    /**
     * 共享动画结束代码
     */
    private void ViewBackFinish() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //共享元素动画返回
                Instrumentation inst = new Instrumentation();
                inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            }
        }).start();
    }

    /**
     * 设置信息
     */
    private void setMessage() {
        String itemListWBean = getIntent().getStringExtra("itemListWBean");
        DelicacyChoiceBean.ItemListBean bean = new Gson().fromJson(itemListWBean, DelicacyChoiceBean.ItemListBean.class);
        Log.e(TAG, "" + bean.toString());
        Log.e(TAG, "bean.getType()" + bean.getType());
        Log.e(TAG, "bean.getType().getTitle()" + bean.getData().getTitle());
        DelicacyChoiceBean.ItemListBean.DataBean data = bean.getData();

        if (data != null) {
            //标题名称
            String title = data.getTitle();
            if (title != null) {
                mTvDetailTitle.setTextString(title).startFadeInAnimation();
            }
            //类型
            String category = data.getCategory();
            if (category != null) {
                mTvDetailCategory.setText("#" + category);
            }
            //设置时长
            String duration = TimeUtils.duration(data.getDuration());
            if (duration != null) {
                mTvDetailDuration.setText(" / " + duration);
            }

            //作者
            DelicacyChoiceBean.ItemListBean.DataBean.AuthorBean authorBean = data.getAuthor();
            if (authorBean != null) {
                //作者名称
                String author = authorBean.getName();
                mTvDetailAuthorName.setText(author);
                //作者视频条数
                int videoNum = authorBean.getVideoNum();
                mTvDetailVideoNum.setText(videoNum + "个视频");
                //作者描述信息
                String description = authorBean.getDescription();
                mTvDetailAuthorDescription.setText(description);
            }

            DelicacyChoiceBean.ItemListBean.DataBean.AuthorBean author = data.getAuthor();
            if (author != null) {
                //作者头像
                String homepageUrl = author.getIcon();
                //头像设置成圆形
                GlideApp.with(this)
                        .load(homepageUrl)
                        .centerCrop()
                        .transform(new GlideCircleTransform())
                        .override(DensityUtils.dip2px(this, 40), DensityUtils.dip2px(this, 40))
                        .into(mIvDetailHomepage);
            }

            //详解信息
            String description = data.getDescription();
            if (description != null) {
                mTvDetailDescription.setTextString(description).startFadeInAnimation();
            }

            //评论,分享,收藏
            DelicacyChoiceBean.ItemListBean.DataBean.ConsumptionBean consumption = data.getConsumption();
            if (consumption != null) {
                //收藏
                int collectionCount = consumption.getCollectionCount();
                mTvDetailCollection.setText(String.valueOf(collectionCount));
                //分享
                int shareCount = consumption.getShareCount();
                mTvDetailShare.setText(String.valueOf(shareCount));
                //回复
                int replyCount = consumption.getReplyCount();
                mTvDetailReply.setText(String.valueOf(replyCount));
            }
        } else {
            Log.e(TAG, "data==null");
        }
    }

    /**
     * 设置视频播放的图片
     */
    private void setImageVideo() {
        //传递的URL
        final String imageUrl = getIntent().getStringExtra("imageUrl");
        //Log.e("TAG", "传递的URL = " + imageUrl);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕宽度
        int windWidth = wm.getDefaultDisplay().getWidth();
        //获取屏幕高度
        int height = wm.getDefaultDisplay().getHeight();
        Log.e(TAG, "屏幕宽度= " + windWidth + " 屏幕高度= " + height);

        /**
         * 关键效果  把图片按比例扩大/缩小到View的宽度，置于顶部显示
         * 解决图片大小不一致问题
         * 等同于 android:scaleType="fitStart"
         */
        //mIvDetailTitle.setScaleType(ImageView.ScaleType.FIT_START);

        //仅从缓存中读取
        GlideApp.with(DetailActivity.this)
                .load(imageUrl)
                .onlyRetrieveFromCache(true)
                .dontTransform()
//                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                .override(windWidth, DensityUtils.dip2px(DetailActivity.this, 380))
                .override(windWidth, height / 2 + DensityUtils.dip2px(DetailActivity.this, 30))
                .centerCrop()
                .into(mIvDetailTitle);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mIvDetailPlay = (ImageView) findViewById(R.id.iv_detail_play);
        mFlVideo = (FrameLayout) findViewById(R.id.fl_video);
        mIvDetailTitle = (ImageView) findViewById(iv_detail_title);
        mIvDetailBack = (ImageView) findViewById(R.id.iv_detail_back);
        mTvDetailTitle = (FadeInTextView) findViewById(R.id.tv_detail_title);
        mTvDetailCategory = (TextView) findViewById(R.id.tv_detail_category);
        mTvDetailDuration = (TextView) findViewById(R.id.tv_detail_duration);
        mIvDetailUp = (ImageView) findViewById(R.id.iv_detail_up);
        mIvDetailHomepage = (ImageView) findViewById(R.id.iv_detail_homepage);
        mTvDetailAuthorName = (TextView) findViewById(R.id.tv_detail_author_name);
        mTvDetailAuthorDescription = (TextView) findViewById(R.id.tv_detail_author_description);
        mTvDetailVideoNum = (TextView) findViewById(tv_detail_video_num);
        mIvDetailRight = (ImageView) findViewById(R.id.iv_detail_right);
        mTvDetailDescription = (FadeInTextView) findViewById(tv_detail_description);
        mLlDetailCollection = (LinearLayout) findViewById(R.id.ll_detail_collection);
        mTvDetailCollection = (TextView) findViewById(tv_detail_collection);
        mLlDetailShare = (LinearLayout) findViewById(R.id.ll_detail_share);
        mTvDetailShare = (TextView) findViewById(R.id.tv_detail_share);
        mLlDetailReply = (LinearLayout) findViewById(R.id.ll_detail_reply);
        mTvDetailReply = (TextView) findViewById(R.id.tv_detail_reply);
        mLlDetailDownLoad = (LinearLayout) findViewById(R.id.ll_detail_down_load);
        mTvDetailDownLoad = (TextView) findViewById(R.id.tv_detail_down_load);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
        subscribe.unsubscribe();
    }
}
