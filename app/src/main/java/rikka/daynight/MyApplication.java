package rikka.daynight;

import android.app.Application;
import android.app.UiModeManager;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by Rikka on 2016/2/27.
 */


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((UiModeManager) getSystemService(Context.UI_MODE_SERVICE)).setNightMode(UiModeManager.MODE_NIGHT_AUTO);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_AUTO);
        }*/
    }
}
