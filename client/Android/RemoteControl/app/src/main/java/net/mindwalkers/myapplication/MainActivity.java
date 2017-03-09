package net.mindwalkers.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MouseButton leftMouseButton, rightMouseButton;
    TouchPad touchPad;
    TextView debugTextView;
    public RestClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestServer server = new RestServer("http://192.168.112.152:5000/", "Server");
        client = new RestClient(server);

        debugTextView = (TextView)findViewById(R.id.debugTextView);
        touchPad = new TouchPad(this, (ImageView)findViewById(R.id.touchPad), debugTextView, client);
        leftMouseButton = new MouseButton(this, (Button)findViewById(R.id.leftButton), MouseButton.LEFT, debugTextView, client);
        rightMouseButton = new MouseButton(this, (Button)findViewById(R.id.rightButton), MouseButton.RIGHT, debugTextView, client);
    }
}
