package com.marwinxxii.reactiveui.filters;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxRadioGroup;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.marwinxxii.reactiveui.R;
import com.marwinxxii.reactiveui.entities.SearchRequest;
import com.marwinxxii.reactiveui.network.NetworkHelper;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class RxFiltersController implements IFiltersController {
    private Subscription mSubscription;

    @Override
    public void init(FiltersView filters, TextView offersView) {
        mSubscription = Observable.combineLatest(
            RxRadioGroup.checkedChanges(filters.getDealTypeRadioGroup()),
            RxAdapterView.itemSelections(filters.getPropertyTypeSpinner()),

            //pass main thread scheduler to avoid switching threads
            //main thread handler supports scheduling with delay
            Observable.combineLatest(
                RxTextView.textChanges(filters.getPriceFromEditText())
                    .debounce(500L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .filter(price -> {
                        boolean isValid = FiltersHelper.validatePrice(price);
                        filters.setPriceFromErrorVisible(!isValid);
                        return isValid;
                    })
                    .map(FiltersHelper::convertPrice),

                RxTextView.textChanges(filters.getPriceToEditText())
                    .debounce(500L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .filter(price -> {
                        boolean isValid = FiltersHelper.validatePrice(price);
                        filters.setPriceToErrorVisible(!isValid);
                        return isValid;
                    })
                    .map(FiltersHelper::convertPrice),

                (from, to) -> FiltersHelper.processPriceRange(from, to, filters)
            ).filter(pr -> pr != null),

            FiltersHelper::buildRequest
        )
            .doOnNext(req -> offersView.setVisibility(View.GONE))
            .switchMap(req -> {
                return Observable.merge(
                    NetworkHelper.provideApi().offersCountForFilter(req)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(error -> offersView.setVisibility(View.GONE))
                        .onErrorResumeNext(Observable.empty())
                        .doOnNext(count -> FiltersHelper.setOffersCount(offersView, count)),

                    RxView.clicks(filters.getApplyButton()).map((Object o) -> req)
                )
                    .ofType(SearchRequest.class);
            })
            .subscribe(request -> {
                Toast.makeText(filters.getContext(), R.string.filters_applied, Toast.LENGTH_SHORT).show();
            });
    }

    @Override
    public void onStop() {
        mSubscription.unsubscribe();
    }
}
