package net.mindwalkers.myapplication;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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

    private float moveSpeed = 1.0f;
    private float wheelSpeed = 1.0f;
    private float mouseX;
    private float mouseY;

    private float moveX;
    private float moveY;

    private Timer mouseMoveTimer;
    private Timer wheelTimer;

    private SendMouseMove sendMouseMoveTask;


    private static final int mouaeMoveDelay = 200;
    private static final int mouseMovePeriod = 100;


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

                        mouseX = event.getX();
                        mouseY = event.getY();

                        moveX = 0.0f;
                        moveY = 0.0f;

                        RestClient.getApi().mousePosition().enqueue(new Callback<MousePosition>() {
                            @Override
                            public void onResponse(Call<MousePosition> call, Response<MousePosition> response) {
                                MousePosition pos = response.body();
                                mouseX = pos.getX();
                                mouseY = pos.getY();
                            }

                            @Override
                            public void onFailure(Call<MousePosition> call, Throwable t) {

                            }
                        });
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        isWheelEmulate = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isWheelEmulate = false;
                        mouseMoveTimer.cancel();
                        sendMouseMoveTask = null;
                        mouseMoveTimer = null;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        if (event.getPointerCount() < 3)
                            isWheelEmulate = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getX();
                        float y = event.getY();

                        float moveDistanceX = x - mouseX;
                        float moveDistanceY = y - mouseY;

                        if (isWheelEmulate) {

                        }
                        else {
                            if (mouseMoveTimer == null)
                            {
                                mouseMoveTimer = new Timer();
                                sendMouseMoveTask = new SendMouseMove();
                                mouseMoveTimer.schedule(sendMouseMoveTask, mouaeMoveDelay, mouseMovePeriod);
                            }

                            moveX += moveDistanceX;
                            moveY += moveDistanceY;
                            mouseX = x;
                            mouseY = y;
                        }

                        if (moveDistanceY < 0.0) {
                            if (isWheelEmulate)
                                debugTextView.setText("wheel up " + mouseX + " " + mouseY);
                            else
                                debugTextView.setText("scroll up " + mouseX + " " + mouseY);
                        }
                        else if (moveDistanceY > 0.0) {
                            if (isWheelEmulate)
                                debugTextView.setText("wheel down " + mouseX + " " + mouseY);
                            else
                                debugTextView.setText("scroll down " + mouseX + " " + mouseY);
                        }
                        break;
                        //return true;
                    default:
                        break;
                }
                //gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private class SendMouseMove extends TimerTask {

        @Override
        public void run() {
            float absMoveX = Math.abs(moveSpeed*moveX);
            float absMoveY = Math.abs(moveSpeed*moveY);

            if (absMoveX > 0 && absMoveX < 1 && absMoveY > 0 && absMoveY < 1)
            {
                return;
            }
            else if (absMoveX == 0 && absMoveY == 0)
            {
                return;
            }

            int distanceX = (int)(moveSpeed*moveX);
            int distanceY = (int)(moveSpeed*moveY);

            MouseMove mouseMoveBody = new MouseMove();
            mouseMoveBody.setSpeed(0);
            mouseMoveBody.setX(distanceX);
            mouseMoveBody.setY(distanceY);

            RestClient.getApi().mouseMove(mouseMoveBody).enqueue(new Callback<MousePosition>() {
                @Override
                public void onResponse(Call<MousePosition> call, Response<MousePosition> response) {

                }

                @Override
                public void onFailure(Call<MousePosition> call, Throwable t) {

                }
            });
        }
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
