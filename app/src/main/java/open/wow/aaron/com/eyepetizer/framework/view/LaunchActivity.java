package open.wow.aaron.com.eyepetizer.framework.view;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import open.wow.aaron.com.eyepetizer.R;
import open.wow.aaron.com.eyepetizer.framework.base.BaseActivity;
import rx.Observable;
import rx.functions.Action1;

/**
 * 启动页Activity
 */
public class LaunchActivity extends BaseActivity {

    private static final String TAG = LaunchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Observable.timer(1 / 2, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                        finish();
                    }
                });

    }
}
