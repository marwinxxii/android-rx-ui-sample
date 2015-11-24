package com.marwinxxii.reactiveui.filters;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.marwinxxii.reactiveui.R;
import com.marwinxxii.reactiveui.entities.PriceRange;
import com.marwinxxii.reactiveui.entities.SearchRequest;
import com.marwinxxii.reactiveui.network.DetachableCallback;
import com.marwinxxii.reactiveui.network.NetworkHelper;
import com.marwinxxii.reactiveui.utils.DebouncingTextWatcher;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ClassicFiltersController implements IFiltersController {
    private FiltersView filters;
    private TextView offersView;
    private Integer priceFrom;
    private Integer priceTo;

    private final Callback<Integer> offersCountCallbackImpl = new Callback<Integer>() {
        @Override
        public void success(Integer offersCount, Response response) {
            FiltersHelper.setOffersCount(offersView, offersCount);
        }

        @Override
        public void failure(RetrofitError error) {
            offersView.setVisibility(View.GONE);
        }
    };
    private DetachableCallback<Integer> actualOffersCountCallback;

    @Override
    public void init(FiltersView filters, TextView offersView) {
        this.filters = filters;
        this.offersView = offersView;

        filters.getDealTypeRadioGroup().setOnCheckedChangeListener((group, checkedId) -> onFieldsChanged());

        filters.getPropertyTypeSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onFieldsChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        filters.getPriceFrom().getEditText().addTextChangedListener(new DebouncingTextWatcher(500L, s -> {
            boolean isError = !FiltersHelper.validatePrice(s);
            if (!isError) {
                priceFrom = FiltersHelper.convertPrice(s);
            }
            handlePriceChange(isError, filters.getPriceFrom());
        }));

        filters.getPriceTo().getEditText().addTextChangedListener(new DebouncingTextWatcher(500L, s -> {
            boolean isError = !FiltersHelper.validatePrice(s);
            if (!isError) {
                priceTo = FiltersHelper.convertPrice(s);
            }
            handlePriceChange(isError, filters.getPriceTo());
        }));

        filters.getApplyButton().setOnClickListener(v -> {
            Toast.makeText(filters.getContext(), R.string.filters_applied, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onStop() {
        tryReleaseNetworkCallback();
    }

    private void onFieldsChanged() {
        int dealTypeId = filters.getDealTypeRadioGroup().getCheckedRadioButtonId();
        int propertyTypeId = (int) filters.getPropertyTypeSpinner().getSelectedItemId();
        PriceRange price = FiltersHelper.processPriceRange(priceFrom, priceTo, filters);
        SearchRequest request = FiltersHelper.buildRequest(dealTypeId, propertyTypeId, price);

        offersView.setVisibility(View.GONE);
        tryReleaseNetworkCallback();//cancel previous + avoid leak
        actualOffersCountCallback = new DetachableCallback<>(offersCountCallbackImpl);
        NetworkHelper.provideApi().offersCountForFilter(request, actualOffersCountCallback);
    }

    private void handlePriceChange(boolean isError, TextInputLayout priceView) {
        FiltersHelper.toggleShowPriceError(isError, priceView);
        filters.getApplyButton().setEnabled(!isError);
        if (!isError) {
            PriceRange range = FiltersHelper.processPriceRange(priceFrom, priceTo, filters);
            if (range != null) {
                onFieldsChanged();
            }
        }
    }

    private void tryReleaseNetworkCallback() {
        if (actualOffersCountCallback != null) {
            actualOffersCountCallback.detach();
            actualOffersCountCallback = null;
        }
    }
}
