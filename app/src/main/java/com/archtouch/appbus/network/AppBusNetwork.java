package com.archtouch.appbus.network;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.archtouch.appbus.network.model.JsonDataSearch;
import com.archtouch.appbus.network.model.RouteResponse;
import com.archtouch.appbus.network.model.StreetResponse;
import com.archtouch.appbus.network.model.TimetableResponse;
import com.archtouch.appbus.service.AppBusService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.functions.Action1;


public class AppBusNetwork {

    private static final String urlBase = "https://api.appglu.com/v1/queries";
    private static AppBusService service;
    private static final int TIME_OUT_SERVER = 10 * 1000;

    static {
        //init url base in static block, for all requests
        setBaseUrl(urlBase);
    }

    private static void setBaseUrl(String baseUrl) {
        service = createRestAdapter(baseUrl).create(AppBusService.class);
    }

    private static RestAdapter createRestAdapter(String baseUrl) {
        return new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setConverter(createGsonConverter())
                .setRequestInterceptor(createRequestInterceptor())
                .setClient(createOkHttpClient())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

    private static RequestInterceptor createRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
                request.addHeader("X-AppGlu-Environment", "staging");
                String credential = Credentials.basic("WKD4N7YMA1uiM8V", "DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68");
                request.addHeader("Authorization", credential);
            }
        };
    }

    private static GsonConverter createGsonConverter() {
        return new GsonConverter(
                new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                        .setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
                        .setPrettyPrinting()
                        .registerTypeAdapter(Date.class, new DateTypeAdapter())
                        .create()
        );
    }

    private static OkClient createOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(TIME_OUT_SERVER, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(TIME_OUT_SERVER, TimeUnit.MILLISECONDS);
        return new OkClient(okHttpClient);
    }

    /**
     * static method for get error retrofit
     * @return
     */
    public static Action1<Throwable> newThrowableAction1() {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("RM", "error", throwable);
            }
        };
    }

     /**
     * interface for request findRoutesByStopName
     * @param endpointData
     * @return
     */
    public static Observable<RouteResponse> findRoutesByStopName(JsonDataSearch endpointData){
        return service.findRoutesByStopName(endpointData);
    }

    /**
     * interface for request findStopsByRouteId
     * @param endpointData
     * @return
     */
    public static Observable<StreetResponse> findStopsByRouteId(JsonDataSearch endpointData){
        return service.findStopsByRouteId(endpointData);
    }

    /** interface for request findDeparturesByRouteId
     *
     * @param endpointData
     * @return
     */
    public static Observable<TimetableResponse> findDeparturesByRouteId(JsonDataSearch endpointData){
        return service.findDeparturesByRouteId(endpointData);
    }
}