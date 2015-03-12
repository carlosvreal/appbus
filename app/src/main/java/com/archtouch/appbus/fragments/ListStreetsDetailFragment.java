package com.archtouch.appbus.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.archtouch.appbus.R;
import com.archtouch.appbus.adapter.StreetListAdapter;
import com.archtouch.appbus.model.Street;
import java.util.ArrayList;
import java.util.List;

public class ListStreetsDetailFragment extends Fragment implements AbsListView.OnItemClickListener {

    private AbsListView mListView;
    private List<Street> list;

    private StreetListAdapter mAdapter;

    public static ListStreetsDetailFragment newInstance(List<Street> list) {
        ListStreetsDetailFragment fragment = new ListStreetsDetailFragment();
        fragment.setList(list);
        return fragment;
    }

    public ListStreetsDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_liststreetsdetail_list, container, false);

        mAdapter = new StreetListAdapter(list, getActivity());

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

    public void setList(List<Street> list) {
        if(list == null)
            list = new ArrayList<Street>();
        this.list = list;
    }
}
