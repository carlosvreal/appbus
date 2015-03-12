package com.archtouch.appbus.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.archtouch.appbus.R;
import com.archtouch.appbus.fragments.ListStreetsDetailFragment;
import com.archtouch.appbus.fragments.ListTimetableFragment;
import com.archtouch.appbus.model.Route;
import com.archtouch.appbus.model.SearchRoute;
import com.archtouch.appbus.model.Street;
import com.archtouch.appbus.model.Timetable;
import com.archtouch.appbus.network.AppBusNetwork;
import com.archtouch.appbus.network.model.JsonDataSearch;
import com.archtouch.appbus.network.model.StreetResponse;
import com.archtouch.appbus.network.model.TimetableResponse;
import com.archtouch.appbus.util.Utils;
import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TimetableDetailActivity extends ActionBarActivity {

//    private MaterialTabHost tabHost;
//    private ViewPager pager;
//    private ViewPagerAdapter adapter;

    private ViewPagerAdapter adapter;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    private Toolbar mToolBar;
    private Route route;

    private List<Street> listStreets;
    private List<Timetable> listTimetables;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_detail);

        route = new Route();
        route.setLongName(getIntent().getExtras().getString(ListMainActivity.ROUTE_NAME));
        route.setId(getIntent().getExtras().getInt(ListMainActivity.ROUTE_ID));

        loadToolbar();

        requestListStreets();

//        initTab();
    }

    public void initTab(){
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);

        pager.setCurrentItem(1);
        tabs.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private void requestListStreets() {

        Utils.showProgressDialog("Loading data...", this);

        //json body search
        JsonDataSearch paramRouteName = new JsonDataSearch();
        paramRouteName.setParamsObject(new SearchRoute(route.getId()));

        Gson gson = new Gson();
        Log.d("JSON BODY:", gson.toJson(paramRouteName).toString());

        AppBusNetwork.findStopsByRouteId(paramRouteName)
            .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).
                finallyDo(new Action0() {
                    @Override
                    public void call() {
//                        Utils.dismissProgressDialog();
                    }
                }).subscribe(new Action1<StreetResponse>() {
            @Override
            public void call(StreetResponse temp) {
                loadStreets(temp.getRows());
            }
        }, AppBusNetwork.newThrowableAction1());
    }

    private void requestListTimetable() {

        Utils.showProgressDialog("Loading data..", this);

        //json body search
        JsonDataSearch paramRouteName = new JsonDataSearch();
        paramRouteName.setParamsObject(new SearchRoute(route.getId()));

        Gson gson = new Gson();
        Log.d("JSON BODY:", gson.toJson(paramRouteName).toString());

        AppBusNetwork.findDeparturesByRouteId(paramRouteName)
            .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).
                finallyDo(new Action0() {
                    @Override
                    public void call() {
//                        Utils.dismissProgressDialog();
                    }
                }).subscribe(new Action1<TimetableResponse>() {
            @Override
            public void call(TimetableResponse temp) {
                loadTimetable(temp.getRows());
            }
        }, AppBusNetwork.newThrowableAction1());
    }

    private void loadStreets(List<Street> list) {
        listStreets = list;
        requestListTimetable();
    }

    private void loadTimetable(List<Timetable> list) {

        Utils.dismissProgressDialog();

        listTimetables = list;

        initTab();
    }

    private void loadToolbar() {
        mToolBar = (Toolbar) findViewById(R.id.screen_default_toolbar);
        mToolBar.setTitle(route.getLongName());

        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }
        if (mToolBar != null) {
            mToolBar.setNavigationIcon(R.drawable.ic_back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Timetable> filterList(String filter){
        List<Timetable> temp = new ArrayList<Timetable>();
        for(Timetable item: listTimetables){
            if(item.getCalendar().equalsIgnoreCase(filter))
                temp.add(item);
        }
        return temp;
    }
//
    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {getString(R.string.title_routes), getString(R.string.title_weekday), getString(R.string.title_saturday),getString(R.string.title_sunday)};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return ListStreetsDetailFragment.newInstance(listStreets);
            } else if (position == 1) {
                return ListTimetableFragment.newInstance(filterList("weekday"));
            } else if (position == 2) {
                return ListTimetableFragment.newInstance(filterList("saturday"));
            } else if(position==3) {
                return ListTimetableFragment.newInstance(filterList("sunday"));
            }
            return new Fragment();
        }
    }
}