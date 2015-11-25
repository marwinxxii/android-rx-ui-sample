package com.marwinxxii.reactiveui.network;

public final class NetworkHelper {
    private NetworkHelper() {
        throw new AssertionError("No instances");
    }

    public static RealtyApi provideApi() {
        return new ApiStub();
    }
}
