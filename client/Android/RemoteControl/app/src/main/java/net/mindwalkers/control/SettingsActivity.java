package net.mindwalkers.control;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {

    private String TAG = "SettingsActivityTag";
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        setupActionBar();

        SeekBar seekBar = (SeekBar)findViewById(R.id.sensitySeekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                SharedPreferences settings = getBaseContext().getSharedPreferences(TouchPad.PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                float sensity = progress/10.0f + 1.0f;
                editor.putFloat("touchpadSensity", sensity);

                editor.commit();
            }
        });

        SharedPreferences settings = getBaseContext().getSharedPreferences(TouchPad.PREFS_NAME, MODE_PRIVATE);
        float sensity = settings.getFloat("touchpadSensity", 1.0f);
        int intSensity = (int)((sensity - 1.0f) * 10.0f);
        Log.i(TAG, " Fl: " + sensity + " int: " + intSensity);
        seekBar.setProgress(intSensity);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
