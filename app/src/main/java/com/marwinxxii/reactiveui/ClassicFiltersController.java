package com.marwinxxii.reactiveui;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * @author Alexey Agapitov <agapitov@yandex-team.ru> on 06.09.2015
 */
public class ClassicFiltersController implements IFiltersController {
    private FiltersView filters;
    private Toolbar toolbar;

    @Override
    public void init(FiltersView filters, Toolbar toolbar) {
        this.filters = filters;
        this.toolbar = toolbar;

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

        filters.getPrice().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (SearchHelper.validatePrice(s)) {
                    onFieldsChanged();
                } else {
                    Toast.makeText(filters.getContext(), "Price is too low or incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onStop() {
    }

    private void onFieldsChanged() {
        int dealTypeId = filters.getDealType().getCheckedRadioButtonId();
        int propertyTypeId = (int) filters.getPropertyType().getSelectedItemId();
        int price = SearchHelper.convertPrice(filters.getPrice().getText());
        toolbar.setTitle(SearchHelper.buildRequest(dealTypeId, propertyTypeId, price).toString());
    }
}
