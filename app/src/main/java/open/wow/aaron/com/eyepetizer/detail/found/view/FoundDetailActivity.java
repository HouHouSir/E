package open.wow.aaron.com.eyepetizer.detail.found.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.detail.found.presenter.FoundDetailP;
import open.wow.aaron.com.eyepetizer.detail.found.view.adapter.FoundDetailAdapter;
import open.wow.aaron.com.eyepetizer.framework.GlideApp;
import open.wow.aaron.com.eyepetizer.framework.base.BaseActivity;

/**
 * 发现的详情页面
 */
public class FoundDetailActivity extends BaseActivity implements IFoundDetailV {
    private static final String TAG = FoundDetailActivity.class.getSimpleName();
    private FoundDetailP mFoundDetailP;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    //private Toolbar mToolbar;
    private TextView mTvFoundDetailDescription;
    private ImageView mIvFoundDetailTitle;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_detail);
        initView();
        initData();
        initOnClick();
    }

    /**
     * 初始化点击事件
     */
    private void initOnClick() {
//        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_left_black));
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }


    @Override
    public void disposeDataFromNet(DelicacyChoiceBean delicacyChoiceBean) {
        if (delicacyChoiceBean != null) {

            Log.e(TAG, "delicacyChoiceBean != null");
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(new FoundDetailAdapter(delicacyChoiceBean, this));

        } else {
            Log.e(TAG, "delicacyChoiceBean == null");
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        setUrlID();
        getDataFromNet();
        setCollapsingToolbarLayout();
        mRefreshLayout.setEnableRefresh(false);//是否启用下拉刷新功能
        mRefreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
    }

    /**
     * 设置折叠标题
     */
    private void setCollapsingToolbarLayout() {
        //1.获取FoundFragment传递过来的值
        String headerImage = getIntent().getStringExtra("headerImage");
        String description = getIntent().getStringExtra("description");
        String name = getIntent().getStringExtra("name");

        //2.设置CollapsingToolbarLayout
        //设置标题
        mCollapsingToolbarLayout.setTitle(name);
        //设置折叠的时候标题显示字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        //设置展开的时候标题显示字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        //设置设置状态栏颜色
        mCollapsingToolbarLayout.setStatusBarScrimColor(Color.GRAY);
        //3设置描述
        mTvFoundDetailDescription.setText(description);

        //4.设置图片
        GlideApp.with(this)
                .load(headerImage)
                //.placeholder(R.drawable.ic_default)
                //.skipMemoryCache(true)
                .diskCacheStrategy( DiskCacheStrategy.NONE)
                .into(mIvFoundDetailTitle);
    }

    /**
     * P层加载网络数据
     */
    private void getDataFromNet() {
        mFoundDetailP.getFoundDataFromNet(id);
    }

    /**
     * 设置点击Item的position对应的URL中的id
     */
    private void setUrlID() {
        int position = getIntent().getIntExtra("position", 0);
        switch (position) {
            case 0://时尚
                id = "24";
                break;
            case 1://运动
                id = "18";
                break;
            case 2://创意
                id = "2";
                break;
            case 3://广告
                id = "14";
                break;
            case 4://音乐
                id = "20";
                break;
            case 5://旅行
                id = "6";
                break;
            case 6://生活
                id = "36";
                break;
            case 7://记录
                id = "22";
                break;
            case 8://开胃
                id = "4";
                break;
            case 9://游戏
                id = "30";
                break;
            case 10://萌宠
                id = "26";
                break;
            case 11://动画
                id = "10";
                break;
            case 12://科技
                id = "32";
                break;
            case 13://剧情
                id = "12";
                break;
            case 14://搞笑
                id = "28";
                break;
            case 15://影视
                id = "8";
                break;
            case 16://综艺
                id = "38";
                break;
            case 17://集锦
                id = "34";
                break;
        }

    }

    /**
     * 初始化视图
     */
    private void initView() {
        mFoundDetailP = new FoundDetailP(this);
        mRecyclerView = findViewById(R.id.recycler_view_found_detail);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        mRefreshLayout = findViewById(R.id.refreshLayout_found_detail);
        mIvFoundDetailTitle =  findViewById(R.id.iv_found_detail_title);
        mTvFoundDetailDescription =  findViewById(R.id.tv_found_detail_description);
        //mToolbar =  findViewById(R.id.toolbar_found_detail);

    }
}
