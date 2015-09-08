package com.marwinxxii.reactiveui.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.marwinxxii.reactiveui.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    protected void bindViews() {
        ButterKnife.bind(this);
        bindToolbar();
    }

    protected void bindToolbar() {
        setSupportActionBar(toolbar);
    }
}
