package rikka.daynight;

import android.app.ActivityOptions;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import moe.xing.daynightmode.BaseDayNightModeActivity;
import moe.xing.daynightmode.DayNightMode;

public class MainActivity extends BaseDayNightModeActivity {
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.textView);
        resetText();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNightMode(DayNightMode.MODE_NIGHT_AUTO);
                resetText();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNightMode(DayNightMode.MODE_NIGHT_NO);
                resetText();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNightMode(DayNightMode.MODE_NIGHT_YES);
                resetText();
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNightMode(DayNightMode.MODE_NIGHT_FOLLOW_SYSTEM);
                resetText();
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.addFlags(/*Intent.FLAG_ACTIVITY_NEW_DOCUMENT | */Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.this.startActivity(intent/*, ActivityOptions.makeTaskLaunchBehind()
                            .toBundle()*/);
                } else {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.this.startActivity(intent);
                }
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this, R.style.AppTheme_Dialog_Alert)
                        .setTitle("Title")
                        .setMessage("Message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayNightMode.setSystemNightMode(MainActivity.this, DayNightMode.MODE_NIGHT_AUTO);
                resetText();
            }
        });

        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayNightMode.setSystemNightMode(MainActivity.this, DayNightMode.MODE_NIGHT_NO);
                resetText();
            }
        });

        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayNightMode.setSystemNightMode(MainActivity.this, DayNightMode.MODE_NIGHT_YES);
                resetText();
            }
        });
    }

    private void resetText() {
        UiModeManager uiManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
        mTextView.setText(String.format("System night mode (6.0+): %d\nSupport library night mode: %d\n\n%s %d\n%s %d\n%s %d\n%s %d",
                uiManager.getNightMode(),
                AppCompatDelegate.getDefaultNightMode(),
                "MODE_NIGHT_AUTO", DayNightMode.MODE_NIGHT_AUTO,
                "MODE_NIGHT_NO", DayNightMode.MODE_NIGHT_NO,
                "MODE_NIGHT_YES", DayNightMode.MODE_NIGHT_YES,
                "MODE_NIGHT_FOLLOW_SYSTEM", DayNightMode.MODE_NIGHT_FOLLOW_SYSTEM
        ));
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
