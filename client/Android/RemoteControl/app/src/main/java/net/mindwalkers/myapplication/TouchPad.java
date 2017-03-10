package net.mindwalkers.myapplication;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TouchPad {
    private Context parent;
    private GestureDetector gestureDetector;
    private TextView debugTextView;
    private ImageView touchPadObject;
    private RestClient client;
    private boolean isWheelEmulate;

    public TouchPad(Context parent, ImageView touchPadObject, TextView debugTextView1, RestClient client) {
        this.parent = parent;
        this.touchPadObject = touchPadObject;
        this.debugTextView = debugTextView1;
        gestureDetector = new GestureDetector(parent, new TouchPadGestureListener());
        gestureDetector.setIsLongpressEnabled(false);
        isWheelEmulate = false;

        this.client = client;

        touchPadObject.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        isWheelEmulate = false;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        isWheelEmulate = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isWheelEmulate = false;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        if (event.getPointerCount() < 3)
                            isWheelEmulate = false;
                        break;
                }
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private class TouchPadGestureListener extends GestureDetector.SimpleOnGestureListener {
         /*@Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            debugTextView.setText("fling touch " + velocityX + " " + velocityY);
            return super.onFling(e1, e2, velocityX, velocityY);
        }*/

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (isWheelEmulate) {
                
            }
            else {
                MouseMove mouseMoveBody = new MouseMove();
                mouseMoveBody.setSpeed(0);
                mouseMoveBody.setX(-((int) distanceX));
                mouseMoveBody.setY(-((int) distanceY));

                RestClient.getApi().mouseMove(mouseMoveBody).enqueue(new Callback<MousePosition>() {
                    @Override
                    public void onResponse(Call<MousePosition> call, Response<MousePosition> response) {

                    }

                    @Override
                    public void onFailure(Call<MousePosition> call, Throwable t) {

                    }
                });
            }

            if (distanceY > 0.0) {
                if (isWheelEmulate)
                    debugTextView.setText("wheel up " + distanceX + " " + distanceY);
                else
                    debugTextView.setText("scroll up " + distanceX + " " + distanceY);
            }
            else if (distanceY < 0.0) {
                if (isWheelEmulate)
                    debugTextView.setText("wheel down " + distanceX + " " + distanceY);
                else
                    debugTextView.setText("scroll down " + distanceX + " " + distanceY);
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
