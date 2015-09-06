package com.marwinxxii.reactiveui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Alexey Agapitov <agapitov@yandex-team.ru> on 06.09.2015
 */
public class FiltersView extends FrameLayout {
    @Bind(R.id.deal_type)
    RadioGroup dealType;

    @Bind(R.id.property_type)
    Spinner propertyType;

    @Bind(R.id.price)
    EditText price;

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

    public RadioGroup getDealType() {
        return dealType;
    }

    public Spinner getPropertyType() {
        return propertyType;
    }

    public EditText getPrice() {
        return price;
    }
}
