package com.marwinxxii.reactiveui.network;

public final class NetworkHelper {
    public static RealtyApi provideApi() {
        return new ApiStub();
    }
}
