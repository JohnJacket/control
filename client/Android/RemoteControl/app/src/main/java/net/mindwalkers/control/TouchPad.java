package net.mindwalkers.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.mindwalkers.control.mouse_control.MouseMove;
import net.mindwalkers.control.mouse_control.MousePosition;
import net.mindwalkers.control.mouse_control.MouseWheel;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class TouchPad {
    private Context parent;
    private GestureDetector gestureDetector;
    private TextView debugTextView;
    private ImageView touchPadObject;
    private RestClient client;
    private Keyboard keyboard;
    private boolean isWheelEmulate;

    private float moveSpeed = 1.0f;
    private float wheelSpeed = 2.0f;

    private float moveX;
    private float moveY;

    private Timer moveTimer;

    private SendTouchPadMoveTask sendMoveTask;
    private boolean ignoreFirstBuggyScrollEvent = true;

    private static final int mouseMoveDelay = 0;
    private static final int mouseMovePeriod = 30;

    public static final String PREFS_NAME = "Touchpad_sensity";
    public static final String TAG = "TOUCH_PAD";

    public TouchPad(Context parent, ImageView touchPadObject, TextView debugTextView1, RestClient client, Keyboard keybd) {
        this.parent = parent;
        this.keyboard = keybd;
        this.touchPadObject = touchPadObject;
        this.debugTextView = debugTextView1;
        gestureDetector = new GestureDetector(parent, new TouchPadGestureListener());
        gestureDetector.setIsLongpressEnabled(false);
        isWheelEmulate = false;

        this.client = client;

        SharedPreferences settings = parent.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        moveSpeed = settings.getFloat("touchpadSensity", 1.0f);

        touchPadObject.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        moveX = 0.0f;
                        moveY = 0.0f;
                        isWheelEmulate = false;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        isWheelEmulate = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isWheelEmulate = false;

                        if (moveTimer != null) {
                            moveTimer.cancel();
                        }
                        sendMoveTask = null;
                        moveTimer = null;
                        ignoreFirstBuggyScrollEvent = true;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        if (event.getPointerCount() < 3)
                            isWheelEmulate = false;
                        break;
                    default:
                        break;
                }
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public ImageView getTouchPadObject() {
        return touchPadObject;
    }

    public void SendMove() {
        float absMoveX = Math.abs(moveSpeed*moveX);
        float absMoveY = Math.abs(moveSpeed*moveY);

        if (absMoveX >= 0 && absMoveX < 1 && absMoveY >= 0 && absMoveY < 1) {
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

    public void SendWheel() {
        double absMoveY = Math.abs(wheelSpeed*moveY);

        if (absMoveY >= 0 && absMoveY < 1) {
            return;
        }

        int distanceY = (int)(wheelSpeed*moveY);

        MouseWheel mouseWheelBody = new MouseWheel();
        mouseWheelBody.setAmount(-distanceY);

        RestClient.getApi().mouseWheel(mouseWheelBody).enqueue(new Callback<MousePosition>() {
            @Override
            public void onResponse(Call<MousePosition> call, Response<MousePosition> response) {

            }

            @Override
            public void onFailure(Call<MousePosition> call, Throwable t) {

            }
        });
    }

    private class SendTouchPadMoveTask extends TimerTask {

        @Override
        public void run() {
            if (!isWheelEmulate) {
                SendMove();
            }
            else {
                SendWheel();
            }

            moveX = 0.0f;
            moveY = 0.0f;
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

            Log.i(TAG, "X: " + distanceX + " Y: " + distanceY);

            if (ignoreFirstBuggyScrollEvent) {
                ignoreFirstBuggyScrollEvent = false;
                return true;
            }

            if (moveTimer == null) {
                moveTimer = new Timer();
                sendMoveTask = new SendTouchPadMoveTask();
                moveTimer.schedule(sendMoveTask, mouseMoveDelay, mouseMovePeriod);
            }

            moveX -= distanceX;
            moveY -= distanceY;

            if (distanceY > 0.0) {
                if (isWheelEmulate)
                    debugTextView.setText("wheel up " + distanceX + " " + distanceY);
                else
                    debugTextView.setText("scroll up " + moveX + " " + moveY);
            }
            else if (distanceY < 0.0) {
                if (isWheelEmulate)
                    debugTextView.setText("wheel down " + distanceX + " " + distanceY);
                else
                    debugTextView.setText("scroll down " + moveX + " " + moveY);
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
