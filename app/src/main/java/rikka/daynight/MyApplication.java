package rikka.daynight;

import android.app.Application;
import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatDelegate;

import moe.xing.daynightmode.DayNightMode;

/**
 * Created by Rikka on 2016/2/27.
 */


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DayNightMode.setDefaultNightMode(this, DayNightMode.MODE_NIGHT_FOLLOW_SYSTEM);
        //DayNightMode.setSystemNightMode(this, DayNightMode.MODE_NIGHT_NO);
    }
}
