package moe.xing.daynightmode;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

public class BaseDayNightModeActivity extends AppCompatActivity {

    private int mCurrentNightMode;
    private Intent mIntent;

    public void setNightMode(int mode) {
        if (mode == mCurrentNightMode) {
            return;
        }

        mCurrentNightMode = mode;

        AppCompatDelegate.setDefaultNightMode(mode);

        fakeRecreate();
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
    public void onBackPressed() {
        super.onBackPressed();

        if (shouldOverrideBackTransition()) {
            overridePendingTransition(0,
                    R.anim.activity_close_exit);
        }
    }

    public boolean shouldOverrideBackTransition() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Let system do recreate
        if (mCurrentNightMode == DayNightMode.MODE_NIGHT_FOLLOW_SYSTEM) {
            return;
        }

        // If night mode changed start a new activity
        if (mCurrentNightMode != AppCompatDelegate.getDefaultNightMode()) {
            fakeRecreate();
            return;
        }

        if (mCurrentNightMode == DayNightMode.MODE_NIGHT_AUTO) {
            if (getDelegate().applyDayNight()) {
                fakeRecreate();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mIntent = getIntent();
        mCurrentNightMode = AppCompatDelegate.getDefaultNightMode();

        // Bad way to really follow system
        if (mCurrentNightMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(isNightMode() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            super.onCreate(savedInstanceState);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        else {
            super.onCreate(savedInstanceState);
        }

    }

    public boolean isNightMode() {
        return ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_YES) > 0);
    }
}
