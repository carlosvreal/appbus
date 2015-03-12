package com.archtouch.appbus.network;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

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

/**
 * Created by real on 6/3/15.
 */
public class AppBusNetwork {

    public static String urlBase = "https://api.appglu.com/v1/queries";
    public static AppBusService service;
    public static int TIME_OUT_SERVER = 10 * 1000;

    static {
        setBaseUrl(urlBase);
    }

    public static void setBaseUrl(String baseUrl) {
        service = createRestAdapter(baseUrl).create(AppBusService.class);
    }

    private final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

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
//                request.addHeader("Connection", "Keep-Alive");
//                request.addHeader("Pragma", "no-cache");
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
//        okHttpClient.setHostnameVerifier(DO_NOT_VERIFY);
        return new OkClient(okHttpClient);
    }

    public static Action1<Throwable> newThrowableAction1() {
//        Utils.dismissProgressDialog();
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("RM", "error", throwable);
            }
        };
    }

//services
    public static Observable<RouteResponse> findRoutesByStopName(JsonDataSearch endpointData){
        return service.findRoutesByStopName(endpointData);
    }

    public static Observable<StreetResponse> findStopsByRouteId(JsonDataSearch endpointData){
        return service.findStopsByRouteId(endpointData);
    }

    public static Observable<TimetableResponse> findDeparturesByRouteId(JsonDataSearch endpointData){
        return service.findDeparturesByRouteId(endpointData);
    }
}