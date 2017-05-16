package net.mindwalkers.control;

import android.content.Context;
import android.hardware.camera2.CaptureResult;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import net.mindwalkers.control.keyboard_control.KeyboardWrite;
import net.mindwalkers.control.mouse_control.MousePosition;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Keyboard {
    private Context     context;
    private TextView    debugTextView;
    private View        inputView;
    private EditText    keyboardEditText;
    public boolean     isKeyboardHidden = true;
    private String TAG = "Keyboard";

    public static final String CLICK = "click";
    public static final String DOWN = "down";
    public static final String UP = "up";

    public Keyboard(Context context, TextView debugTextView1, View view, EditText editText) {
        this.context = context;
        this.debugTextView = debugTextView1;
        this.inputView = view;
        this.keyboardEditText = editText;
        keyboardEditText.addTextChangedListener(new OnChangeTextListener(this, keyboardEditText));
        keyboardEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return false;
                }
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    RestClient.getApi().keyboardKeyAction(Dictonary.dictonary.get(KeyEvent.KEYCODE_DEL), DOWN).enqueue(new Callback<MousePosition>() {
                        @Override
                        public void onResponse(Call<MousePosition> call, Response<MousePosition> response) {

                        }

                        @Override
                        public void onFailure(Call<MousePosition> call, Throwable t) {

                        }
                    });
                    return false;
                }

                debugTextView.setText(KeyEvent.keyCodeToString(keyCode));
                return false;
            }
        });
    }

    public  void toggleKeyboard() {
        if (isKeyboardHidden) {
            show();
        }
        else {
            hide();
        }
    }

    public void hide() {
        debugTextView.setText("Hide keyboard");
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(inputView.getWindowToken(), 0);
        isKeyboardHidden = true;
    }

    public void show() {
        debugTextView.setText("Show keyboard");
        if (inputView == null)
            Log.d(TAG, "inputView == null");
        InputMethodManager im = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(inputView, InputMethodManager.SHOW_IMPLICIT);
        isKeyboardHidden = false;
    }

    public class OnChangeTextListener implements TextWatcher {
        private Keyboard keyboard;
        private EditText editText;
        private String TAG = "OnChangeTextListener";

        public OnChangeTextListener(Keyboard keyboard, EditText editText) {
            super();
            this.keyboard = keyboard;
            this.editText = editText;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (editText.getText().toString() != "") {
                Log.d(TAG, "afterTextChanged " + editText.getText());
                KeyboardWrite keyboardWriteBody = new KeyboardWrite();
                keyboardWriteBody.setText(editText.getText().toString());
                RestClient.getApi().keyboardWrite(keyboardWriteBody).enqueue(new Callback<MousePosition>() {
                    @Override
                    public void onResponse(Call<MousePosition> call, Response<MousePosition> response) {

                    }

                    @Override
                    public void onFailure(Call<MousePosition> call, Throwable t) {

                    }
                });
                editText.getText().clear();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //Log.d(TAG, "beforeTextChanged " + editText.getText());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Log.d(TAG, "onTextChanged " + editText.getText());
        }
    }
}
