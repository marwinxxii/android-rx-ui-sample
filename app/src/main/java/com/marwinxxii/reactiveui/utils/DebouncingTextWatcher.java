package com.marwinxxii.reactiveui.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

public abstract class DebouncingTextWatcher implements TextWatcher {
    private final Debouncer<CharSequence> debouncer;

    public DebouncingTextWatcher(long debounceInterval) {
        debouncer = new Debouncer<>(debounceInterval, this::onDebouncedTextChanged);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        debouncer.onValueChange(s);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public abstract void onDebouncedTextChanged(CharSequence value);

    private static class Debouncer<T> {
        private final Handler handler = new Handler(Looper.getMainLooper());
        private final long debounceInterval;
        private final Callback1<T> valueCallback;

        private boolean notifyQueued = false;
        private T lastValue;

        private final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                notifyQueued = false;
                valueCallback.call(lastValue);
                lastValue = null;
            }
        };

        public Debouncer(long debounceInterval, Callback1<T> valueCallback) {
            this.debounceInterval = debounceInterval;
            this.valueCallback = valueCallback;
        }

        private void onValueChange(T newValue) {
            lastValue = newValue;
            if (!notifyQueued) {
                notifyQueued = handler.postDelayed(runnable, debounceInterval);
            }
        }
    }
}
