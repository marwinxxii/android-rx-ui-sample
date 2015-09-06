package com.marwinxxii.reactiveui.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.marwinxxii.reactiveui.R;

/**
 * @author Alexey Agapitov <agapitov@yandex-team.ru> on 06.09.2015
 */
public class MainActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.activity_main_title);
        addPreferencesFromResource(R.xml.main);
    }
}
