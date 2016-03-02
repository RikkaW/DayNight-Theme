package rikka.daynight;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

/**
 * On 6.0+, we use a bad way (change system setting) to let night mode work, The best way is wait Google for fix
 */

public class BaseActivity extends AppCompatActivity {
    public static final int MODE_NIGHT_AUTO = AppCompatDelegate.MODE_NIGHT_AUTO;
    public static final int MODE_NIGHT_NO = AppCompatDelegate.MODE_NIGHT_NO;
    public static final int MODE_NIGHT_YES = AppCompatDelegate.MODE_NIGHT_YES;

    private int mThemeId = 0;
    private int mCurrentNightMode;
    private Intent mIntent;

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
                case MODE_NIGHT_AUTO:
                    uiManager.setNightMode(UiModeManager.MODE_NIGHT_AUTO);
                    break;
                case MODE_NIGHT_NO:
                    uiManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
                    break;
                case MODE_NIGHT_YES:
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

        // Use support night mode for pre Marshmallow
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // save support night mode value
            mCurrentNightMode = AppCompatDelegate.getDefaultNightMode();

            if (delegate.applyDayNight() && mThemeId != 0) {
                // If day night has been applied, we need to re-set the theme for it to fully apply
                setTheme(mThemeId);
            }
        } else {
            // save system's night mode value for Marshmallow
            mCurrentNightMode = ((UiModeManager) getSystemService(Context.UI_MODE_SERVICE)).getNightMode();
        }

        super.onCreate(savedInstanceState);
    }

    // Copy from AppCompatActivity
    @Override
    public void setTheme(@StyleRes final int resid) {
        super.setTheme(resid);
        // Keep hold of the theme id so that we can re-set it later if needed
        mThemeId = resid;
    }
}
