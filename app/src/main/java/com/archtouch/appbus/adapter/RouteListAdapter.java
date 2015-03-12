package com.archtouch.appbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.archtouch.appbus.R;
import com.archtouch.appbus.model.Route;

import java.util.List;

public class RouteListAdapter extends BaseAdapter {

    private List<Route> routes;
    private Context mContext;

    public RouteListAdapter(List<Route> list, Context mContext) {
        this.routes = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return routes.size();
    }

    @Override
    public Object getItem(int position) {
        return routes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_route_list, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.titleTextRoute);
            holder.title.setFocusable(false);
            holder.description = (TextView) convertView.findViewById(R.id.descriptionTextRoute);
            holder.description.setFocusable(false);
//            holder.imageView = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Route route = routes.get(position);

        holder.title.setText("Route " + (position+1));
        holder.description.setText(route.getLongName());
//        holder.imageView
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;
    }
}