package net.mindwalkers.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class ServerChooserActivity extends AppCompatActivity {
    private ListView serverListView;
    final ArrayList<RestServer> servers = new ArrayList<RestServer>();
    private String TAG = "ServerChooserActivity";
    private ServerDiscovery serverDiscovery;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_chooser);
        createToolbar();
        final ArrayList<RestServer> servers = new ArrayList<RestServer>();
        //RestServer server = new RestServer("192.168.112.152", "TestServer");
        //servers.add(server);
        serverDiscovery = new ServerDiscovery(this, new ServerDiscovery.DiscoveredEventListener() {
            @Override
            public void onReceive(String ip, String name) {
                RestServer serv = new RestServer(ip, name);
                servers.add(serv);
            }
        });
        serverDiscovery.start();

        serverListView = (ListView) findViewById(R.id.serverListView);
        serverListView.setAdapter(new ServerListAdapter(this, servers));
        serverListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = serverListView.getItemAtPosition(position);
                RestServer serv = (RestServer) obj;
                Intent mainPage = new Intent(ServerChooserActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("serverName", serv.getName());
                bundle.putString("serverAddress", serv.getAddress());
                mainPage.putExtras(bundle);
                startActivity(mainPage);
            }
        });
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.serverChooserToolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server_chooser, menu);
        return true;
    }

    @Override
    protected void onStop() {
        serverDiscovery.end();
        super.onStop();
    }
}
