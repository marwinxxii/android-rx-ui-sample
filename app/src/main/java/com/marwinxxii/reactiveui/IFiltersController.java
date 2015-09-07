package com.marwinxxii.reactiveui;

import android.support.v7.widget.Toolbar;

public interface IFiltersController {
    void init(FiltersView view, Toolbar toolbar);

    void onStop();
}
