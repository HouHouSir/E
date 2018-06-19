package open.wow.aaron.com.eyepetizer.detail.found.view;

import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/19
 * 功能描述:
 */

public interface IFoundDetailV {
//    /**
//     * 显示dialog
//     */
//    void showDialog();
//
//    /**
//     * 隐藏dialog
//     */
//    void hideDialog();

    /**
     * 处理网络数据
     * @param delicacyChoiceBean
     */
    void disposeDataFromNet(DelicacyChoiceBean delicacyChoiceBean);
}
