package com.marwinxxii.reactiveui;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.marwinxxii.reactiveui.entities.SearchRequest;

public class ClassicFiltersController implements IFiltersController {
    private FiltersView filters;
    private Integer priceFrom;
    private Integer priceTo;

    @Override
    public void init(FiltersView filters, TextView offersView) {
        this.filters = filters;

        filters.getDealType().setOnCheckedChangeListener((group, checkedId) -> onFieldsChanged());

        filters.getPropertyType().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onFieldsChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        filters.getPriceFrom().getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isError = !FiltersHelper.validatePrice(s);
                if (!isError) {
                    priceFrom = FiltersHelper.convertPrice(s);
                }
                handlePriceChange(isError, filters.getPriceFrom());
            }
        });

        filters.getPriceTo().getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isError = !FiltersHelper.validatePrice(s);
                if (!isError) {
                    priceTo = FiltersHelper.convertPrice(s);
                }
                handlePriceChange(isError, filters.getPriceTo());
            }
        });

        filters.getApplyButton().setOnClickListener(v -> {
            Snackbar.make(filters, R.string.filters_applied, Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onStop() {
    }

    private void onFieldsChanged() {
        int dealTypeId = filters.getDealType().getCheckedRadioButtonId();
        int propertyTypeId = (int) filters.getPropertyType().getSelectedItemId();
        PriceRange price = FiltersHelper.processPriceRange(priceFrom, priceTo, filters);
        SearchRequest request = FiltersHelper.buildRequest(dealTypeId, propertyTypeId, price);
    }

    private void handlePriceChange(boolean isError, TextInputLayout priceView) {
        FiltersHelper.handlePriceError(isError, priceView);
        filters.getApplyButton().setEnabled(!isError);
        if (!isError) {
            PriceRange range = FiltersHelper.processPriceRange(priceFrom, priceTo, filters);
            if (range != null) {
                onFieldsChanged();
            }
        }
    }

    public static class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
