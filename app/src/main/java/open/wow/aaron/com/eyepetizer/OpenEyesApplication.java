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

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }

    public static Context getObjectContext() {
        return context;
    }

}
