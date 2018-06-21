package open.wow.aaron.com.eyepetizer;

import android.app.Application;
import android.content.Context;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2017/7/21
 * 功能描述: Application
 */
public class OpenEyesApplication extends Application {
    private static Context context;

//    public static RefWatcher getRefWatcher(Context context) {
//        OpenEyesApplication application = (OpenEyesApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

//    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        /**
         * 以下为OOM测试
         */
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        refWatcher = LeakCanary.install(this);
    }

    public static Context getObjectContext() {
        return context;
    }

}
