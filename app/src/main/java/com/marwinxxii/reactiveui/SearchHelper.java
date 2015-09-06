package com.marwinxxii.reactiveui;

import android.text.TextUtils;

import com.marwinxxii.reactiveui.network.SearchRequest;
import com.marwinxxii.reactiveui.network.SearchRequest.DealType;
import com.marwinxxii.reactiveui.network.SearchRequest.PropertyType;

/**
 * @author Alexey Agapitov <agapitov@yandex-team.ru> on 06.09.2015
 */
public final class SearchHelper {
    public static final int MIN_PRICE = 5000;
    public SearchHelper() {
    }

    public static SearchRequest buildRequest(int dealTypeId, int propertyTypeId, int price) {
        return new SearchRequest(
            R.id.deal_type_buy == dealTypeId ? DealType.BUY : DealType.RENT,
            getProperty(propertyTypeId),
            price
        );
    }

    public static boolean validatePrice(CharSequence price) {
        if (TextUtils.isEmpty(price)) {
            return true;
        }
        String s = price.toString();
        try {
            int value = Integer.parseInt(s, 10);
            if (value < 0) {
                return false;
            }

            if (value < MIN_PRICE) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static int convertPrice(CharSequence value) {
        return TextUtils.isEmpty(value) ? 0 : Integer.parseInt(value.toString(), 10);
    }

    private static PropertyType getProperty(int propertyIndex) {
        switch (propertyIndex) {
            case 2:
                return PropertyType.HOUSE;
            case 1:
                return PropertyType.ROOM;
            case 0:
            default:
                return PropertyType.APT;
        }
    }

    /*public static class ValidationResult<T> {
        private static final ValidationResult sInvalidResult = new ValidationResult(null, false);

        private final T result;
        private final boolean isValid;

        private ValidationResult(T result, boolean isValid) {
            this.result = result;
            this.isValid = isValid;
        }

        public boolean isValid() {
            return isValid;
        }

        public T getResult() {
            return result;
        }

        public static <T> ValidationResult<T> invalidResult() {
            return sInvalidResult;
        }

        public static <T> ValidationResult<T> valid(T value) {
            return new ValidationResult<>(value, true);
        }
    }*/
}
