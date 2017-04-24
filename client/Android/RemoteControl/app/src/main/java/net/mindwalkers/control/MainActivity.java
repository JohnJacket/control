package net.mindwalkers.control;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.mindwalkers.control.mouse_control.MouseButton;

public class MainActivity extends AppCompatActivity {

    MouseButton leftMouseButton, rightMouseButton;
    TouchPad touchPad;
    TextView debugTextView;
    public RestClient client;
    private boolean isKeyboardHidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createToolbar();
        runClient();
        createView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        toggleKeyboard(false);
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
        touchPad = new TouchPad(this, (ImageView)findViewById(R.id.touchPad), debugTextView, client);
        leftMouseButton = new MouseButton(this, (Button)findViewById(R.id.leftButton), MouseButton.LEFT, debugTextView, client);
        rightMouseButton = new MouseButton(this, (Button)findViewById(R.id.rightButton), MouseButton.RIGHT, debugTextView, client);

        findViewById(R.id.touchPad).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                debugTextView.setText(KeyEvent.keyCodeToString(keyCode));
                return true;
            }
        });
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
                toggleKeyboard();
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

    public void toggleKeyboard(boolean forceToggleValue) {
        if (forceToggleValue) {
            debugTextView.setText("Show keyboard");
            InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            im.showSoftInput(touchPad.getTouchPadObject(), InputMethodManager.SHOW_IMPLICIT);
            isKeyboardHidden = false;
        }
        else {
            debugTextView.setText("Hide keyboard");
            ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(touchPad.getTouchPadObject().getWindowToken(), 0);
            isKeyboardHidden = true;
        }
    }

    public  void toggleKeyboard() {
        //isKeyboardHidden = !isKeyboardHidden;
        toggleKeyboard(isKeyboardHidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        isKeyboardHidden = false;
        super.onBackPressed();
    }
}
