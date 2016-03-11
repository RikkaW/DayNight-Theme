package moe.xing.daynightmode;

import android.content.Intent;
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

        overridePendingTransition(R.anim.activity_open_exit,
                R.anim.activity_close_exit);
    }

    public boolean shouldOverrideBackTransition() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // If night mode changed start a new activity
        if (mCurrentNightMode != AppCompatDelegate.getDefaultNightMode()) {
            fakeRecreate();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIntent = getIntent();
        mCurrentNightMode = AppCompatDelegate.getDefaultNightMode();
    }
}
