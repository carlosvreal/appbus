package com.archtouch.appbus.activitys;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import com.archtouch.appbus.R;
import com.archtouch.appbus.adapter.RouteListAdapter;
import com.archtouch.appbus.model.Route;
import com.archtouch.appbus.model.SearchEndpoint;
import com.archtouch.appbus.network.AppBusNetwork;
import com.archtouch.appbus.network.model.JsonDataSearch;
import com.archtouch.appbus.network.model.RouteResponse;
import com.archtouch.appbus.util.Utils;
import com.google.gson.Gson;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ListMainActivity extends ActionBarActivity {

    private static final int RELOAD_SEARCH_RESULT = 456;
    public static final int MAP_RESULT = 789;

    public static final String ROUTE_ID = "route_id";
    public static final String ROUTE_NAME = "route_name";
    public static final String STREET_NAME = "street_name_map";

    private Toolbar mToolBar;
    private RouteListAdapter adapter;
    private ListView mListView;
    private EditText searchField;
    private List<Route> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_route_main);

        initToolbar();
        initSearchBarComponent();
    }

    private void initToolbar() {
        mToolBar = (Toolbar) findViewById(R.id.screen_default_toolbar);
        mToolBar.setTitle(R.string.title_activity_list_main);

        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }
        if (mToolBar != null) {
            mToolBar.setNavigationIcon(R.drawable.bus);
        }
    }

    private void initSearchBarComponent(){

        searchField = (EditText) findViewById(R.id.edittextContentSearch);

        Button buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListMainActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                if(!Utils.isNetworkAvailable(ListMainActivity.this)) {
                    Utils.alertDialogOk(getString(R.string.title_warning),getString(R.string.msg_connectionless),ListMainActivity.this);
                } else {
                    if (searchField.getText().toString().trim().isEmpty()) {
                        Utils.alertDialogOk(getString(R.string.title_warning), getString(R.string.msg_error_empty_field), ListMainActivity.this);
                    } else {
                        if(routes != null)
                            routes.clear();

                        if(adapter != null)
                            adapter.notifyDataSetChanged();

                        requestSearchRoutes(searchField.getText().toString());
                    }
                }
            }
        });
    }

    /**
     * Send request for search route
     * @param search
     */
    private void requestSearchRoutes(String search) {

        saveDataSearchPreference(search);

        searchField.setFocusable(false);
        Utils.showProgressDialog(getString(R.string.title_msg_load_routes), this);

        JsonDataSearch paramRouteName = new JsonDataSearch();
        paramRouteName.setParamsObject(new SearchEndpoint(search));

        Gson gson = new Gson();
        Log.d("JSON BODY:", gson.toJson(paramRouteName).toString());

        //execute request for find routes by stopname
        AppBusNetwork.findRoutesByStopName(paramRouteName)
           .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread()).
                    finallyDo(new Action0() {
                        @Override
                        public void call() {
                        }
                    }).subscribe(new Action1<RouteResponse>() {
            @Override
            public void call(RouteResponse temp) {
                loadRoutesResultSearch(temp.getRows());
            }
        }, AppBusNetwork.newThrowableAction1());
    }

    /**
     * receive list of routes and load on listview
     * @param listRoutes
     */
    private void loadRoutesResultSearch(List<Route> listRoutes){

        Utils.dismissProgressDialog();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        searchField.setFocusableInTouchMode(true);

        if(listRoutes == null || listRoutes.size() == 0) {
            Utils.alertDialogOk(getString(R.string.title_result_load),getString(R.string.msg_without_result_search) ,ListMainActivity.this);
        } else {

            routes = listRoutes;
            adapter = new RouteListAdapter(listRoutes, this);
            mListView = (ListView) findViewById(R.id.listViewRoutes);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    openDetailTimetableRoute(routes.get(position));
                }
            });
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_map) {
            openMapActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Call details route passing route id and name
     *
     * @param route
     */
    private void openDetailTimetableRoute(Route route){
        Intent intent = new Intent(ListMainActivity.this, TimetableDetailActivity.class);
        intent.putExtra(ROUTE_ID, route.getId());
        intent.putExtra(ROUTE_NAME, route.getLongName());

        startActivityForResult(intent, RELOAD_SEARCH_RESULT);
    }

    private void openMapActivity(){
        Intent intent = new Intent(ListMainActivity.this, BusMapActivity.class);
        startActivityForResult(intent, MAP_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if(requestCode == RELOAD_SEARCH_RESULT){

            String search = loadDataSearch();
            if(search != null){
                //load last search
                searchField.setText(loadDataSearch());
                requestSearchRoutes(loadDataSearch());
            }
        }else if(requestCode == MAP_RESULT){
            //execute search using street name selected in BusMapActivity
             if(data != null) {
                String street = data.getExtras().getString(STREET_NAME);
                if (street != null){
                    if (!street.isEmpty()) {

                        if (routes != null)
                            routes.clear();

                        if (adapter != null)
                            adapter.notifyDataSetChanged();

                        searchField.setText(street);
                        requestSearchRoutes(street);
                    }
                }
            }

           searchField.setFocusableInTouchMode(true);
        }
    }

    private void saveDataSearchPreference(String search){
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.SP_BUS), getApplicationContext().MODE_PRIVATE).edit();
        editor.putString(ROUTE_NAME, search).commit();
    }

    private String loadDataSearch(){
        SharedPreferences preferences=getSharedPreferences(getString(R.string.SP_BUS), getApplicationContext().MODE_PRIVATE);
        return preferences.getString("",null);
    }
}