package rikka.daynight;

import android.app.ActivityOptions;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import moe.xing.daynightmode.BaseDayNightModeActivity;
import moe.xing.daynightmode.DayNightMode;

public class MainActivity extends BaseDayNightModeActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.textView);

        UiModeManager uiManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
        textView.setText(String.format("System night mode (only take effect on 6.0+): %d\n\n%s %d\n%s %d\n%s %d",
                uiManager.getNightMode(),
                "MODE_NIGHT_AUTO", Configuration.UI_MODE_NIGHT_UNDEFINED >> 4,
                "MODE_NIGHT_NO", Configuration.UI_MODE_NIGHT_NO >> 4,
                "MODE_NIGHT_YES", Configuration.UI_MODE_NIGHT_YES >> 4
        ));

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setVisibility(View.GONE);
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNightMode(DayNightMode.MODE_NIGHT_AUTO);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNightMode(DayNightMode.MODE_NIGHT_NO);
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNightMode(DayNightMode.MODE_NIGHT_YES);
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.this.startActivity(intent, ActivityOptions.makeTaskLaunchBehind()
                            .toBundle());
                } else {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.this.startActivity(intent);
                }
            }
        });
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
