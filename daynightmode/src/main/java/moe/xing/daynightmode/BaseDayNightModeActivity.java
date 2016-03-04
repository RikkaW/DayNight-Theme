package moe.xing.daynightmode;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import java.util.Calendar;

/**
 * On 6.0+, we use a bad way (change system setting) to let night mode work, The best way is wait Google for fix
 */
public class BaseDayNightModeActivity extends AppCompatActivity {
    private static final String TAG = "DayNightModeActivity";

    private int mCurrentNightMode;
    private int mSystemNight = -1;
    private Intent mIntent;
    private boolean mCheckResourcesConf = true;

    private boolean isNight() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return hour < 7 || hour >= 21;
    }

    private void checkResourcesConf(Resources res) {
        Configuration conf = res.getConfiguration();
        int uiMode = conf.uiMode;
        uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;

        if (AppCompatDelegate.getDefaultNightMode() == DayNightMode.MODE_NIGHT_FOLLOW_SYSTEM) {
            if (mSystemNight == -1) {
                mSystemNight = ((conf.uiMode & Configuration.UI_MODE_NIGHT_YES) > 0) ? 1 : 0;
            }
            uiMode |= mSystemNight == 1
                    ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO;
        } else {
            uiMode |= (AppCompatDelegate.getDefaultNightMode() == DayNightMode.MODE_NIGHT_YES
                    || AppCompatDelegate.getDefaultNightMode() == DayNightMode.MODE_NIGHT_AUTO && isNight())
                    ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO;
        }

        if (uiMode != conf.uiMode) {
            Log.d(TAG, String.format("Resources Conf changed: old: %d new: %d", conf.uiMode, uiMode));

            conf.uiMode = uiMode;
            res.updateConfiguration(conf, null);
        }
    }

    @Override
    public Resources getResources() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return super.getResources();
        }

        if (!mCheckResourcesConf) {
            return super.getResources();
        }

        Resources res = super.getResources();
        if (res == null)
            return null;

        checkResourcesConf(res);
        return res;
    }

    public void setNightMode(int mode) {
        if (mode == mCurrentNightMode) {
            return;
        }

        mCurrentNightMode = mode;

        AppCompatDelegate.setDefaultNightMode(mode);

        if (mode != DayNightMode.MODE_NIGHT_FOLLOW_SYSTEM) {
            fakeRecreate();
        }
    }

    protected Intent getNightModeChangedRestartActivityIntent() {
        if (mIntent.getAction() != null && mIntent.getAction().equals(Intent.ACTION_MAIN)) {
            return new Intent(this, this.getClass());
        }
        return mIntent;
    }

    protected int getNightModeChangedEnterAnim() {
        return R.anim.fade_in;
    }

    protected int getNightModeChangedExitAnim() {
        return R.anim.fade_out;
    }

    private void fakeRecreate() {
        startActivity(getNightModeChangedRestartActivityIntent());
        finish();

        overridePendingTransition(getNightModeChangedEnterAnim(),
                getNightModeChangedExitAnim());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // If night mode changed start a new activity
        if (mCurrentNightMode != AppCompatDelegate.getDefaultNightMode()) {
            fakeRecreate();
        }
    }

    // Copy from AppCompatActivity
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final AppCompatDelegate delegate = getDelegate();
        delegate.installViewFactory();
        delegate.onCreate(savedInstanceState);

        mIntent = getIntent();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mCurrentNightMode = AppCompatDelegate.getDefaultNightMode();
            super.onCreate(savedInstanceState);
        } else {
            mCurrentNightMode = AppCompatDelegate.getDefaultNightMode();
            super.onCreate(savedInstanceState);

            mCheckResourcesConf = false;
        }
    }
}
