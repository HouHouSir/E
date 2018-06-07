package open.wow.aaron.com.eyepetizer.delicacy.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.delicacy.bean.DelicacyChoiceBean;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/5/15
 * 功能描述:
 */

public class AllRecyclerAdapter extends RecyclerView.Adapter {
    private List<DelicacyChoiceBean.ItemListBean> mItemList;
    private Context mContext;

    private final int BANNER = 0;//轮播图banner
    private final int HORIZONTAL_VIEW_ONE = 1;//横向1
    private final int HORIZONTAL_VIEW_TOW = 2;//横向2
    private final int VERTICAL_VIEW = 3;//纵向
    private final int TITLE = 4;//单个纵向
    private final int REPEAT_BANNER = 5;//重复的banner



    private View mHeaderView;

    /**
     * 添加头
     *
     * @param headerView 头部视图
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * 获取头
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    public AllRecyclerAdapter(List<DelicacyChoiceBean.ItemListBean> itemList, Context context) {
        mItemList = itemList;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HORIZONTAL_VIEW_ONE:
                return new HorizontalViewOneAdapter(
                        LayoutInflater.from(mContext)
                                .inflate(R.layout.item_horizontal_list_one, parent, false));

            case HORIZONTAL_VIEW_TOW:
                return new HorizontalViewTowAdapter(
                        LayoutInflater.from(mContext)
                                .inflate(R.layout.item_horizontal_list_tow, parent, false));

            case VERTICAL_VIEW:
                return new VerticalViewHolderAdapter(
                        LayoutInflater.from(mContext)
                                .inflate(R.layout.item_layout_view, parent, false));

            case TITLE:
                return new TitleViewHolderAdapter(LayoutInflater.from(mContext)
                        .inflate(R.layout.item_title_view, parent, false));

            case BANNER:
                return new BannerAdapter(LayoutInflater.from(mContext)
                        .inflate(R.layout.view_banner, parent, false));

            case REPEAT_BANNER:
                return new RepeatBannerAdapter(LayoutInflater.from(mContext)
                        .inflate(R.layout.repeat_banner, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HorizontalViewOneAdapter) {
            //String type = itemList.get(position).getType();
            ((HorizontalViewOneAdapter) holder).setData(mItemList, mContext, position);
        }

        if (holder instanceof VerticalViewHolderAdapter) {
            try {
                ((VerticalViewHolderAdapter) holder).setData(mItemList, mContext, position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (holder instanceof HorizontalViewTowAdapter) {
            ((HorizontalViewTowAdapter) holder).setData(mItemList, mContext, position);
        }

        if (holder instanceof TitleViewHolderAdapter) {
            ((TitleViewHolderAdapter) holder).setData(mItemList, mContext, position);
        }

        if (holder instanceof BannerAdapter) {
            if (position==0){
                ((BannerAdapter) holder).setData(mItemList, mContext, position);
            }
        }
    }


    @Override
    public int getItemCount() {
        return mItemList != null ? mItemList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        String type = mItemList.get(position).getType();
        String tag = mItemList.get(position).getTag();
        //Log.e("TAG", "type == " + type + " tag == " + tag + " position == " + position);

//        if (position == 0) {
//            return TYPE_HEADER;
//        }

//        if (position == itemList.size() + 1){
//            return TYPE_FOOTER;
//        }

        //竖向RecyclerView
        if ("video".equals(type) && "1".equals(tag)) {
            return VERTICAL_VIEW;
        }

        //轮播图(添加头的方式添加)
        if (position==0&&"video".equals(type) && "0".equals(tag)) {
            return BANNER;
        }else if (position!=0&&"video".equals(type) && "0".equals(tag)){
            //这里是重复的轮播图
            return REPEAT_BANNER;
        }

        //单个item
        if ("textFooter".equals(type) || "textHeader".equals(type)) {
            return TITLE;
        }

        //横向One
        if ("videoCollectionWithCover".equals(type)) {
            return HORIZONTAL_VIEW_ONE;
        }

        //横向Tow
        if ("videoCollectionOfFollow".equals(type)) {
            return HORIZONTAL_VIEW_TOW;
        }

        //广告
//        if (type.contains("banner")) {
//            return VERTICAL_VIEW;
//        }

        return VERTICAL_VIEW;
    }
}
