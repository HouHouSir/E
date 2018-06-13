package open.wow.aaron.com.eyepetizer.found.presenter;

import java.util.ArrayList;

import open.wow.aaron.com.eyepetizer.found.model.FoundM;
import open.wow.aaron.com.eyepetizer.found.model.IFoundM;
import open.wow.aaron.com.eyepetizer.found.model.bean.FoundBean;
import open.wow.aaron.com.eyepetizer.found.view.IFoundV;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/13
 * 功能描述:Found P层
 */

public class FoundP {
    private FoundM mFoundM;
    private IFoundV mIFoundV;

    public FoundP(IFoundV IFoundV) {
        mIFoundV = IFoundV;
        mFoundM = new FoundM();
    }

    /**
     * 网络请求
     */
    public void getFoundDataFromNet(){
        mIFoundV.showDialog();
        mFoundM.getFoundDataFromNet(new IFoundM.CallBack() {
            @Override
            public void onSuccess(ArrayList<FoundBean> arrayList) {
                mIFoundV.hideDialog();
                mIFoundV.disposeDataFromNet(arrayList);
            }
        });
    }

    public void stopNet(){
        mFoundM.stopNet();
    }
}
