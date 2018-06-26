package open.wow.aaron.com.eyepetizer.framework.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.author.AuthorFragment;
import open.wow.aaron.com.eyepetizer.delicacy.view.DelicacyChoiceFragment;
import open.wow.aaron.com.eyepetizer.found.view.FoundFragment;
import open.wow.aaron.com.eyepetizer.framework.base.BaseActivity;
import open.wow.aaron.com.eyepetizer.framework.dimens.generator.DimenGenerator;
import open.wow.aaron.com.eyepetizer.framework.view.adapter.OpenEyesViewPagerAdapter;
import open.wow.aaron.com.eyepetizer.mine.MineFragment;

/**
 * 作者：哇牛Aaron
 * 时间: 2017/7/20
 * 功能描述: 主页面
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private String[] titles = new String[]{"精选", "发现", "作者", "我的"};
    private int[] images = new int[]{
            R.drawable.tab_delicacy_choice_selector,
            R.drawable.tab_found_selector,
            R.drawable.tab_author_selector,
            R.drawable.tab_mine_selector};

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private DelicacyChoiceFragment mDelicacyChoiceFragment = new DelicacyChoiceFragment();
    private FoundFragment mFoundFragment = new FoundFragment();
    private AuthorFragment mAuthorFragment = new AuthorFragment();
    private MineFragment mMineFragment = new MineFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        //Log.e(TAG, "网络状况 " + NetUtils.isConnected(this));
        //long l = System.currentTimeMillis();
        //Log.e(TAG, "时间: " + l);

        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        Log.e("maxMemory:", Long.toString(maxMemory / (1024 * 1024)));
        //oppoR15 384M android:largeHeap="true" 申请更大内存以后为512M

//        mTabLayout.getTabAt(1).select();


        DimenGenerator dimenGenerator = new DimenGenerator();

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.table_layout);
    }

    private void initData() {
        //为viewPager添加Fragment集合
        mFragmentList.add(mDelicacyChoiceFragment);
        mFragmentList.add(mFoundFragment);
        mFragmentList.add(mAuthorFragment);
        mFragmentList.add(mMineFragment);


        //adapter
        mViewPager.setAdapter(new OpenEyesViewPagerAdapter(getSupportFragmentManager(), mFragmentList));

        //mTabLayout.setupWithViewPager(mViewPager);

        //viewPager与tabLayout关联
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //super.onTabSelected(tab);

                // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
                mViewPager.setCurrentItem(tab.getPosition(), false);
            }
        });
        //mTabLayout.setupWithViewPager(mViewPager);


//        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
//                mViewPager.setCurrentItem(tab.getPosition(), false);
//            }
//        });

//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
//                mViewPager.setCurrentItem(tab.getPosition(), false);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        setTabLayoutItem();
    }

    //设置Tab底部标题,给tab添加View 以满足上面是图片,下面是文字的需求.
    private void setTabLayoutItem() {
        for (int i = 0; i < titles.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null, false);

            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.tv_tab);
            tvTitle.setText(titles[i]);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            imgTab.setImageResource(images[i]);

            mTabLayout.addTab(tab);
        }
    }


    //问题二. Fragment重叠问题
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof DelicacyChoiceFragment) {
            mDelicacyChoiceFragment = (DelicacyChoiceFragment) fragment;
        }
        if (fragment instanceof FoundFragment) {
            mFoundFragment = (FoundFragment) fragment;
        }
        if (fragment instanceof AuthorFragment) {
            mAuthorFragment = (AuthorFragment) fragment;
        }
        if (fragment instanceof MineFragment) {
            mMineFragment = (MineFragment) fragment;
        }
    }

    /**
     * 问题四: 程序被杀死时恢复到以前被选中的状态
     * 保存选中的位置
     *
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
        int currentItem = mViewPager.getCurrentItem();
        //Log.e("TAG", "currentItem " + currentItem);
        outState.putInt("currentItem", currentItem);
    }

    /**
     * 恢复选中的位置
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            int currentItem = savedInstanceState.getInt("currentItem");
            //默认选中某项
            TabLayout.Tab tabAt = mTabLayout.getTabAt(currentItem);
            if (tabAt != null) {
                tabAt.select();
            }
//            mTabLayout.getTabAt(currentItem).select();
        }
    }

}
