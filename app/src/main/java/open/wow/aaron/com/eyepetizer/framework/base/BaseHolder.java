package open.wow.aaron.com.eyepetizer.framework.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/29
 * 功能描述:
 */
public class BaseHolder<T, V> extends RecyclerView.ViewHolder {

    public BaseHolder(int viewId, ViewGroup parent, int viewType) {
        super(((LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE)).inflate(viewId, parent,false));
    }
    public void refreshData(T data, V position, Context context) {

    }
    public void refreshData(T data, V position) {

    }
    public void refreshData(T data, Context context) {

    }
}
