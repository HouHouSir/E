package open.wow.aaron.com.eyepetizer.detail.found.presenter;

import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.detail.found.model.FoundDetailM;
import open.wow.aaron.com.eyepetizer.detail.found.model.IFoundDetailM;
import open.wow.aaron.com.eyepetizer.detail.found.view.IFoundDetailV;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/19
 * 功能描述:发现详情页 P层
 */

public class FoundDetailP {
    private FoundDetailM mFoundDetailM;
    private IFoundDetailV mIFoundDetailV;

    public FoundDetailP(IFoundDetailV mIFoundDetailV) {
        this.mIFoundDetailV = mIFoundDetailV;
        mFoundDetailM = new FoundDetailM();
    }

    /**
     * 网络请求
     */
    public void getFoundDataFromNet(String id){
        mFoundDetailM.getFoundDataFromNet(new IFoundDetailM.CallBack() {
            @Override
            public void onSuccess(DelicacyChoiceBean delicacyChoiceBean) {
                mIFoundDetailV.disposeDataFromNet(delicacyChoiceBean);
            }
        }, id);
    }

    /**
     * 取消网络请求
     */
    public void stopNet(){
        mFoundDetailM.stopNet();
    }
}
