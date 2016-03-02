package moe.xing.daynightmode;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

/**
 * On 6.0+, we use a bad way (change system setting) to let night mode work, The best way is wait Google for fix
 */
public class BaseDayNightModeActivity extends AppCompatActivity {
    private int mCurrentNightMode;
    private Intent mIntent;

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return super.getDelegate();
    }

    public void setNightMode(int mode) {
        if (mode == mCurrentNightMode) {
            return;
        }

        mCurrentNightMode = mode;

        UiModeManager uiManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);

        // For Marshmallow use system's night mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (mode) {
                // Bad way, change system setting may affect other app
                case DayNightMode.MODE_NIGHT_AUTO:
                    uiManager.setNightMode(UiModeManager.MODE_NIGHT_AUTO);
                    break;
                case DayNightMode.MODE_NIGHT_NO:
                    uiManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
                    break;
                case DayNightMode.MODE_NIGHT_YES:
                    uiManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
                    break;
            }
        } else {
            AppCompatDelegate.setDefaultNightMode(mode);

            fakeRecreate();

            // set and recreate activity
            /*getDelegate().setLocalNightMode(mode);
            recreate();*/
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

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // If night mode changed start a new activity
            if (mCurrentNightMode != AppCompatDelegate.getDefaultNightMode()) {
                fakeRecreate();
            }
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
            // just save support night mode value
            mCurrentNightMode = AppCompatDelegate.getDefaultNightMode();
            super.onCreate(savedInstanceState);
        } else {
            // save system's night mode value for Marshmallow
            mCurrentNightMode = ((UiModeManager) getSystemService(Context.UI_MODE_SERVICE)).getNightMode();

            // right not system have changed conf.uiMode, set support night mode to avoid some UI still use light theme
            Configuration conf = getResources().getConfiguration();
            int uiMode = conf.uiMode;
            AppCompatDelegate.setDefaultNightMode((conf.uiMode & Configuration.UI_MODE_NIGHT_YES) > 0 ?
                    AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            super.onCreate(savedInstanceState);
            conf.uiMode = uiMode;
            getResources().updateConfiguration(conf, null);
        }
    }
}
