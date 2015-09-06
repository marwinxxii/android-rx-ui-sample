package com.marwinxxii.reactiveui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.from(Arrays.asList(0, 1, 2))
          .delay(300L, TimeUnit.MILLISECONDS)
          .map(String::valueOf)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(s -> {
              Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
          });
    }
}
