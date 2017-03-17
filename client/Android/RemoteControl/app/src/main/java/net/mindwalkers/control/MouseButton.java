package net.mindwalkers.control;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    }
    private class MouseButtonGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        /*@Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            debugTextView.setText("double tap");
            return super.onDoubleTapEvent(e);
        }
        */
        @Override
        public void onLongPress(MotionEvent e) {
            isLongPress = true;
            buttonObject.setPressed(true);

            MouseDown mouseDownBody = new MouseDown();
            mouseDownBody.setButton(buttonType);

            RestClient.getApi().mouseDown(mouseDownBody).enqueue(new Callback<MousePosition>() {
                @Override
                public void onResponse(Call<MousePosition> call, Response<MousePosition> response) {
                    debugTextView.setText("Long press");
                }

                @Override
                public void onFailure(Call<MousePosition> call, Throwable t) {

                }
            });

            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            MouseDown mouseDownBody = new MouseDown();
            mouseDownBody.setButton(buttonType);

            RestClient.getApi().mouseUp(mouseDownBody).enqueue(new Callback<MousePosition>() {
                @Override
                public void onResponse(Call<MousePosition> call, Response<MousePosition> response) {
                    debugTextView.setText("Single Up");
                }

                @Override
                public void onFailure(Call<MousePosition> call, Throwable t) {

                }
            });

            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            if (isLongPress) {
                isLongPress = false;
                buttonObject.setPressed(false);
            }
            else {
                MouseDown mouseDownBody = new MouseDown();
                mouseDownBody.setButton(buttonType);

                RestClient.getApi().mouseDown(mouseDownBody).enqueue(new Callback<MousePosition>() {
                    @Override
                    public void onResponse(Call<MousePosition> call, Response<MousePosition> response) {
                        debugTextView.setText("Down");
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
