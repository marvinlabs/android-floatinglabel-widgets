package com.marvinlabs.widget.floatinglabel.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.marvinlabs.widget.floatinglabel.FloatingLabelWidgetBase;
import com.marvinlabs.widget.floatinglabel.LabelAnimator;
import com.marvinlabs.widget.floatinglabel.R;
import com.marvinlabs.widget.floatinglabel.anim.TextViewLabelAnimator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A widget to pick one or more items from a list
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 */
public class FloatingLabelItemPicker<ItemT> extends FloatingLabelWidgetBase<TextView> {

    private static final String SAVE_STATE_KEY_SELECTED_INDICES = "saveStateSelectedIndices";

    public interface OnItemPickerWidgetEventListener<ItemT> {
        public void onShowItemPickerDialog(FloatingLabelItemPicker<ItemT> source);
    }

    /**
     * The available items
     */
    protected List<ItemT> availableItems;

    /**
     * The selected items indices within the available items
     */
    protected int[] selectedIndices;

    /**
     * Something to turn our items into strings
     */
    protected ItemPrinter<ItemT> itemPrinter;

    /**
     * The listener to notify when this widget has something to say
     */
    protected OnItemPickerWidgetEventListener<ItemT> widgetListener;

    // =============================================================================================
    // Lifecycle
    // ==

    public FloatingLabelItemPicker(Context context) {
        super(context);
    }

    public FloatingLabelItemPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingLabelItemPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // =============================================================================================
    // Overridden methods
    // ==

    @Override
    protected int getDefaultLayoutId() {
        return R.layout.flw_widget_floating_label_picker;
    }

    @Override
    protected void afterLayoutInflated(Context context, AttributeSet attrs, int defStyle) {
        final int drawableRightId;
        final int drawableLeftId;
        final int drawablePadding;

        if (attrs == null) {
            drawableLeftId =0;
            drawableRightId = R.drawable.ic_picker;
            drawablePadding=0;
        } else {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingLabelWidgetBase, defStyle, 0);

            drawableRightId = a.getResourceId(R.styleable.FloatingLabelWidgetBase_android_drawableRight, R.drawable.ic_picker);
            drawableLeftId = a.getResourceId(R.styleable.FloatingLabelWidgetBase_android_drawableLeft, 0);
            drawablePadding = a.getDimensionPixelSize(R.styleable.FloatingLabelWidgetBase_android_drawablePadding, 0);

            a.recycle();
        }

        final TextView inputWidget = getInputWidget();

        inputWidget.setCompoundDrawablesWithIntrinsicBounds(drawableLeftId, 0, drawableRightId, 0);
        inputWidget.setCompoundDrawablePadding(drawablePadding);
    }

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
        if (selectedIndices != null) {
            saveState.putIntArray(SAVE_STATE_KEY_SELECTED_INDICES, selectedIndices);
        }
    }

    @Override
    protected void restoreAdditionalInstanceState(Bundle savedState) {
        selectedIndices = savedState.getIntArray(SAVE_STATE_KEY_SELECTED_INDICES);
//        setSelectedIndices(selectedIndices);
    }

    @Override
    protected void setInitialWidgetState() {
        if (selectedIndices == null || selectedIndices.length == 0) {
            setLabelAnchored(true);
            getInputWidget().setText("");
        } else {
            setLabelAnchored(false);
            getInputWidget().setText(getItemPrinter().itemsToString(getSelectedItems()));
        }
    }

    @Override
    protected LabelAnimator<TextView> getDefaultLabelAnimator() {
        return new TextViewLabelAnimator<TextView>();
    }

    // =============================================================================================
    // Item picking
    // ==

    /**
     * Sets the items that can be selected from this widget
     *
     * @param availableItems
     */
    public void setAvailableItems(List<ItemT> availableItems) {
        this.availableItems = availableItems;
    }

    public List<ItemT> getAvailableItems() {
        return availableItems;
    }

    /**
     * Set the indices of the items currently selected
     *
     * @param indices The positions of the selected items within the available item list
     */
    public void setSelectedIndices(int[] indices) {
        selectedIndices = indices;
        onSelectedItemsChanged();
    }

    /**
     * Get the indices of the items currently selected
     *
     * @return an array of indices within the available items list
     */
    public int[] getSelectedIndices() {
        return selectedIndices;
    }

    /**
     * Get the items currently selected
     *
     * @return
     */
    public Collection<ItemT> getSelectedItems() {
        ArrayList<ItemT> items = new ArrayList<ItemT>(selectedIndices == null ? 0 : selectedIndices.length);
        for (int index : selectedIndices) {
            items.add(availableItems.get(index));
        }
        return items;
    }

    /**
     * Refreshes the widget state when the selection changes
     */
    protected void onSelectedItemsChanged() {
        if (selectedIndices == null || selectedIndices.length == 0) {
            getInputWidget().setText("");
            anchorLabel();
        } else {
            getInputWidget().setText(getItemPrinter().itemsToString(getSelectedItems()));
            floatLabel();
        }
    }

    /**
     * Show the item picker
     */
    protected void requestShowPicker() {
        if (widgetListener != null) widgetListener.onShowItemPickerDialog(this);
    }

    // =============================================================================================
    // Other methods
    // ==

    public OnItemPickerWidgetEventListener<ItemT> getWidgetListener() {
        return widgetListener;
    }

    public void setWidgetListener(OnItemPickerWidgetEventListener<ItemT> widgetListener) {
        this.widgetListener = widgetListener;
    }

    public void setItemPrinter(ItemPrinter<ItemT> itemPrinter) {
        this.itemPrinter = itemPrinter;
    }

    public ItemPrinter<ItemT> getItemPrinter() {
        if (itemPrinter == null) {
            itemPrinter = new ItemPrinter.ToStringItemPrinter<ItemT>();
        }
        return itemPrinter;
    }

    /**
     * Listen to click events on the input widget
     */
    OnClickListener inputWidgetClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("FloatingLabelPicker", "FloatingLabelPicker clicked");
            requestShowPicker();
        }
    };
}
