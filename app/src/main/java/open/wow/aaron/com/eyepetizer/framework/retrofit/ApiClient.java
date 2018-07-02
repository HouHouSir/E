package open.wow.aaron.com.eyepetizer.framework.retrofit;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import open.wow.aaron.com.eyepetizer.OpenEyesApplication;
import open.wow.aaron.com.eyepetizer.framework.utils.NetUtils;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2017/7/21
 * 功能描述: Retrofit设置
 */

public class ApiClient {
    private static final String TAG = ApiClient.class.getSimpleName();

    private Retrofit mRetrofit;
    private static final int NO_NET_MAX = 60 * 60 * 12; // 离线时缓存保存4xx,单位:秒
    private static final int NET_MAX = 60 * 2; //在线缓存在xx内可读取 单位:秒

    private ApiClient() {

        //应用程序拦截器
        Interceptor mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.e(TAG, "拦截 网络 缓存");
                Request request = chain.request();
                if (!NetUtils.isConnected(OpenEyesApplication.getObjectContext())) {//判断网络状态 无网络时
                    Log.e(TAG, "无网~~ 缓存");
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + NO_NET_MAX)
                            .removeHeader("Pragma")
                            .build();
                } else {//有网状态
                    Log.e(TAG, "有网~~ 缓存");
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                            .header("Cache-Control", "public, max-age=" + NET_MAX)//添加缓存请求头
                            .removeHeader("Pragma")
                            .build();
                }

                return chain.proceed(request);
            }
        };


        //简单方法
//        CookieManager cookieManager = new CookieManager();
//        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        //复杂方法
//        CookieHandler cookieHandler = new CookieManager(
//                new PersistentCookieStore(OpenEyesApplication.getObjectContext()),
//                CookiePolicy.ACCEPT_ALL);

        OkHttpClient mClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .cache(new Cache(new File(OpenEyesApplication.getObjectContext().getCacheDir() + "http")
                        , 1024 * 1024 * 50))
                .connectTimeout(20, TimeUnit.SECONDS)//20秒超时
                .retryOnConnectionFailure(true)//错误重连
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                //.cookieJar(new JavaNetCookieJar(cookieManager))//简单方法
                //.cookieJar(new JavaNetCookieJar(cookieHandler))//复杂方法
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ConstantURLs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mClient)
                .build();
    }

    private static ApiClient mApiClient;

    //单例设计模式
    private static ApiClient getInstance() {
        if (mApiClient == null) {
            synchronized (ApiClient.class) {
                if (mApiClient == null) {
                    mApiClient = new ApiClient();
                }
            }
        }
        return mApiClient;
    }

    /**
     * 外部调用
     *
     * @return
     */
    public static ApiStores createRetrofit() {
//        ApiStores apiStores = getInstance().mRetrofit.create(ApiStores.class);
        return getInstance().mRetrofit.create(ApiStores.class);
    }

}
