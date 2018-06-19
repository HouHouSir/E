package open.wow.aaron.com.eyepetizer.detail.found.model;

import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.framework.retrofit.ApiClient;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/19
 * 功能描述: 发现详情M层
 */

public class FoundDetailM implements  IFoundDetailM{
    private Subscription subscribe;

    @Override
    public void getFoundDataFromNet(final CallBack callBack, String id) {
         subscribe = ApiClient.createRetrofit().getFoundDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DelicacyChoiceBean>() {
                    @Override
                    public void onCompleted() {
                        callBack.onSuccess(null);
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
