package net.mindwalkers.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MouseButton {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int MIDDLE = 3;

    private Context context;
    private Button buttonObject;
    private int buttonType;
    private GestureDetector gestureDetector;
    private static final String TAG = "MouseButton";
    private TextView debugTextView;

    public MouseButton(Context context, Button buttonObject, int buttonType, TextView debugTextView) {
        this.context = context;
        this.buttonObject = buttonObject;
        this.buttonType = buttonType;
        gestureDetector = new GestureDetector(context, new MouseButtonGestureListener());
        buttonObject.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });
        this.debugTextView = debugTextView;
    }
    private class MouseButtonGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            debugTextView.setText("double tap");
            return super.onDoubleTapEvent(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            debugTextView.setText("Long press");
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            debugTextView.setText("Single Up");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            debugTextView.setText("Fling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            debugTextView.setText("Down");
            return super.onDown(e);
        }
    }
}
