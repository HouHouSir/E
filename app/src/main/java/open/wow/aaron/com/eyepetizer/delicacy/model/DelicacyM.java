package open.wow.aaron.com.eyepetizer.delicacy.model;

import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.framework.retrofit.ApiClient;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/5/9
 * 功能描述:
 */

public class DelicacyM implements IDelicacyM {
    private Subscription subscribe;


    @Override
    public void getDelicacyDataFromNet(final CallBack callBack) {
        subscribe = ApiClient.createRetrofit()
                .getDelicacyChoice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DelicacyChoiceBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(DelicacyChoiceBean delicacyChoiceBean) {
//                        String nextPageUrl = delicacyChoiceBean.getNextPageUrl();
//                        Log.e("TAG","nextPageUrl: " + nextPageUrl);
                            callBack.onSuccess(delicacyChoiceBean);
                    }
                });
    }

//    @Override
//    public void getDataFromNetRefresh(final CallBack callBack) {
//        subscribe = ApiClient.createRetrofit()
//                .getDelicacyChoice()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<DelicacyChoiceBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(DelicacyChoiceBean delicacyChoiceBean) {
////                        String nextPageUrl = delicacyChoiceBean.getNextPageUrl();
////                        Log.e("TAG","nextPageUrl: " + nextPageUrl);
//                        callBack.onSuccess(delicacyChoiceBean);
//                    }
//                });
//    }

    @Override
    public void getDataFromNetLoadMore(final CallBack callBack,String date,String num,String pager) {
        subscribe = ApiClient.createRetrofit()
                .getDelicacyChoiceLoadMore(date,num,pager)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DelicacyChoiceBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(DelicacyChoiceBean delicacyChoiceBean) {
                        callBack.onSuccess(delicacyChoiceBean);
                    }
                });
    }

    @Override
    public void stopNet() {
        if (subscribe != null && !subscribe.isUnsubscribed()) {//isUnsubscribed 是否取消订阅
            subscribe.unsubscribe();//取消网络请求
        }
    }

}
