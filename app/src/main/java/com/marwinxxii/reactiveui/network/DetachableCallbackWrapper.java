package com.marwinxxii.reactiveui.network;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetachableCallbackWrapper<T> implements Callback<T> {
    private Callback<T> mCallback;
    private boolean mIsDetached = false;

    public DetachableCallbackWrapper(Callback<T> callback) {
        this.mCallback = callback;
    }

    @Override
    public void success(T t, Response response) {
        if (!mIsDetached) {
            mCallback.success(t, response);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        if (!mIsDetached) {
            mCallback.failure(error);
        }
    }

    public void detach() {
        mIsDetached = true;
        mCallback = null;
    }
}
