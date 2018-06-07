package open.wow.aaron.com.eyepetizer.framework.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2017/7/21
 * 功能描述: 网络状态工具类
 */

public class NetUtils {
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 检查网络的另一种方法
     *
     * @param context 上下文对象
     * @return
     */
    public static boolean isConnected(Context context) {
        boolean isWIFI = isWIFIConnectivity(context);
        boolean isMOBILE = isMOBILEConnectivity(context);

        // 如果没有网络
        if (!isMOBILE && !isWIFI) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是wifi连接
     *
     * @param context 上下文对象
     * @return wifi连接返回true，wifi没有连接返回false
     */
    public static boolean isWIFIConnectivity(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取WiFi的连接信息。
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断MOBILE连接
     *
     * @param context 上下文对象
     * @return MOBILE连接返回true，MOBILE没有连接返回false
     */
    public static boolean isMOBILEConnectivity(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // MOBILE链接的描述信息
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
