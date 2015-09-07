package com.marwinxxii.reactiveui;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxRadioGroup;
import com.jakewharton.rxbinding.widget.RxTextView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

public class RxFiltersController implements IFiltersController {
    private Subscription mSubscription;

    @Override
    public void init(FiltersView filters, Toolbar toolbar) {
        mSubscription = Observable.combineLatest(
            RxRadioGroup.checkedChanges(filters.getDealType()),
            RxAdapterView.itemSelections(filters.getPropertyType()),

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
            .flatMap(request -> RxView.clicks(filters.getApplyButton()).map(o -> request))
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
