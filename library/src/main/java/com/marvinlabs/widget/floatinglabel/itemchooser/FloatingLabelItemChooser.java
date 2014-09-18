package com.marvinlabs.widget.floatinglabel.itemchooser;

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

/**
 * A widget to choose and hold an item
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 */
public class FloatingLabelItemChooser<ItemT extends Parcelable> extends FloatingLabelTextViewBase<TextView> {

    private static final String SAVE_STATE_KEY_SELECTED_ITEM = "saveStateSelectedItem";

    public interface OnWidgetEventListener<ItemT extends Parcelable> {
        public void onShowItemChooser(FloatingLabelItemChooser<ItemT> source);
    }

    public interface OnItemChooserEventListener<ItemT extends Parcelable> {
        public void onSelectionChanged(FloatingLabelItemChooser<ItemT> source, ItemT selectedItem);
    }

    /**
     * The selected items indices within the available items
     */
    protected ItemT selectedItem;

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
    protected OnItemChooserEventListener<ItemT> itemChooserListener;

    // =============================================================================================
    // Lifecycle
    // ==

    public FloatingLabelItemChooser(Context context) {
        super(context);
    }

    public FloatingLabelItemChooser(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingLabelItemChooser(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // =============================================================================================
    // Overridden methods
    // ==

    @Override
    protected int getDefaultLayoutId() {
        return R.layout.flw_widget_floating_label_item_chooser;
    }

    @Override
    protected int getDefaultDrawableRightResId() {
        return R.drawable.ic_chooser;
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
        if (selectedItem != null) {
            saveState.putParcelable(SAVE_STATE_KEY_SELECTED_ITEM, selectedItem);
        }
    }

    @Override
    protected void restoreAdditionalInstanceState(Bundle savedState) {
        selectedItem = savedState.getParcelable(SAVE_STATE_KEY_SELECTED_ITEM);
    }

    @Override
    protected void setInitialWidgetState() {
        if (selectedItem == null) {
            setLabelAnchored(true);
            getInputWidget().setText("");
        } else {
            setLabelAnchored(false);
            getInputWidget().setText(getItemPrinter().print(getSelectedItem()));
        }
    }

    @Override
    protected LabelAnimator<TextView> getDefaultLabelAnimator() {
        return new TextViewLabelAnimator<TextView>();
    }

    // =============================================================================================
    // Item choosing
    // ==

    /**
     * Set the item currently selected
     *
     * @param item The item selected by the user
     */
    public void setSelectedItem(ItemT item) {
        selectedItem = item;
        onSelectedItemChanged();
    }

    /**
     * Get the item currently selected
     *
     * @return The item selected by the user
     */
    public ItemT getSelectedItem() {
        return selectedItem;
    }

    /**
     * Refreshes the widget state when the selection changes
     */
    protected void onSelectedItemChanged() {
        if (selectedItem == null) {
            anchorLabel();
            getInputWidget().setText("");
        } else {
            getInputWidget().setText(getItemPrinter().print(selectedItem));
            floatLabel();
        }

        if (itemChooserListener != null) itemChooserListener.onSelectionChanged(this, selectedItem);
    }

    /**
     * Show the item picker
     */
    protected void requestShowPicker() {
        if (widgetListener != null) widgetListener.onShowItemChooser(this);
    }

    // =============================================================================================
    // Other methods
    // ==

    public OnItemChooserEventListener<ItemT> getItemChooserListener() {
        return itemChooserListener;
    }

    public void setItemChooserListener(OnItemChooserEventListener<ItemT> itemChooserListener) {
        this.itemChooserListener = itemChooserListener;
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
