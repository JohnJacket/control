package net.mindwalkers.control;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class Keyboard {
    private Context     context;
    private TextView    debugTextView;
    private View        inputView;
    public boolean     isKeyboardHidden = true;

    public Keyboard(Context context, TextView debugTextView, View view) {
        this.context = context;
        this.debugTextView = debugTextView;
        this.inputView = view;

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
        InputMethodManager im = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(inputView, InputMethodManager.SHOW_IMPLICIT);
        isKeyboardHidden = false;
    }
}
