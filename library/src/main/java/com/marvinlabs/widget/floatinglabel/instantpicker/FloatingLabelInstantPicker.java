package com.marvinlabs.widget.floatinglabel.instantpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.marvinlabs.widget.floatinglabel.FloatingLabelWidgetBase;
import com.marvinlabs.widget.floatinglabel.LabelAnimator;
import com.marvinlabs.widget.floatinglabel.R;
import com.marvinlabs.widget.floatinglabel.anim.TextViewLabelAnimator;

/**
 * A widget to pick one or more items from a list
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 */
public abstract class FloatingLabelInstantPicker<InstantT extends Instant & Parcelable> extends FloatingLabelWidgetBase<TextView> {

    private static final String SAVE_STATE_KEY_INSTANT = "saveStateInstant";

    public interface OnWidgetEventListener<InstantT extends Instant & Parcelable> {
        public void onShowInstantPickerDialog(FloatingLabelInstantPicker<InstantT> source);
    }

    /**
     * The selected instant
     */
    protected InstantT selectedInstant;

    /**
     * Something to turn our instant into a string
     */
    protected InstantPrinter instantPrinter;

    /**
     * The listener to notify when this widget has something to say
     */
    protected OnWidgetEventListener<InstantT> widgetListener;

    // =============================================================================================
    // Lifecycle
    // ==

    public FloatingLabelInstantPicker(Context context) {
        super(context);
    }

    public FloatingLabelInstantPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingLabelInstantPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // =============================================================================================
    // Overridden methods
    // ==

    @Override
    protected int getDefaultLayoutId() {
        return R.layout.flw_widget_floating_label_instant_picker;
    }

    @Override
    protected void afterLayoutInflated(Context context, AttributeSet attrs, int defStyle) {
        final int drawableRightId;
        final int drawableLeftId;
        final int drawablePadding;

        if (attrs == null) {
            drawableLeftId = 0;
            drawableRightId = getDefaultIconResId();
            drawablePadding = 0;
        } else {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingLabelWidgetBase, defStyle, 0);

            drawableRightId = a.getResourceId(R.styleable.FloatingLabelWidgetBase_android_drawableRight, getDefaultIconResId());
            drawableLeftId = a.getResourceId(R.styleable.FloatingLabelWidgetBase_android_drawableLeft, 0);
            drawablePadding = a.getDimensionPixelSize(R.styleable.FloatingLabelWidgetBase_android_drawablePadding, 0);

            a.recycle();
        }

        final TextView inputWidget = getInputWidget();

        inputWidget.setCompoundDrawablesWithIntrinsicBounds(drawableLeftId, 0, drawableRightId, 0);
        inputWidget.setCompoundDrawablePadding(drawablePadding);
    }

    protected abstract int getDefaultIconResId();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        getInputWidget().setClickable(true);
        getInputWidget().setOnClickListener(inputWidgetClickListener);
    }

    @Override
    protected void restoreInputWidgetState(Parcelable inputWidgetState) {
        getInputWidget().onRestoreInstanceState(inputWidgetState);
    }

    @Override
    protected Parcelable saveInputWidgetInstanceState() {
        return getInputWidget().onSaveInstanceState();
    }

    @Override
    protected void putAdditionalInstanceState(Bundle saveState) {
        if (selectedInstant != null) {
            saveState.putParcelable(SAVE_STATE_KEY_INSTANT, selectedInstant);
        }
    }

    @Override
    protected void restoreAdditionalInstanceState(Bundle savedState) {
        selectedInstant = savedState.getParcelable(SAVE_STATE_KEY_INSTANT);
    }

    @Override
    protected void setInitialWidgetState() {
        if (selectedInstant == null) {
            setLabelAnchored(true);
            getInputWidget().setText("");
        } else {
            setLabelAnchored(false);
            getInputWidget().setText(getInstantPrinter().print(getSelectedInstant()));
        }
    }

    @Override
    protected LabelAnimator<TextView> getDefaultLabelAnimator() {
        return new TextViewLabelAnimator<TextView>();
    }

    // =============================================================================================
    // Instant picking
    // ==

    /**
     * Set the instant currently selected
     *
     * @param i The new selected instant
     */
    public void setSelectedInstant(InstantT i) {
        this.selectedInstant = i;
        onSelectedInstantChanged();
    }

    /**
     * Get the selected instant
     *
     * @return the instant (date or time)
     */
    public InstantT getSelectedInstant() {
        return selectedInstant;
    }

    /**
     * Refreshes the widget state when the selection changes
     */
    protected void onSelectedInstantChanged() {
        if (selectedInstant == null) {
            getInputWidget().setText("");
            anchorLabel();
        } else {
            getInputWidget().setText(getInstantPrinter().print(getSelectedInstant()));
            floatLabel();
        }
    }

    /**
     * Show the item picker
     */
    protected void requestShowPicker() {
        if (widgetListener != null) widgetListener.onShowInstantPickerDialog(this);
    }

    // =============================================================================================
    // Other methods
    // ==

    public OnWidgetEventListener<InstantT> getWidgetListener() {
        return widgetListener;
    }

    public void setWidgetListener(OnWidgetEventListener<InstantT> widgetListener) {
        this.widgetListener = widgetListener;
    }

    public void setInstantPrinter(InstantPrinter<InstantT> instantPrinter) {
        this.instantPrinter = instantPrinter;
    }

    public InstantPrinter<InstantT> getInstantPrinter() {
        if (instantPrinter == null) {
            instantPrinter = getDefaultInstantPrinter();
        }
        return instantPrinter;
    }

    protected abstract InstantPrinter<InstantT> getDefaultInstantPrinter();

    /**
     * Listen to click events on the input widget
     */
    OnClickListener inputWidgetClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            requestShowPicker();
        }
    };
}
