package open.wow.aaron.com.eyepetizer.author.model;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/29
 * 功能描述:
 */
public interface IAuthorM {
    /**
     * 获取作者url
     *
     * @param callBack
     */
    void getDelicacyDataFromNet(CallBack callBack);

    /**
     * 加载下一页
     *
     * @param callBack
     * @param nextUrl
     */
    void getDataFromNetLoadMore(CallBack callBack, String nextUrl);

    /**
     * 取消网络请求
     */
    void stopNet();

    /**
     * 请求数据回调
     */
    interface CallBack<T, S> {
        void onSuccess(T t);

        void onFail(S s);
    }
}
