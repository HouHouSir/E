package open.wow.aaron.com.eyepetizer.detail.found.model;

import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/19
 * 功能描述: M层接口
 * 1.getFoundDataFromNet()获取网络数据
 * 2.stopNet()停止网络请求
 */

public interface IFoundDetailM {
    /**
     * 获取网络数据
     *
     * @param callBack
     */
    void getFoundDataFromNet(CallBack callBack, String id);

    /**
     * 停止网络请求
     */
    void stopNet();

    /**
     * 网络成功/失败回调
     */
    interface CallBack {
        void onSuccess(DelicacyChoiceBean delicacyChoiceBean);
    }
}
