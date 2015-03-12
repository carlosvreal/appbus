package com.archtouch.appbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.archtouch.appbus.R;
import com.archtouch.appbus.model.Street;

import java.util.List;


public class StreetListAdapter extends BaseAdapter {

    private List<Street> streets;
    private Context mContext;

    public StreetListAdapter(List<Street> list, Context mContext) {
        this.streets = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return streets.size();
    }

    @Override
    public Object getItem(int position) {
        return streets.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_label, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.textItemLabel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Street street = streets.get(position);
        holder.title.setText(street.getName());

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
    }
}