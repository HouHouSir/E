package open.wow.aaron.com.eyepetizer.delicacy.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.delicacy.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.delicacy.presenter.DelicacyP;
import open.wow.aaron.com.eyepetizer.delicacy.view.adapter.AllRecyclerAdapter;
import open.wow.aaron.com.eyepetizer.framework.base.BaseFragment;

/**
 * 作者：哇牛Aaron
 * 时间: 2017/7/20
 * 功能描述: 精选
 */

public class DelicacyChoiceFragment extends BaseFragment implements IDelicacyV {
    private static final String TAG = DelicacyChoiceFragment.class.getSimpleName();
    private DelicacyP mP;
    private List<DelicacyChoiceBean.ItemListBean> itemListBeen;
    private DelicacyChoiceBean mBean;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private AllRecyclerAdapter allRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //初始化P层
        mP = new DelicacyP(this);

        View view = inflater.inflate(R.layout.fragment_delicacy_choice, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));

        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //下拉刷新-->回调refresh()方法
                mP.getDataFromNetRefresh();
                //refreshLayout.setNoMoreData(false);//恢复上拉状态
            }
        });
        //上拉加载更多
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshLoadMore();
                //refreshlayout.finishLoadMore();
            }
        });
    }


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        mP.getDataFromNet();
        Log.e(TAG,"onFragmentFirstVisible()");
    }

    @Override
    public void showDetail() {

    }

    @Override
    public void search() {

    }

    //BaseFragment懒加载
    @Override
    public void disposeDataFromNet(DelicacyChoiceBean delicacyChoiceBean) {
        setRecyclerView(delicacyChoiceBean);
    }


    @Override
    public void refresh(DelicacyChoiceBean delicacyChoiceBean) {
        //refreshLayout.setNoMoreData(false);//恢复上拉状态
        itemListBeen.clear();
        itemListBeen.addAll(delicacyChoiceBean.getItemList());
        allRecyclerAdapter.notifyDataSetChanged();

        mBean = delicacyChoiceBean;
        refreshLayout.finishRefresh();
    }

    @Override
    public void loadMore(DelicacyChoiceBean delicacyChoiceBean) {

        if (delicacyChoiceBean == null) {
            //Toast.makeText(getActivity(), "木有数据呀", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "loadMore()木有数据呀");
        } else {
            allRecyclerAdapter.notifyDataSetChanged();
            itemListBeen.addAll(delicacyChoiceBean.getItemList());
            mBean = delicacyChoiceBean;
        }
        refreshLayout.finishLoadMore();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mP.stopNet();
    }

    /**
     * 设置RecycleView
     *
     * @param delicacyChoiceBean
     */
    private void setRecyclerView(DelicacyChoiceBean delicacyChoiceBean) {
        if (delicacyChoiceBean == null) {
            //Toast.makeText(getActivity(), "木有数据呀", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "setRecyclerView()木有数据呀");
        } else {
            //String nextPageUrl = delicacyChoiceBean.getNextPageUrl();
            //Log.e(TAG,"nextPageUrl: " + nextPageUrl);
            itemListBeen = delicacyChoiceBean.getItemList();
            allRecyclerAdapter = new AllRecyclerAdapter(itemListBeen, getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(allRecyclerAdapter);
            //allRecyclerAdapter.setHasStableIds(true);//去除默认动画

            //recyclerView添加头
            allRecyclerAdapter.setHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.view_banner, null, false));
            mBean = delicacyChoiceBean;
        }
    }

    /**
     * 加载更多数据的网络请求
     */
    private void refreshLoadMore() {
        //Log.e(TAG,"mBean.getNextPageUrl()= " + mBean.getNextPageUrl());
        if (mBean.getNextPageUrl() == null) {
            Log.e(TAG,"mBean.getNextPageUrl()= 空");
            //refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
            ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.footer_finish_no_more);//"没有更多数据";
            refreshLayout.finishLoadMore();
        } else {
            ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.footer_finish);//"加载完成";
            //String data = String.valueOf(mBean.getNextPublishTime());
            String nextPageUrl = mBean.getNextPageUrl();
            //Log.e(TAG,"nextPageUrl= "+ nextPageUrl);
            String[] dateString = nextPageUrl.split("date=");
            String s = dateString[1];
            String[] b = s.split("&num=");  //以每个A作为分割点,得到的是
            String data = b[0];
            String next = b[1];
            String[] split = next.split("&page=");
            String num = split[0];
            String pager = split[1];
            Log.e(TAG, "data= " + data);
            Log.e(TAG, "num= " + num);
            Log.e(TAG, "pager= " + pager);
            //加载更多数据-->回调loadMore()方法
            mP.getDataFromNetLoadMore(data, num, pager);
        }
    }


    /**
     * 刷新2种方式
     * (1) 有点是作者提供,缺点是第一次没有数据不提示,再次刷新才提示
     * 下拉刷新的时候  refreshLayout.setNoMoreData(false);//恢复上拉状态
     * 上拉加载更多的时候  refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
     * (2)自创修改文字
     * ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.footer_finish_no_more);//"没有更多数据";
     * ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.footer_finish);//"加载完成";
     *
     */
}
