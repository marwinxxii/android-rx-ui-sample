package com.marwinxxii.reactiveui.entities;

import android.support.annotation.Nullable;
import android.util.Pair;

public class PriceRange extends Pair<Integer, Integer> {
    public PriceRange(Integer from, Integer to) {
        super(from, to);
    }

    @Nullable
    public Integer getFrom() {
        return first;
    }

    @Nullable
    public Integer getTo() {
        return second;
    }

    @Override
    public String toString() {
        return "PriceRange{" + first + ", " + second + "}";
    }
}
