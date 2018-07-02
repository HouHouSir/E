package open.wow.aaron.com.eyepetizer.author;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.author.presenter.AuthorP;
import open.wow.aaron.com.eyepetizer.author.view.IAuthorV;
import open.wow.aaron.com.eyepetizer.author.view.adapter.AuthorAdapter;
import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.framework.base.BaseFragment;
import open.wow.aaron.com.eyepetizer.framework.retrofit.ApiStores;

/**
 * 作者界面
 */
public class AuthorFragment extends BaseFragment implements IAuthorV<DelicacyChoiceBean, String> {
    private static final String TAG = AuthorFragment.class.getSimpleName();
    private AuthorP mAuthorP;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private String nextUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_author, container, false);
        mRecyclerView = inflate.findViewById(R.id.recycler_view);
        mSmartRefreshLayout = inflate.findViewById(R.id.refreshLayout);
        mAuthorP = new AuthorP(this);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mAuthorP.getDataFromNetLoadMore(nextUrl);
            }
        });
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();

        mAuthorP.getDataFromNet();
    }

    @Override
    public void disposeDataFromNet(DelicacyChoiceBean delicacyChoiceBean) {
        if (delicacyChoiceBean != null) {
            Log.e(TAG, "delicacyChoiceBean= " + delicacyChoiceBean.getItemList().toString());
            nextUrl = delicacyChoiceBean.getNextPageUrl();


            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(new AuthorAdapter(getActivity(),delicacyChoiceBean));
        }
    }

    @Override
    public void disposeDataFromNetError(String error) {
        Log.e(TAG, "error= " + error);
        if (error.equals(ApiStores.FAIL_MESSAGE)) {
            Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refresh(DelicacyChoiceBean delicacyChoiceBean) {

    }

    @Override
    public void loadMore(DelicacyChoiceBean delicacyChoiceBean) {
        if (delicacyChoiceBean != null) {
            Log.e(TAG, "loadMore() delicacyChoiceBean= " + delicacyChoiceBean.getItemList().toString());
        }
    }

}
