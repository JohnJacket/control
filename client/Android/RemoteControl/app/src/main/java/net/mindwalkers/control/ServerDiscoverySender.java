package net.mindwalkers.control;

import android.content.Context;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ServerDiscoverySender extends Thread {
    private boolean running;
    private List<String> ips = Collections.synchronizedList(new LinkedList<String>());
    private Context context;
    private ServerDiscoveryListener serverDiscovery;
    private static final String hey = "Hey";
    public ServerDiscoverySender(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        try {
            serverDiscovery = new ServerDiscoveryListener(context, new ServerDiscoveryListener.BroadcastListener() {
                @Override
                public void onReceive(String msg, String ip) {
                    if (msg.equals(hey) && !ips.contains(ip))
                        ips.add(ip);
                }
            });
            serverDiscovery.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = true;
        while (running) {
            try {
                serverDiscovery.send(hey);
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getIps() {
        return ips;
    }

    public void end() {
        running = false;
        serverDiscovery.end();
    }
}
