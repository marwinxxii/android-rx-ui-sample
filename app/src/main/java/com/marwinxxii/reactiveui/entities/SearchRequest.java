package com.marwinxxii.reactiveui.entities;

public class SearchRequest {
    private final DealType deal;
    private final PropertyType property;
    private final PriceRange priceRange;

    public SearchRequest(DealType deal, PropertyType property, PriceRange priceRange) {
        this.deal = deal;
        this.property = property;
        this.priceRange = priceRange;
    }

    public DealType getDeal() {
        return deal;
    }

    public PropertyType getProperty() {
        return property;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    @Override
    public String toString() {
        return /*"SearchRequest{" +*/
            "deal=" + deal +
            ", prop=" + property +
            ", priceRange=" + priceRange +
            '}';
    }
}
