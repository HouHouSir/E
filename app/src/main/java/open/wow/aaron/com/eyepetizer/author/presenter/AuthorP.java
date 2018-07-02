package open.wow.aaron.com.eyepetizer.author.presenter;

import open.wow.aaron.com.eyepetizer.author.model.AuthorM;
import open.wow.aaron.com.eyepetizer.author.model.IAuthorM;
import open.wow.aaron.com.eyepetizer.author.view.IAuthorV;
import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/29
 * 功能描述:
 */
public class AuthorP {
    private AuthorM mAuthorM;
    private IAuthorV mIAuthorV;

    public AuthorP(IAuthorV IAuthorV) {
        mIAuthorV = IAuthorV;
        mAuthorM = new AuthorM();
    }

    /**
     * 获取数据
     */
    public void getDataFromNet() {
        mAuthorM.getDelicacyDataFromNet(new IAuthorM.CallBack<DelicacyChoiceBean, String>() {
            @Override
            public void onSuccess(DelicacyChoiceBean delicacyChoiceBean) {
                mIAuthorV.disposeDataFromNet(delicacyChoiceBean);
            }

            @Override
            public void onFail(String s) {
                mIAuthorV.disposeDataFromNetError(s);
            }
        });
    }

    /**
     * 下拉刷新
     */
    public void getDataFromNetRefresh() {

    }

    /**
     * 加载更多
     *
     * @param nextUrl 下一页的Url
     */
    public void getDataFromNetLoadMore(String nextUrl) {
        mAuthorM.getDataFromNetLoadMore(new IAuthorM.CallBack<DelicacyChoiceBean, String>() {
            @Override
            public void onSuccess(DelicacyChoiceBean delicacyChoiceBean) {
                mIAuthorV.loadMore(delicacyChoiceBean);
            }

            @Override
            public void onFail(String s) {

            }
        }, nextUrl);
    }

    /**
     * 取消网络请求
     */
    public void stopNet(){
        mAuthorM.stopNet();
    }
}
