package com.archtouch.appbus.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.archtouch.appbus.R;

import com.archtouch.appbus.adapter.TimeListAdapter;
import com.archtouch.appbus.model.Timetable;

import java.util.List;


public class ListTimetableFragment extends Fragment implements AbsListView.OnItemClickListener {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listtimetable_list, container, false);

        mAdapter = new TimeListAdapter(list, getActivity());

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void setList(List<Timetable> list) {
        this.list = list;
    }
}