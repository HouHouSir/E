package open.wow.aaron.com.eyepetizer.author.view.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.framework.base.BaseHolder;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/29
 * 功能描述:
 */
public class AuthorAdapter extends RecyclerView.Adapter<BaseHolder> {
    private static final String TAG = AuthorAdapter.class.getSimpleName();
    private FragmentActivity mFragmentActivity;
    private List<DelicacyChoiceBean.ItemListBean> mBeanList;

    private int AUTHOR_TITLE = 1;//标题item
    private int AUTHOR_HORIZONTAL = 2;//横向RecycleView item
    private int NULL_VIEW = 3;

    public AuthorAdapter(FragmentActivity fragmentActivity, DelicacyChoiceBean delicacyChoiceBean) {
        this.mFragmentActivity = fragmentActivity;
        mBeanList = delicacyChoiceBean.getItemList();
        Log.e(TAG, "delicacyChoiceBean = " + delicacyChoiceBean.toString());
        Log.e(TAG, "mBeanList = " + mBeanList.toString());


        Log.e(TAG,"icon= " + delicacyChoiceBean.getItemList().get(1).getData().getHeaderBean().getIcon());
    }



    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AUTHOR_TITLE) {
//            return new AuthorTitleViewHolder(LayoutInflater.from(mFragmentActivity)
//                    .inflate(R.layout.item_author_title, parent, false));
            return new AuthorTitleViewHolder(R.layout.item_author_title, parent, viewType);
        } else if (viewType == AUTHOR_HORIZONTAL) {
//            return new AuthorHorizontalHolder.AuthorItemHorizontalViewHolder(LayoutInflater.from(mFragmentActivity)
//                    .inflate(R.layout.item_author_horizontal, parent, false));

//            return new AuthorHorizontalHolder.AuthorItemHorizontalViewHolder(LayoutInflater.from(mFragmentActivity)
//                    .inflate(R.layout.item_recyclerview_author_horizontal, parent, false));
            return new AuthorHorizontalHolder(R.layout.item_recyclerview_author_horizontal, parent, viewType);

        } else if (viewType == NULL_VIEW) {
            return new BaseHolder(R.layout.repeat_banner, parent, viewType);
        } else {
            return new BaseHolder(R.layout.repeat_banner, parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (holder instanceof AuthorTitleViewHolder) {
            String text = mBeanList.get(position).getData().getText();


            //Toast.makeText(mFragmentActivity, text + "", Toast.LENGTH_SHORT).show();
            ((AuthorTitleViewHolder) holder).tv_author_title.setText(text);
        } else if (holder instanceof AuthorHorizontalHolder) {


            Log.e(TAG, "holder instanceof AuthorHorizontalHolder");
            ((AuthorHorizontalHolder)holder).refreshData(mBeanList.get(position).getData(), position, mFragmentActivity);
            //holder.refreshData(mBeanList.get(position).getData(), position, mFragmentActivity);

        }
    }

    @Override
    public int getItemViewType(int position) {

        String type = mBeanList.get(position).getType();

        //标题:例如,最新更新作者
        if (TextUtils.equals(type, "leftAlignTextHeader")) return AUTHOR_TITLE;

        //横向RecycleView
        if (TextUtils.equals(type, "videoCollectionWithBrief")) return AUTHOR_HORIZONTAL;

        return NULL_VIEW;
//        return super.getItemViewType(position);
    }

    /**
     * 标题
     */
    class AuthorTitleViewHolder extends BaseHolder {
        TextView tv_author_title;//标题

        public AuthorTitleViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);

            tv_author_title = itemView.findViewById(R.id.tv_author_title);
        }
    }
}
