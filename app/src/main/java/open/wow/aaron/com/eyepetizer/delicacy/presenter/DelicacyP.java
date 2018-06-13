package open.wow.aaron.com.eyepetizer.delicacy.presenter;

import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.delicacy.model.DelicacyM;
import open.wow.aaron.com.eyepetizer.delicacy.model.IDelicacyM;
import open.wow.aaron.com.eyepetizer.delicacy.view.IDelicacyV;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/5/9
 * 功能描述:
 */

public class DelicacyP {
    private static final String TAG = DelicacyP.class.getSimpleName();
    private DelicacyM mDao;
    private IDelicacyV mView;

    public DelicacyP(IDelicacyV view) {
        this.mView = view;
        mDao = new DelicacyM();
    }

    /**
     * 获取数据
     */
    public void getDataFromNet() {
        mDao.getDelicacyDataFromNet(new IDelicacyM.CallBack() {
            @Override
            public void onSuccess(DelicacyChoiceBean delicacyChoiceBean) {
//                String nextPageUrl = delicacyChoiceBean.getNextPageUrl();
//                Log.e("TAG", "nextPageUrl: " + nextPageUrl);
                mView.disposeDataFromNet(delicacyChoiceBean);
            }
        });
    }
    public void getDataFromNetRefresh() {
        mDao.getDelicacyDataFromNet(new IDelicacyM.CallBack() {
            @Override
            public void onSuccess(DelicacyChoiceBean delicacyChoiceBean) {
//                String nextPageUrl = delicacyChoiceBean.getNextPageUrl();
//                Log.e("TAG", "nextPageUrl: " + nextPageUrl);
                mView.refresh(delicacyChoiceBean);
            }
        });
    }
    public void getDataFromNetLoadMore(String date,String num,String pager) {
        //Log.e(TAG," date = " + date+" num= " + num +" pager= " + pager);
        mDao.getDataFromNetLoadMore(new IDelicacyM.CallBack() {
            @Override
            public void onSuccess(DelicacyChoiceBean delicacyChoiceBean) {
                mView.loadMore(delicacyChoiceBean);
            }
        }, date,num,pager);
    }

    public void stopNet(){
        mDao.stopNet();
    }
}
