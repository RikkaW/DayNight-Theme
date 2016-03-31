package moe.xing.daynightmode;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

/**
 * On 6.0+, we use a bad way (change system setting) to let night mode work, The best way is wait Google for fix
 */
public class BaseDayNightModeActivity extends AppCompatActivity {

    private int mCurrentNightMode;
    private Intent mIntent;

    public void setNightMode(int mode) {
        if (mode == DayNightMode.MODE_NIGHT_AUTO_FOLLOW_SYSTEM_IF_SYSTEM_AUTO &&
                ((UiModeManager) getSystemService(Context.UI_MODE_SERVICE)).getNightMode() == UiModeManager.MODE_NIGHT_AUTO) {
            mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        }

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
        super.onCreate(savedInstanceState);

        mIntent = getIntent();
        mCurrentNightMode = AppCompatDelegate.getDefaultNightMode();
    }
}
