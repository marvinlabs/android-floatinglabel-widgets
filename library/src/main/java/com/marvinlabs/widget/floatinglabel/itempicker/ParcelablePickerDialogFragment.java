package com.marvinlabs.widget.floatinglabel.itempicker;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * A fragment that allows to pick items from a list of strings.
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 */
public class ParcelablePickerDialogFragment<ItemT extends Parcelable> extends AbstractPickerDialogFragment<ItemT> {

    protected ArrayList<ItemT> availableItems = null;

    // =============================================================================================
    // Factory methods
    // ==

    /**
     * Create a new instance of that picker dialog
     *
     * @param pickerId                The id of the item picker
     * @param title                   The title for the dialog
     * @param positiveButtonText      The text of the positive button
     * @param negativeButtonText      The text of the negative button
     * @param enableMultipleSelection Whether or not to allow selecting multiple items
     * @param selectedItemIndices     The positions of the items already selected
     * @param availableItems          The collection of items that can be picked
     * @return A new instance of the picker dialog
     */
    public static <T extends Parcelable> ParcelablePickerDialogFragment<T> newInstance(
            int pickerId,
            String title,
            String positiveButtonText,
            String negativeButtonText,
            boolean enableMultipleSelection,
            int[] selectedItemIndices,
            ArrayList<T> availableItems) {

        ParcelablePickerDialogFragment<T> f = new ParcelablePickerDialogFragment<T>();
        Bundle args = buildCommonArgsBundle(pickerId, title, positiveButtonText, negativeButtonText, enableMultipleSelection, selectedItemIndices);
        args.putParcelableArrayList(ARG_AVAILABLE_ITEMS, availableItems);
        f.setArguments(args);
        return f;
    }

    // =============================================================================================
    // Other methods
    // ==

    @Override
    public ArrayList<ItemT> getAvailableItems() {
        if (availableItems == null) {
            availableItems = getArguments().getParcelableArrayList(ARG_AVAILABLE_ITEMS);
            if (availableItems == null || availableItems.isEmpty()) {
                throw new RuntimeException("StringPickerDialogFragment needs some items to pick from");
            }
        }
        return availableItems;
    }
}