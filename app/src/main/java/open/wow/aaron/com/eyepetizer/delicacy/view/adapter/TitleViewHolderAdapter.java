package open.wow.aaron.com.eyepetizer.delicacy.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/5/15
 * 功能描述:
 */

public class TitleViewHolderAdapter extends RecyclerView.ViewHolder{
    private TextView tv_title_view_name;
    public TitleViewHolderAdapter(View itemView) {
        super(itemView);
        tv_title_view_name = (TextView) itemView.findViewById(R.id.tv_title_view_name);
    }

    public void setData(List<DelicacyChoiceBean.ItemListBean> itemList, Context context, int position) {
        tv_title_view_name.setText(itemList.get(position).getData().getText());
    }
}
