package com.marwinxxii.reactiveui.filters;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.marwinxxii.reactiveui.R;
import com.marwinxxii.reactiveui.entities.PriceRange;
import com.marwinxxii.reactiveui.entities.SearchRequest;
import com.marwinxxii.reactiveui.entities.DealType;
import com.marwinxxii.reactiveui.entities.PropertyType;

public final class FiltersHelper {
    private FiltersHelper() {
        throw new AssertionError("No instances");
    }

    public static SearchRequest buildRequest(int dealTypeId, int propertyTypeIndex, PriceRange price) {
        return new SearchRequest(
            R.id.deal_type_buy == dealTypeId ? DealType.BUY : DealType.RENT,
            getProperty(propertyTypeIndex),
            price
        );
    }

    public static boolean validatePrice(CharSequence price) {
        try {
            convertPrice(price);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static Integer convertPrice(CharSequence value) {
        return TextUtils.isEmpty(value) ? null : Integer.parseInt(value.toString(), 10);
    }

    @Nullable
    public static PriceRange processPriceRange(Integer from, Integer to, FiltersView filters) {
        if (from == null || to == null || from <= to) {
            filters.setPriceFromErrorVisible(false);
            filters.setPriceToErrorVisible(false);
            return new PriceRange(from, to);
        } else {
            boolean fromGreater = from > to;
            filters.setPriceFromErrorVisible(fromGreater);
            filters.setPriceToErrorVisible(!fromGreater);
            return null;
        }
    }

    public static void setOffersCount(TextView offersView, Integer count) {
        String text = offersView.getContext().getString(R.string.offers_found, count);
        offersView.setText(text);
        offersView.setVisibility(View.VISIBLE);
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
}
