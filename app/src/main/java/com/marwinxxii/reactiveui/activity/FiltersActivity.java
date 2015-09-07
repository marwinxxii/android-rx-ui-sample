package com.marwinxxii.reactiveui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.marwinxxii.reactiveui.ClassicFiltersController;
import com.marwinxxii.reactiveui.FiltersView;
import com.marwinxxii.reactiveui.IFiltersController;
import com.marwinxxii.reactiveui.R;
import com.marwinxxii.reactiveui.RxFiltersController;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FiltersActivity extends AppCompatActivity {
    @Bind(R.id.filters)
    FiltersView filters;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private IFiltersController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        String action = getIntent().getAction();
        String actionRx = getString(R.string.activity_filters_action_rx);
        if (actionRx.equals(action)) {
            controller = new RxFiltersController();
            setTitle(R.string.activity_filters_label_rx);
        } else {
            controller = new ClassicFiltersController();
        }
        controller.init(filters, toolbar);
    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.onStop();
    }
}
