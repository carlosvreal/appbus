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

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TimetableDetailActivity extends ActionBarActivity {

    private static final String WEEKDAY = "weekday";
    private static final String SATURDAY = "saturday";
    private static final String SUNDAY = "sunday";

    private ViewPagerAdapter adapter;
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

        initToolbar();

        requestListStreets();
//        requestListTimetable();
//        initTabsDetail();
    }

    private void initToolbar() {
        mToolBar = (Toolbar) findViewById(R.id.screen_default_toolbar);
        mToolBar.setTitle(route.getLongName());

        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }
        if (mToolBar != null) {
            mToolBar.setNavigationIcon(R.drawable.ic_back);
        }
    }

    /**
     * Request list streets route
     */
    private void requestListStreets() {

        Utils.showProgressDialog("Loading data...", this);

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
                    }
                }).subscribe(new Action1<StreetResponse>() {
            @Override
            public void call(StreetResponse temp) {
                Utils.dismissProgressDialog();

                listStreets = temp.getRows();

                requestListTimetable();
            }
        }, AppBusNetwork.newThrowableAction1());
    }

    /**
     * Request list of timetable route
     */
    private void requestListTimetable() {

        Utils.showProgressDialog(getString(R.string.msg_loading_alert), this);

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
                    }
                }).subscribe(new Action1<TimetableResponse>() {
            @Override
            public void call(TimetableResponse temp) {

                Utils.dismissProgressDialog();
                listTimetables = temp.getRows();
                initTabsDetail();
            }
        }, AppBusNetwork.newThrowableAction1());
    }

    /**
     * init tabs Routes, Weekday, saturday and sunday
     */
    private void initTabsDetail(){
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);

        pager.setCurrentItem(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Execute basic filter using
     * @param filter
     * @return
     */
    private List<Timetable> filterList(String filter) {
        List<Timetable> temp = new ArrayList<>();
        if (listTimetables != null){
            for (Timetable item : listTimetables) {
                if (item.getCalendar().equalsIgnoreCase(filter))
                    temp.add(item);
            }
        }
        return temp;
    }

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
                return ListTimetableFragment.newInstance(filterList(WEEKDAY));
            } else if (position == 2) {
                return ListTimetableFragment.newInstance(filterList(SATURDAY));
            } else if(position==3) {
                return ListTimetableFragment.newInstance(filterList(SUNDAY));
            }
            return new Fragment();
        }
    }
}