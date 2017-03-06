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
    MouseButton leftMouseButton, rightMouseButton;
    TextView debugTextView;
    GestureDetector touchPadGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchPadView = (ImageView)findViewById(R.id.touchPad);
        debugTextView = (TextView)findViewById(R.id.debugTextView);
        leftMouseButton = new MouseButton(this, (Button)findViewById(R.id.leftButton), MouseButton.LEFT, debugTextView);
        rightMouseButton = new MouseButton(this, (Button)findViewById(R.id.rightButton), MouseButton.RIGHT, debugTextView);

        touchPadGestureDetector = new GestureDetector(this, new TouchPadGesture());
        touchPadGestureDetector.setIsLongpressEnabled(false);

        touchPadView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchPadGestureDetector.onTouchEvent(event);
                return true;
            }
        });
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
