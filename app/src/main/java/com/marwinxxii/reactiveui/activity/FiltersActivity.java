package com.marwinxxii.reactiveui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.marwinxxii.reactiveui.R;
import com.marwinxxii.reactiveui.filters.ClassicFiltersController;
import com.marwinxxii.reactiveui.filters.FiltersView;
import com.marwinxxii.reactiveui.filters.IFiltersController;
import com.marwinxxii.reactiveui.filters.RxFiltersController;

import butterknife.Bind;

public class FiltersActivity extends BaseActivity {
    @Bind(R.id.filters)
    FiltersView filters;

    @Bind(R.id.found_offers)
    TextView foundOffersView;

    private IFiltersController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        bindViews();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String action = getIntent().getAction();
        String actionRx = getString(R.string.activity_filters_action_rx);
        if (actionRx.equals(action)) {
            controller = new RxFiltersController();
            setTitle(R.string.activity_filters_label_rx);
        } else {
            controller = new ClassicFiltersController();
        }
        controller.init(filters, foundOffersView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.onStop();
    }
}
