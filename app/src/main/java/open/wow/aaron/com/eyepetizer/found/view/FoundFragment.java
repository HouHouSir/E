package open.wow.aaron.com.eyepetizer.found.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.detail.found.view.FoundDetailActivity;
import open.wow.aaron.com.eyepetizer.found.model.bean.FoundBean;
import open.wow.aaron.com.eyepetizer.found.presenter.FoundP;
import open.wow.aaron.com.eyepetizer.found.view.adapter.FoundAdapter;
import open.wow.aaron.com.eyepetizer.framework.base.BaseFragment;
import open.wow.aaron.com.eyepetizer.framework.ui.GridDividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoundFragment extends BaseFragment implements IFoundV {
    private static final String TAG = FoundFragment.class.getSimpleName();
    private FoundP mFoundP;
    private ProgressBar mProgressbarFound;
    private RecyclerView mRecyclerViewFound;
    private FoundAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_found, container, false);
        mProgressbarFound = (ProgressBar) inflate.findViewById(R.id.progressbar_found);
        mRecyclerViewFound = (RecyclerView) inflate.findViewById(R.id.recycler_view_found);

        mFoundP = new FoundP(this);

        return inflate;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        //请求网络数据
        mFoundP.getFoundDataFromNet();
    }

    @Override
    public void showDialog() {
        mProgressbarFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        mProgressbarFound.setVisibility(View.GONE);
    }

    @Override
    public void disposeDataFromNet(final ArrayList<FoundBean> arrayList) {
        if (arrayList != null) {
            mAdapter = new FoundAdapter(arrayList, getActivity());
            mRecyclerViewFound.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mRecyclerViewFound.addItemDecoration(new GridDividerItemDecoration(10, Color.parseColor("#ffffff")));
            mRecyclerViewFound.setAdapter(mAdapter);

            //Item点击事件
            mAdapter.setOnItemClickListener(new FoundAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
                    starFoundDetailActivity(position,arrayList);
                }
            });
        } else {
            Log.e(TAG, "木有数据呀");
            hideDialog();
            //Toast.makeText(getActivity(), "网络错误,请重试!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Item点击事件中 启动Found详情界面
     *
     * @param position 点击了那个位置 , 根据位置的不同 , 详情页中联网请求不同内容
     * @param arrayList list数据集合  1.用于传递图片URL(headerImage) 2.标题名称(description)
     */
    private void starFoundDetailActivity(int position, ArrayList<FoundBean> arrayList) {
        Intent intent = new Intent(getActivity(), FoundDetailActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("headerImage",arrayList.get(position).getHeaderImage());
        intent.putExtra("description",arrayList.get(position).getDescription());
        intent.putExtra("name",arrayList.get(position).getName());
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        RefWatcher refWatcher = OpenEyesApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);

    }
}
