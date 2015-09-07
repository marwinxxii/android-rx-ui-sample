package com.marwinxxii.reactiveui.activity;

import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;

import com.marwinxxii.reactiveui.R;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setTitle(R.string.activity_main_title);
    }

    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            addPreferencesFromResource(R.xml.main);
        }
    }
}
