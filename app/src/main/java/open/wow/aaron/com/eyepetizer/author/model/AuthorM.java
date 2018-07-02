package open.wow.aaron.com.eyepetizer.author.model;

import android.text.TextUtils;

import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.framework.retrofit.ApiClient;
import open.wow.aaron.com.eyepetizer.framework.retrofit.ApiStores;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/29
 * 功能描述:
 */
public class AuthorM implements IAuthorM {
    private Subscription subscribe;

    @Override
    public void getDelicacyDataFromNet(final CallBack callBack) {
        fromNet(callBack, "http://baobab.kaiyanapp.com/api/v4/pgcs/all");
//        fromNet(callBack, "http://baobab.kaiyanapp.com/api/v4/pgcs/all?start=0&num=10");
    }

    @Override
    public void getDataFromNetLoadMore(final CallBack callBack, String nextUrl) {
        if (TextUtils.isEmpty(nextUrl)) {
            callBack.onFail(ApiStores.FAIL_MESSAGE);
        } else {
            fromNet(callBack, nextUrl);
        }
    }

    /**
     * 获取数据
     *
     * @param callBack
     * @param nextUrl
     */
    private void fromNet(final CallBack callBack, String nextUrl) {
        subscribe = ApiClient.createRetrofit().getAuthor(nextUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<AuthorBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        callBack.onSuccess(null);
//                    }
//
//                    @Override
//                    public void onNext(AuthorBean authorBean) {
//                        callBack.onSuccess(authorBean);
//                    }
//                });
                .subscribe(new Observer<DelicacyChoiceBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callBack.onSuccess(null);
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
