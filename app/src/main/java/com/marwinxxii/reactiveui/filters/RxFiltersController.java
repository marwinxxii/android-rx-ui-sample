package com.marwinxxii.reactiveui.filters;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxRadioGroup;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.marwinxxii.reactiveui.R;
import com.marwinxxii.reactiveui.entities.SearchRequest;
import com.marwinxxii.reactiveui.network.NetworkHelper;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class RxFiltersController implements IFiltersController {
    private Subscription mSubscription;

    @Override
    public void init(FiltersView filters, TextView offersView) {
        mSubscription = Observable.combineLatest(
            RxRadioGroup.checkedChanges(filters.getDealTypeRadioGroup()),
            RxAdapterView.itemSelections(filters.getPropertyTypeSpinner()),

            Observable.combineLatest(
                RxTextView.textChanges(filters.getPriceFrom().getEditText())
                    .filter(filterProcessPrice(filters, filters.getPriceFrom()))
                    .map(FiltersHelper::convertPrice),

                RxTextView.textChanges(filters.getPriceTo().getEditText())
                    .filter(filterProcessPrice(filters, filters.getPriceTo()))
                    .map(FiltersHelper::convertPrice),

                (from, to) -> FiltersHelper.processPriceRange(from, to, filters)
            ).filter(pr -> pr != null),

            FiltersHelper::buildRequest
        )
            .flatMap(req -> {
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
                Snackbar.make(filters, R.string.filters_applied, Snackbar.LENGTH_SHORT).show();
            });
    }

    @Override
    public void onStop() {
        mSubscription.unsubscribe();
    }

    private static Func1<CharSequence, Boolean> filterProcessPrice(FiltersView filters, TextInputLayout priceView) {
        return price -> {
            boolean isError = !FiltersHelper.validatePrice(price);
            FiltersHelper.handlePriceError(isError, priceView);
            filters.getApplyButton().setEnabled(!isError);
            return !isError;
        };
    }
}
