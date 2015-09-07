package com.marwinxxii.reactiveui.network;

import com.marwinxxii.reactiveui.PriceRange;

public class SearchRequest {
    private final DealType deal;
    private final PropertyType property;
    private final PriceRange price;

    public SearchRequest(DealType deal, PropertyType property, PriceRange price) {
        this.deal = deal;
        this.property = property;
        this.price = price;
    }

    public DealType getDeal() {
        return deal;
    }

    public PropertyType getProperty() {
        return property;
    }

    public PriceRange getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return /*"SearchRequest{" +*/
            "deal=" + deal +
            ", prop=" + property +
            ", price=" + price +
            '}';
    }

    public enum DealType {
        BUY, RENT
    }

    public enum PropertyType {
        APT, ROOM, HOUSE
    }
}
