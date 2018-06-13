package open.wow.aaron.com.eyepetizer.found;


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
import open.wow.aaron.com.eyepetizer.found.model.bean.FoundBean;
import open.wow.aaron.com.eyepetizer.found.presenter.FoundP;
import open.wow.aaron.com.eyepetizer.found.view.IFoundV;
import open.wow.aaron.com.eyepetizer.found.view.adapter.FoundAdapter;
import open.wow.aaron.com.eyepetizer.framework.base.BaseFragment;
import open.wow.aaron.com.eyepetizer.framework.ui.DividerGridItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoundFragment extends BaseFragment implements IFoundV {
    private static final String TAG = FoundFragment.class.getSimpleName();
    private FoundP mFoundP;
    private ProgressBar mProgressbarFound;
    private RecyclerView mRecyclerViewFound;


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
    public void disposeDataFromNet(ArrayList<FoundBean> arrayList) {
        if (arrayList != null) {
            FoundAdapter adapter = new FoundAdapter(arrayList,getActivity());
            mRecyclerViewFound.setLayoutManager(new GridLayoutManager(getActivity(),2));
            mRecyclerViewFound.addItemDecoration(new DividerGridItemDecoration(getActivity()));
//            mRecyclerViewFound.addItemDecoration(new DividerGridItemDecoration(10, Color.parseColor("#ffffff")));
//            mRecyclerViewFound.addItemDecoration(new GridDividerItemDecorationBug(10, Color.parseColor("#ffffff")));
            //mRecyclerViewFound.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//            mRecyclerViewFound.addItemDecoration(new GridSpacingItemDecoration(10,10,false,10));
            mRecyclerViewFound.setAdapter(adapter);

        }else {
            Log.e(TAG,"木有数据呀");
        }
    }
}
