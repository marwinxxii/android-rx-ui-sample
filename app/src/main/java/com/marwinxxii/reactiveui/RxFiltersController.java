package com.marwinxxii.reactiveui;

import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxRadioGroup;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Alexey Agapitov <agapitov@yandex-team.ru> on 06.09.2015
 */
public class RxFiltersController implements IFiltersController {
    private CompositeSubscription subscriptions;

    @Override
    public void init(FiltersView filters, Toolbar toolbar) {
        subscriptions = new CompositeSubscription();
        Subscription s = Observable.combineLatest(
            RxRadioGroup.checkedChanges(filters.getDealType()),
            RxAdapterView.itemSelections(filters.getPropertyType()),
            RxTextView.textChanges(filters.getPrice())
                .filter(p -> {
                    boolean valid = SearchHelper.validatePrice(p);
                    if (!valid) {
                        Toast.makeText(filters.getContext(), "Price is too low or incorrect", Toast.LENGTH_SHORT).show();
                    }
                    return valid;
                })
                .map(SearchHelper::convertPrice),

            SearchHelper::buildRequest
        ).subscribe(request -> {
            toolbar.setTitle(request.toString());
        });
        subscriptions.add(s);
    }

    @Override
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
