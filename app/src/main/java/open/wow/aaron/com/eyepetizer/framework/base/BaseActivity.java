package open.wow.aaron.com.eyepetizer.framework.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 作者：哇牛Aaron
 * 时间: 2018/6/4
 * 功能描述:Activity基类
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        immerse();
        setLightStatusBarIcon(true);


        //如何识别凹形屏：返回 true为凹形屏 ，可识别OPPO的手机是否为凹形屏。
        boolean isOppo = getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
        Log.e(TAG, "isOppo = " + isOppo);
    }

    /**
     * 沉浸式状态栏
     */
    private void immerse(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Log.e(TAG, ">=5.0版本");
            Window window = getWindow();

            View decorView = window.getDecorView();

            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Log.e(TAG, ">=4.4版本");
            Window window = getWindow();

            WindowManager.LayoutParams attributes = window.getAttributes();

            attributes.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

            window.setAttributes(attributes);
        }

    }

//    private void setLightStatusBarIcon(){
//        //对Android版本是6.0及以后的OPPO机型
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//        //对Android5.1版本并且是ColorOS3.0的OPPO机型
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1){
//            final int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;
//
//            Window window = getWindow();
//
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
//            window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT);
//
//        }
//    }

    /**
     * 为方便开发者对于OPPO机型的反色适配，提供了用于设置状态栏显示效果的接口方法，开发者可以根据应用需要动态设置状态栏图标为白色或是黑色。
     *接口传入值ture时状态栏图标为黑色，接口转入值为false状态栏图标为白色
     * @param lightMode
     */
    public void setLightStatusBarIcon(boolean lightMode) {
        final int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;

        Window window  = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int vis = window.getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (lightMode) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (lightMode) {
                vis |= SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
            } else {
                vis &= ~SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
            }
        }
        window.getDecorView().setSystemUiVisibility(vis);
    }
}
