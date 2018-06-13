package open.wow.aaron.com.eyepetizer.found.model;

import java.util.ArrayList;

import open.wow.aaron.com.eyepetizer.found.model.bean.FoundBean;
import open.wow.aaron.com.eyepetizer.framework.retrofit.ApiClient;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/13
 * 功能描述:
 */

public class FoundM implements IFoundM {
    private static final String TAG = FoundM.class.getSimpleName();
    private Subscription subscribe;

    @Override
    public void getFoundDataFromNet(final CallBack callBack) {
        subscribe = ApiClient.createRetrofit().getFound()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<FoundBean>>() {
                    @Override
                    public void onCompleted() {
                        callBack.onSuccess(null);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<FoundBean> arrayList) {
//                        for (int i= 0;i< arrayList.size();i++){
//                            FoundBean foundBean = arrayList.get(i);
//                            Log.e(TAG,"foundBean= " + foundBean.toString());
//                        }


                        callBack.onSuccess(arrayList);
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
