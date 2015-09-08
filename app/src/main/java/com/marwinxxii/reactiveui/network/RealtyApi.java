package com.marwinxxii.reactiveui.network;

import com.marwinxxii.reactiveui.entities.SearchRequest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface RealtyApi {
    @POST("/offers")
    Observable<Integer> offersCountForFilter(@Body SearchRequest request);

    @POST("/offers")
    void offersCountForFilterCb(@Body SearchRequest request, Callback<Integer> callback);
}
