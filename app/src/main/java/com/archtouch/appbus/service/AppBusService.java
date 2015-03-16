package com.archtouch.appbus.service;


import com.archtouch.appbus.network.model.JsonDataSearch;
import com.archtouch.appbus.network.model.RouteResponse;
import com.archtouch.appbus.network.model.StreetResponse;
import com.archtouch.appbus.network.model.TimetableResponse;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;


public interface AppBusService {

    //    https://api.appglu.com/v1/queries/findRoutesByStopName/run
    @POST("/findRoutesByStopName/run")
    public Observable<RouteResponse> findRoutesByStopName(@Body JsonDataSearch endpointData);

    //    https://api.appglu.com/v1/queries/findStopsByRouteId/run
    @POST("/findStopsByRouteId/run")
    public Observable<StreetResponse> findStopsByRouteId(@Body JsonDataSearch endpointData);

    //    https://api.appglu.com/v1/queries/findDeparturesByRouteId/run
    @POST("/findDeparturesByRouteId/run")
    public Observable<TimetableResponse> findDeparturesByRouteId(@Body JsonDataSearch endpointData);


}
