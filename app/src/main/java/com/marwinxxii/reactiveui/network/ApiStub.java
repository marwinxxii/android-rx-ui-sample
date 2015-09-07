package com.marwinxxii.reactiveui.network;

import com.marwinxxii.reactiveui.PriceRange;
import com.marwinxxii.reactiveui.entities.DealType;
import com.marwinxxii.reactiveui.entities.PropertyType;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public final class ApiStub {
    private ApiStub() {
    }

    public static Observable<Integer> offersCountForFilter(
        DealType deal, PropertyType property, PriceRange price
    ) {
        Observable<Long> timer = Observable.timer(new Random().nextInt(500), TimeUnit.MILLISECONDS);
        if (new Random().nextBoolean()) {
            return timer.flatMap(t -> Observable.just(calculateOffersCount(deal, property, price)));
        } else {
            return timer.flatMap(t -> Observable.error(new IOException("Simulated IO error")));
        }
    }

    public static void offersCountForFilter(DealType deal, PropertyType property, PriceRange price,
        Action1<Integer> success, Action1<Throwable> error) {
        offersCountForFilter(deal, property, price)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(success, error);
    }

    private static int calculateOffersCount(DealType deal, PropertyType property, PriceRange price) {
        int base;
        int minPrice, maxPrice;
        if (DealType.BUY.equals(deal)) {
            base = new Random().nextInt(5000);
            minPrice = 3_000_000;
            maxPrice = 10_000_000;
        } else {
            base = new Random().nextInt(15000);
            minPrice = 10000;
            maxPrice = 50000;
        }

        int from = Math.max(minPrice, price.getFrom() == null ? 0 : price.getFrom());
        int to = Math.min(maxPrice, price.getTo() == null ? 0 : price.getTo());
        return (int)((from - to) / (double)minPrice * base);
    }
}
