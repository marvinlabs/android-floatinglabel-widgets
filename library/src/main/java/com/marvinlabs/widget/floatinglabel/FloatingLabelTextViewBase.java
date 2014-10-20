package com.marvinlabs.widget.floatinglabel;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 */
public abstract class FloatingLabelTextViewBase<InputWidgetT extends TextView> extends FloatingLabelWidgetBase<InputWidgetT> {

    // =============================================================================================
    // Lifecycle
    // ==

    public FloatingLabelTextViewBase(Context context) {
        super(context, null, 0);
    }

    public FloatingLabelTextViewBase(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public FloatingLabelTextViewBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void afterLayoutInflated(Context context, AttributeSet attrs, int defStyle) {
        super.afterLayoutInflated(context, attrs, defStyle);

        // Load custom attributes
        final int drawableRightId;
        final int drawableLeftId;
        final int drawablePadding;
        final int inputWidgetTextColor;
        final float inputWidgetTextSize;

        if (attrs == null) {
            inputWidgetTextColor = 0xaa000000;
            inputWidgetTextSize = getResources().getDimensionPixelSize(R.dimen.flw_defaultInputWidgetTextSize);
            drawableLeftId = getDefaultDrawableLeftResId();
            drawableRightId = getDefaultDrawableRightResId();
            drawablePadding = 0;
        } else {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingLabelTextViewBase, defStyle, 0);
            drawableRightId = a.getResourceId(R.styleable.FloatingLabelTextViewBase_android_drawableRight, getDefaultDrawableRightResId());
            drawableLeftId = a.getResourceId(R.styleable.FloatingLabelTextViewBase_android_drawableLeft, getDefaultDrawableLeftResId());
            drawablePadding = a.getDimensionPixelSize(R.styleable.FloatingLabelTextViewBase_android_drawablePadding, 0);
            inputWidgetTextColor = a.getColor(R.styleable.FloatingLabelTextViewBase_flw_inputWidgetTextColor, 0xaa000000);
            inputWidgetTextSize = a.getDimension(R.styleable.FloatingLabelTextViewBase_flw_inputWidgetTextSize, getResources().getDimensionPixelSize(R.dimen.flw_defaultInputWidgetTextSize));
            a.recycle();
        }

        final TextView inputWidget = getInputWidget();
        inputWidget.setCompoundDrawablesWithIntrinsicBounds(drawableLeftId, 0, drawableRightId, 0);
        inputWidget.setCompoundDrawablePadding(drawablePadding);
        inputWidget.setTextColor(inputWidgetTextColor);
        inputWidget.setTextSize(TypedValue.COMPLEX_UNIT_PX, inputWidgetTextSize);
    }

    protected int getDefaultDrawableLeftResId() {
        return 0;
    }

    protected int getDefaultDrawableRightResId() {
        return 0;
    }
}
