package net.mindwalkers.myapplication;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MouseButton {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int MIDDLE = 3;

    private Context parent;
    private Button buttonObject;
    private int buttonType;
    private GestureDetector gestureDetector;
    private TextView debugTextView;
    private boolean isLongPress;

    RestClient client;

    public MouseButton(Context parent, Button buttonObject, int buttonType, TextView debugTextView, RestClient client) {
        this.parent = parent;
        this.buttonObject = buttonObject;
        this.buttonType = buttonType;
        isLongPress = false;
        gestureDetector = new GestureDetector(parent, new MouseButtonGestureListener());
        buttonObject.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        this.debugTextView = debugTextView;
        this.client = client;
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
            isLongPress = true;
            buttonObject.setPressed(true);
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
            if (isLongPress) {
                isLongPress = false;
                buttonObject.setPressed(false);
            }
            else {
                client.getApi().mousePosition().enqueue(new Callback<MousePosition>() {
                    @Override
                    public void onResponse(Call<MousePosition> call, Response<MousePosition> response) {
                        if (response.body() != null) {
                            debugTextView.setText("Down X: " + response.body().getX() + " Y: " + response.body().getY());
                        }
                    }

                    @Override
                    public void onFailure(Call<MousePosition> call, Throwable t) {

                    }
                });

            }
            return super.onDown(e);
        }
    }
}
