package open.wow.aaron.com.eyepetizer.found.view;

import java.util.ArrayList;

import open.wow.aaron.com.eyepetizer.found.model.bean.FoundBean;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/13
 * 功能描述:
 */

public interface IFoundV {
    /**
     * 显示dialog
     */
    void showDialog();

    /**
     * 隐藏dialog
     */
    void hideDialog();

    /**
     * 处理网络数据
     * @param arrayList
     */
    void disposeDataFromNet(ArrayList<FoundBean> arrayList);
}
