package net.mindwalkers.control;

import android.content.Context;
import android.view.GestureDetector;
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

public class TouchPad {
    private Context parent;
    private GestureDetector gestureDetector;
    private TextView debugTextView;
    private ImageView touchPadObject;
    private RestClient client;
    private boolean isWheelEmulate;

    private float moveSpeed = 1.0f;
    private float wheelSpeed = 2.0f;

    private float moveX;
    private float moveY;

    private Timer moveTimer;
    private Timer wheelTimer;

    private SendTouchPadMove sendMoveTask;


    private static final int mouseMoveDelay = 150;
    private static final int mouseMovePeriod = 50;


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

                        if (moveTimer != null)
                            moveTimer.cancel();
                        sendMoveTask = null;
                        moveTimer = null;
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

    private class SendTouchPadMove extends TimerTask {

        @Override
        public void run() {
            if (!isWheelEmulate) {
                float absMoveX = Math.abs(moveSpeed*moveX);
                float absMoveY = Math.abs(moveSpeed*moveY);

                if (absMoveX >= 0 && absMoveX < 1 && absMoveY >= 0 && absMoveY < 1)
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
            else {
                double absMoveY = Math.abs(wheelSpeed*moveY);

                if (absMoveY >= 0 && absMoveY < 1)
                {
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

            if (moveTimer == null)
            {
                moveTimer = new Timer();
                sendMoveTask = new SendTouchPadMove();
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
