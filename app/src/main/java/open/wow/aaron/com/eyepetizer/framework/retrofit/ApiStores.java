package open.wow.aaron.com.eyepetizer.framework.retrofit;


import java.util.ArrayList;

import open.wow.aaron.com.eyepetizer.delicacy.model.bean.DelicacyChoiceBean;
import open.wow.aaron.com.eyepetizer.found.model.bean.FoundBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2017/7/21
 * 功能描述: 所有API请求接口
 */

public interface ApiStores {
    //http://baobab.kaiyanapp.com/api/v4/tabs/selected?date=1527555600000&num=2&page=2

    /**
     * 精选
     * @return
     */
    @POST("tabs/selected")
    Observable<DelicacyChoiceBean> getDelicacyChoice();


    //http://baobab.kaiyanapp.com/api/v4/tabs/selected?date=1510880400000&num=1&page=1
//    @GET()
//    Observable<DelicacyChoiceBean> getMore(@Query("num") int num, @Query("page") int page);


    //http://baobab.kaiyanapp.com/api/v4/tabs/selected?date=1527555600000&num=2&page=2

    /**
     * 精选加载更多
     * @param date
     * @param num
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("tabs/selected?")
    Observable<DelicacyChoiceBean> getDelicacyChoiceLoadMore(@Field("date") String date,
                                                             @Field("num") String num,
                                                             @Field("page") String page);

    /**
     * 发现
     * http://baobab.kaiyanapp.com/api/v4/categories
     */
    @GET("categories")
    Observable<ArrayList<FoundBean>> getFound();
}
