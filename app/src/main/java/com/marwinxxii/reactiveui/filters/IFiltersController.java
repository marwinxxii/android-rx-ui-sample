package com.marwinxxii.reactiveui.filters;

import android.widget.TextView;

public interface IFiltersController {
    void init(FiltersView view, TextView offersView);

    void onStop();
}
