package com.marwinxxii.reactiveui.network;

/**
 * @author Alexey Agapitov <agapitov@yandex-team.ru> on 06.09.2015
 */
public class SearchRequest {
    private final DealType deal;
    private final PropertyType property;
    private final int price;

    public SearchRequest(DealType deal, PropertyType property, int price) {
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

    public int getPrice() {
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
