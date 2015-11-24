package com.marwinxxii.reactiveui.network;

import com.marwinxxii.reactiveui.entities.SearchRequest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface RealtyApi {
    @POST("/offersCount")
    Observable<Integer> offersCountForFilter(@Body SearchRequest request);

    @POST("/offersCount")
    void offersCountForFilter(@Body SearchRequest request, Callback<Integer> callback);
}
