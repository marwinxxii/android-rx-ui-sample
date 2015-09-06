package com.marwinxxii.reactiveui;

import android.support.v7.widget.Toolbar;

/**
 * @author Alexey Agapitov <agapitov@yandex-team.ru> on 06.09.2015
 */
public interface IFiltersController {
    void init(FiltersView view, Toolbar toolbar);

    void onStop();
}
