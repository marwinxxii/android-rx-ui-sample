package com.marwinxxii.reactiveui.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

public class DebouncingTextWatcher implements TextWatcher {
    private final Callback1<CharSequence> callback;
    private final long debounceInterval;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable notifyTextChangedRunnable = new Runnable() {
        @Override
        public void run() {
            notifyQueued = false;
            callback.call(lastText);
            lastText = null;
        }
    };

    private boolean notifyQueued = false;
    private CharSequence lastText;

    public DebouncingTextWatcher(long debounceInterval, Callback1<CharSequence> callback) {
        this.callback = callback;
        this.debounceInterval = debounceInterval;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        lastText = s;
        if (!notifyQueued) {
            notifyQueued = handler.postDelayed(notifyTextChangedRunnable, debounceInterval);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
