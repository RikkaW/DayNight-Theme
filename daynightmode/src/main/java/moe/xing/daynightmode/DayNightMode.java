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
    public static final int MODE_NIGHT_FOLLOW_SYSTEM = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
    public static final int MODE_NIGHT_AUTO_FOLLOW_SYSTEM_IF_SYSTEM_AUTO = MODE_NIGHT_YES + 1;

    static public void setDefaultNightMode(Context context, int mode) {
        if (mode == MODE_NIGHT_AUTO_FOLLOW_SYSTEM_IF_SYSTEM_AUTO) {
            if (((UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE)).getNightMode() == UiModeManager.MODE_NIGHT_AUTO) {
                mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
            } else {
                mode = AppCompatDelegate.MODE_NIGHT_AUTO;
            }
        }

        AppCompatDelegate.setDefaultNightMode(
                mode);
    }

    static public void setSystemNightMode(Context context, int mode) {
        ((UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE))
                .setNightMode(mode);
    }
}
