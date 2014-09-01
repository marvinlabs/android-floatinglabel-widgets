package com.marvinlabs.widget.floatinglabel.instantpicker;

import android.content.Context;
import android.util.AttributeSet;

import com.marvinlabs.widget.floatinglabel.R;

import java.text.DateFormat;

/**
 * A widget to pick one or more items from a list
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 */
public class FloatingLabelDatePicker extends FloatingLabelInstantPicker<DateInstant> {

    // =============================================================================================
    // Lifecycle
    // ==

    public FloatingLabelDatePicker(Context context) {
        super(context);
    }

    public FloatingLabelDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingLabelDatePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // =============================================================================================
    // Overridden methods
    // ==


    @Override
    protected int getDefaultIconResId() {
        return R.drawable.ic_datepicker;
    }

    @Override
    protected InstantPrinter<DateInstant> getDefaultInstantPrinter() {
        return new DatePrinter.JavaUtilPrinter(DateFormat.MEDIUM);
    }
}
