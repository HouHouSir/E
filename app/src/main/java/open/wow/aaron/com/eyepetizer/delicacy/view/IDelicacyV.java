package open.wow.aaron.com.eyepetizer.delicacy.view;

import open.wow.aaron.com.eyepetizer.delicacy.bean.DelicacyChoiceBean;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/5/9
 * 功能描述:
 */

public interface IDelicacyV {
    /**
     * 显示详情界面
     */
    void showDetail();

    /**
     * 搜索
     */
    void search();

    /**
     * 精选,第一次进入网络请求返回值
     * @param delicacyChoiceBean
     */
    void disposeDataFromNet(DelicacyChoiceBean delicacyChoiceBean);

    /**
     * 下拉刷新
     * @param delicacyChoiceBean
     */
    void refresh(DelicacyChoiceBean delicacyChoiceBean);

    /**
     * 上拉加载更多
     * @param delicacyChoiceBean
     */
    void loadMore(DelicacyChoiceBean delicacyChoiceBean);
}
