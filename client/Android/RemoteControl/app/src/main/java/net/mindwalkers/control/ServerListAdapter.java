package net.mindwalkers.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ServerListAdapter extends BaseAdapter {
    private ArrayList<RestServer> listData;
    private LayoutInflater layoutInflater;

    public ServerListAdapter(Context aContext, ArrayList<RestServer> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.server_list_layout, null);
            holder = new ViewHolder();
            holder.serverNameView = (TextView) convertView.findViewById(R.id.serverNameTextView);
            holder.serverAddressView = (TextView) convertView.findViewById(R.id.serverAddressTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.serverNameView.setText(listData.get(position).getName());
        holder.serverAddressView.setText(listData.get(position).getAddress());
        return convertView;
    }

    static class ViewHolder {
        TextView serverNameView;
        TextView serverAddressView;
    }
}
