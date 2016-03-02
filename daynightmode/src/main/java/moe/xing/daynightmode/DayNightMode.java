package moe.xing.daynightmode;

import android.app.UiModeManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by Rikka on 2016/3/2.
 */
public class DayNightMode {
    public static final int MODE_NIGHT_AUTO = AppCompatDelegate.MODE_NIGHT_AUTO;
    public static final int MODE_NIGHT_NO = AppCompatDelegate.MODE_NIGHT_NO;
    public static final int MODE_NIGHT_YES = AppCompatDelegate.MODE_NIGHT_YES;

    static public void setDefaultNightMode(ContextWrapper contextWrapper, int mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((UiModeManager) contextWrapper.getSystemService(Context.UI_MODE_SERVICE))
                    .setNightMode(mode);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    mode);
        }
    }
}
