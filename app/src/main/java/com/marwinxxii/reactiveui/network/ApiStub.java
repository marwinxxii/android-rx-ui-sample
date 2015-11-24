package com.marwinxxii.reactiveui.network;

import com.marwinxxii.reactiveui.entities.PriceRange;
import com.marwinxxii.reactiveui.entities.DealType;
import com.marwinxxii.reactiveui.entities.SearchRequest;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.http.Body;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class ApiStub implements RealtyApi {
    @Override
    public Observable<Integer> offersCountForFilter(SearchRequest request) {
        Observable<Long> timer = Observable.timer(new Random().nextInt(500), TimeUnit.MILLISECONDS);
        if (new Random().nextBoolean()) {
            return timer.flatMap(t -> Observable.just(calculateOffersCount(request)));
        } else {
            return timer.flatMap(t -> Observable.error(
                RetrofitError.networkError("http://localhost/offersCount", new IOException("Simulated IO error"))));
        }
    }

    @Override
    public void offersCountForFilter(@Body SearchRequest request, Callback<Integer> callback) {
        offersCountForFilter(request)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Integer>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    callback.failure((RetrofitError) e);
                }

                @Override
                public void onNext(Integer integer) {
                    callback.success(integer, null);
                }
            });
    }

    private static int calculateOffersCount(SearchRequest request) {
        return new Random().nextInt(DealType.BUY.equals(request.getDeal()) ? 6000 : 15000);
        /*int base;
        int minPrice, maxPrice;
        if (DealType.BUY.equals(request.getDeal())) {
            base = new Random().nextInt(5000);
            minPrice = 3_000_000;
            maxPrice = 10_000_000;
        } else {
            base = new Random().nextInt(15000);
            minPrice = 10000;
            maxPrice = 50000;
        }

        PriceRange price = request.getPrice();
        int from = Math.max(minPrice, price.getFrom() == null ? 0 : price.getFrom());
        int to = Math.min(maxPrice, price.getTo() == null ? 0 : price.getTo());
        return (int) ((from - to) / (double) minPrice * base);*/
    }
}
