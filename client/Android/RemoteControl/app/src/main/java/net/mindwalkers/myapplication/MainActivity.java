package net.mindwalkers.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView touchPadView;
    Button leftMouseButton, rightMouseButton, middleMouseButton;
    TextView debugTextView;
    GestureDetector leftMouseButtonGestureDetector;
    GestureDetector middleMouseButtonGestureDetector;
    GestureDetector rightMouseButtonGestureDetector;
    GestureDetector touchPadGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchPadView = (ImageView)findViewById(R.id.touchPad);
        leftMouseButton = (Button)findViewById(R.id.leftButton);
        middleMouseButton = (Button)findViewById(R.id.middleButton);
        rightMouseButton = (Button)findViewById(R.id.rightButton);
        debugTextView = (TextView)findViewById(R.id.debugTextView);
        leftMouseButtonGestureDetector = new GestureDetector(this, new LeftMouseButtonGesture());
        middleMouseButtonGestureDetector = new GestureDetector(this, new MiddleMouseButtonGesture());
        rightMouseButtonGestureDetector = new GestureDetector(this, new RightMouseButtonGesture());
        touchPadGestureDetector = new GestureDetector(this, new TouchPadGesture());
        touchPadGestureDetector.setIsLongpressEnabled(false);

        leftMouseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                leftMouseButtonGestureDetector.onTouchEvent(event);
                return false;
            }
        });

        middleMouseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                middleMouseButtonGestureDetector.onTouchEvent(event);
                return true;
            }
        });
        rightMouseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rightMouseButtonGestureDetector.onTouchEvent(event);
                return false;
            }
        });

        touchPadView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchPadGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private class LeftMouseButtonGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            debugTextView.setText("left double tap" );
            return super.onDoubleTapEvent(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            debugTextView.setText("left long press");
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            debugTextView.setText("left single up");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            debugTextView.setText("left up");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            debugTextView.setText("left down");
            return super.onDown(e);
        }
    }

    private class RightMouseButtonGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            debugTextView.setText("right double tap" );
            return super.onDoubleTapEvent(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            debugTextView.setText("right long press");
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            debugTextView.setText("right single up");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            debugTextView.setText("right up");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            debugTextView.setText("right down");
            return super.onDown(e);
        }
    }

    private class MiddleMouseButtonGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            debugTextView.setText("middle fling"  + velocityX + " " + velocityY);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        /*
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            debugTextView.setText("middle scroll"  + distanceX + " " + distanceY);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
        */
    }

    private class TouchPadGesture extends GestureDetector.SimpleOnGestureListener {
        /*@Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            debugTextView.setText("fling touch " + velocityX + " " + velocityY);
            return super.onFling(e1, e2, velocityX, velocityY);
        }*/

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            debugTextView.setText("scroll touch " + distanceX + " " + distanceY);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
