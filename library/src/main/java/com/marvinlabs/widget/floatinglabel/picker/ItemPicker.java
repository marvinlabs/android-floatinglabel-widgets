package com.marvinlabs.widget.floatinglabel.picker;

/**
 * Something that allows the user to pick one or more items. This could be a dialog, ...
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 *
 * @param <ItemT> The type of item that can be picked
 */
public interface ItemPicker<ItemT> {

    /**
     * Get the unique ID for this picker
     * @return
     */
    public int getPickerId();

    /**
     * Set the items that are initially selected by the user
     *
     * @param itemIndices The indices of the selected items
     */
    public void setSelectedItems(int[] itemIndices);

    /**
     * Get the indices of the items currently selected
     *
     * @return an array of indices within the available items list
     */
    public int[] getSelectedIndices();

    /**
     * Returns true if no item has been picked
     *
     * @return
     */
    public boolean isSelectionEmpty();

    /**
     * Set the listener that will receive events
     *
     * @param listener The listener
     */
    public void addListener(ItemPickerListener<ItemT> listener);

    /**
     * Unset a listener
     *
     * @param listener The listener
     */
    public void removeListener(ItemPickerListener<ItemT> listener);
}