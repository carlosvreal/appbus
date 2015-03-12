package com.archtouch.appbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.archtouch.appbus.R;
import com.archtouch.appbus.model.Street;
import com.archtouch.appbus.model.Timetable;

import java.util.List;

/**
 * Created by real on 9/3/15.
 */
public class TimeListAdapter extends BaseAdapter {

    private List<Timetable> timetables;
    private Context mContext;

    public TimeListAdapter(List<Timetable> list, Context mContext) {
        this.timetables = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return timetables.size();
    }

    @Override
    public Object getItem(int position) {
        return timetables.get(position);
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

        Timetable time = timetables.get(position);
        holder.title.setText(time.getTime());

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
    }
}