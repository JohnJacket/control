package net.mindwalkers.control;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.mindwalkers.control.mouse_control.MouseButton;

public class MainActivity extends AppCompatActivity {

    MouseButton leftMouseButton, rightMouseButton;
    TouchPad touchPad;
    TextView debugTextView;
    private Keyboard keyboard;
    public RestClient client;
    private String TAG = "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createToolbar();
        runClient();
        createView();
        final View activityRootView = findViewById(R.id.activity_main);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = activityRootView.getRootView().getHeight();

                int keypadHeight = screenHeight - r.bottom;

                Log.d(TAG, "keypadHeight = " + keypadHeight);

                if(keypadHeight > screenHeight * 0.15) {
                    keyboard.isKeyboardHidden = false;
                }
                else {
                    keyboard.isKeyboardHidden = true;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO: Can you add showing keyboard here?
        //touchPad.getTouchPadObject().setFocusableInTouchMode(true);
        //touchPad.getTouchPadObject().requestFocus();
        //keyboard.toggleKeyboard();
    }

    @Override
    protected void onStop() {
        keyboard.hide();
        super.onStop();
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void runClient() {
        RestServer server = new RestServer("http://192.168.112.152:5000/", "Server");
        client = new RestClient(server);
    }

    private void createView() {
        debugTextView = (TextView)findViewById(R.id.debugTextView);
        keyboard = new Keyboard(this, (TextView)findViewById(R.id.debugTextView), findViewById(R.id.keyboardEditText), (EditText) findViewById(R.id.keyboardEditText));
        touchPad = new TouchPad(this, (ImageView)findViewById(R.id.touchPad), debugTextView, client, keyboard);
        leftMouseButton = new MouseButton(this, (Button)findViewById(R.id.leftButton), MouseButton.LEFT, debugTextView, client);
        rightMouseButton = new MouseButton(this, (Button)findViewById(R.id.rightButton), MouseButton.RIGHT, debugTextView, client);
    }

    @Override
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

        switch (id) {
            case R.id.action_settings: {
                Intent settingsPage = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsPage);
                break;
            }
            case R.id.action_keyboard: {
                keyboard.toggleKeyboard();
                break;
            }
            case R.id.action_power_panel: {
                debugTextView.setText("Show power panel");
                break;
            }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyDown()");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!keyboard.isKeyboardHidden)
                keyboard.isKeyboardHidden = false;
            debugTextView.setText("Hide keyboard");
        }
        return super.onKeyDown(keyCode, event);
    }
    */

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed()");
        super.onBackPressed();
    }
}
