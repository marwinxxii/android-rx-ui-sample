package com.marwinxxii.reactiveui.filters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.*;

import com.marwinxxii.reactiveui.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FiltersView extends FrameLayout {
    @Bind(R.id.deal_type)
    RadioGroup dealType;

    @Bind(R.id.property_type)
    Spinner propertyType;

    @Bind(R.id.price_from)
    TextInputLayout priceFrom;
    
    @Bind(R.id.price_to)
    TextInputLayout priceTo;
    
    @Bind(R.id.apply)
    Button apply;

    public FiltersView(Context context) {
        super(context);
    }

    public FiltersView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FiltersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FiltersView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_filters, this, true);
        ButterKnife.bind(this);
    }

    public RadioGroup getDealTypeRadioGroup() {
        return dealType;
    }

    public Spinner getPropertyTypeSpinner() {
        return propertyType;
    }

    public TextInputLayout getPriceFrom() {
        return priceFrom;
    }

    public TextInputLayout getPriceTo() {
        return priceTo;
    }

    public Button getApplyButton() {
        return apply;
    }
}
