package com.marvinlabs.widget.floatinglabel.itempicker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.marvinlabs.widget.floatinglabel.FloatingLabelTextViewBase;
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
public class FloatingLabelItemPicker<ItemT> extends FloatingLabelTextViewBase<TextView> {

    private static final String SAVE_STATE_KEY_SELECTED_INDICES = "saveStateSelectedIndices";

    public interface OnWidgetEventListener<ItemT> {
        public void onShowItemPickerDialog(FloatingLabelItemPicker<ItemT> source);
    }

    public interface OnItemPickerEventListener<ItemT> {
        public void onSelectionChanged(FloatingLabelItemPicker<ItemT> source, Collection<ItemT> selectedItems);
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
    protected OnWidgetEventListener<ItemT> widgetListener;

    /**
     * The listener to notify when the selection changes
     */
    protected OnItemPickerEventListener<ItemT> itemPickerListener;

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
        return R.layout.flw_widget_floating_label_item_picker;
    }

    @Override
    protected int getDefaultDrawableRightResId() {
        return R.drawable.ic_picker;
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
            getInputWidget().setText(getItemPrinter().printCollection(getSelectedItems()));
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
        if (availableItems == null || selectedIndices == null || selectedIndices.length == 0) {
            return new ArrayList<ItemT>(0);
        }

        ArrayList<ItemT> items = new ArrayList<ItemT>(selectedIndices.length);
        for (int index : selectedIndices) {
            items.add(availableItems.get(index));
        }
        return items;
    }

    /**
     * Refreshes the widget state when the selection changes
     */
    protected void onSelectedItemsChanged() {
        final Collection<ItemT> selectedItems = getSelectedItems();
        if (selectedItems.isEmpty()) {
            anchorLabel();
            getInputWidget().setText("");
        } else {
            getInputWidget().setText(getItemPrinter().printCollection(selectedItems));
            floatLabel();
        }

        if (itemPickerListener!=null) itemPickerListener.onSelectionChanged(this, selectedItems);
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

    public OnItemPickerEventListener<ItemT> getItemPickerListener() {
        return itemPickerListener;
    }

    public void setItemPickerListener(OnItemPickerEventListener<ItemT> itemPickerListener) {
        this.itemPickerListener = itemPickerListener;
    }

    public OnWidgetEventListener<ItemT> getWidgetListener() {
        return widgetListener;
    }

    public void setWidgetListener(OnWidgetEventListener<ItemT> widgetListener) {
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
            requestShowPicker();
        }
    };
}
