package com.archtouch.appbus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.archtouch.appbus.R;
import com.archtouch.appbus.adapter.StreetListAdapter;
import com.archtouch.appbus.model.Street;
import java.util.ArrayList;
import java.util.List;

public class ListStreetsDetailFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_liststreetsdetail_list, container, false);

        mAdapter = new StreetListAdapter(list, getActivity());

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        return view;
    }

    private void setList(List<Street> list) {
        if(list == null)
            list = new ArrayList<>();
        this.list = list;
    }
}
