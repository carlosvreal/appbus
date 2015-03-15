package com.archtouch.appbus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;

import com.archtouch.appbus.R;

import com.archtouch.appbus.adapter.TimeListAdapter;
import com.archtouch.appbus.model.Timetable;

import java.util.List;


public class ListTimetableFragment extends Fragment  {

    private AbsListView mListView;
    private ListAdapter mAdapter;
    private List<Timetable> list;

    public static ListTimetableFragment newInstance(List<Timetable> list) {
        ListTimetableFragment fragment = new ListTimetableFragment();
        fragment.setList(list);
        return fragment;
    }

    public ListTimetableFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listtimetable_list, container, false);

        mAdapter = new TimeListAdapter(list, getActivity());

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        return view;
    }

    private void setList(List<Timetable> list) {
        this.list = list;
    }
}