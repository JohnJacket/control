package net.mindwalkers.control;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ServerDiscovery extends Thread {
    private boolean running;
    private DiscoveredEventListener listener;
    private List<String> ips = Collections.synchronizedList(new LinkedList<String>());
    private Context context;
    private ServerDiscoveryListener serverDiscoveryListener;
    private static final String hey = "Hey";
    public ServerDiscovery(Context context, DiscoveredEventListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            serverDiscoveryListener = new ServerDiscoveryListener(context, new ServerDiscoveryListener.BroadcastListener() {
                @Override
                public void onReceive(String msg, String ip) {
                    Log.d(hey, msg);
                    if (msg.startsWith(hey) && !ips.contains(ip)) {
                        ips.add(ip);
                        String name = msg.substring(msg.indexOf(":")+1);
                        listener.onReceive(ip, name);
                    }

                }
            });
            serverDiscoveryListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = true;
        while (running) {
            try {
                serverDiscoveryListener.send(hey);
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public interface DiscoveredEventListener {
        public void onReceive(String ip, String name);
    }

    public List<String> getIps() {
        return ips;
    }

    public void end() {
        running = false;
        serverDiscoveryListener.end();
    }
}
