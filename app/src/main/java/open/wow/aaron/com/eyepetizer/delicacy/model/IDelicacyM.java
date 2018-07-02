package open.wow.aaron.com.eyepetizer.delicacy.model;

import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/5/9
 * 功能描述:
 */

public interface IDelicacyM {
    void getDelicacyDataFromNet(CallBack callBack);
//    void getDataFromNetRefresh(CallBack callBack);
    void getDataFromNetLoadMore(CallBack callBack,String date,String num,String pager);
    void stopNet();

    interface CallBack {
        void onSuccess(DelicacyChoiceBean delicacyChoiceBean);

//        void onFail();
    }
}
