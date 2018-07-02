package open.wow.aaron.com.eyepetizer.author.view;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2018/6/29
 * 功能描述:
 */
public interface IAuthorV<T, S> {
    /**
     * 第一次进入网络请求返回值
     *
     * @param t
     */
    void disposeDataFromNet(T t);

    /**
     * @param error
     */
    void disposeDataFromNetError(S error);


    /**
     * 下拉刷新
     *
     * @param t
     */
    void refresh(T t);

    /**
     * 上拉加载更多
     *
     * @param t
     */
    void loadMore(T t);
}
