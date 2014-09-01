package com.marvinlabs.widget.floatinglabel.picker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that allows to pick items. This class is abstract and you need to implement concrete
 * subclasses to provide the items.
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 *
 * @param <ItemT> The type of items presented in the dialog
 */
public abstract class AbstractPickerDialogFragment<ItemT> extends DialogFragment implements ItemPicker<ItemT> {

    public static final String ARG_AVAILABLE_ITEMS = "AvailableItems";
    public static final String ARG_SELECTED_ITEMS_INDICES = "SelectedItemsIndices";
    public static final String ARG_TITLE = "Title";
    public static final String ARG_PICKER_ID = "PickerId";
    public static final String ARG_POSITIVE_BUTTON_TEXT = "PositiveButtonText";
    public static final String ARG_NEGATIVE_BUTTON_TEXT = "NegativeButtonText";
    public static final String ARG_ENABLE_MULTIPLE_SELECTION = "EnableMultipleSelection";

    protected int pickerId;
    protected String title;
    protected String positiveButtonText;
    protected String negativeButtonText;
    protected boolean enableMultipleSelection;
    protected SparseArray<ItemT> selectedItems;
    protected ItemPrinter<ItemT> itemPrinter;

    protected ArrayList<ItemPickerListener<ItemT>> listeners = new ArrayList<ItemPickerListener<ItemT>>();

    // =============================================================================================
    // Factory methods
    // ==

    /**
     * Utility method for implementations to create the base argument bundle
     *
     * @param pickerId                The id of the item picker
     * @param title                   The title for the dialog
     * @param positiveButtonText      The text of the positive button
     * @param negativeButtonText      The text of the negative button
     * @param enableMultipleSelection Whether or not to allow selecting multiple items
     * @param selectedItemIndices     The positions of the items already selected
     * @return The arguments bundle
     */
    protected static Bundle buildCommonArgsBundle(int pickerId, String title, String positiveButtonText, String negativeButtonText, boolean enableMultipleSelection, int[] selectedItemIndices) {
        Bundle args = new Bundle();
        args.putInt(ARG_PICKER_ID, pickerId);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_POSITIVE_BUTTON_TEXT, positiveButtonText);
        args.putString(ARG_NEGATIVE_BUTTON_TEXT, negativeButtonText);
        args.putBoolean(ARG_ENABLE_MULTIPLE_SELECTION, enableMultipleSelection);
        args.putIntArray(ARG_SELECTED_ITEMS_INDICES, selectedItemIndices);
        return args;
    }

    // =============================================================================================
    // Fragment methods
    // ==

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ItemPickerListener) {
            addListener((ItemPickerListener<ItemT>) activity);
        }

        if (getParentFragment() instanceof ItemPickerListener) {
            addListener((ItemPickerListener<ItemT>) getParentFragment());
        }
    }

    @Override
    public void onDetach() {
        if (getActivity() instanceof ItemPickerListener) {
            removeListener((ItemPickerListener<ItemT>) getActivity());
        }

        if (getParentFragment() instanceof ItemPickerListener) {
            removeListener((ItemPickerListener<ItemT>) getParentFragment());
        }

        // Persist the new selected items in the arguments
        getArguments().putIntArray(ARG_SELECTED_ITEMS_INDICES, getSelectedIndices());

        super.onDetach();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        readArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton(positiveButtonText, dialogButtonClickListener)
                .setNegativeButton(negativeButtonText, dialogButtonClickListener);

        if (title != null) builder.setTitle(title);

        if (enableMultipleSelection) {
            setupMultiChoiceDialog(builder);
        } else {
            setupSingleChoiceDialog(builder);
        }

        return builder.create();
    }

    protected void setupSingleChoiceDialog(AlertDialog.Builder builder) {
        final List<ItemT> availableItems = getAvailableItems();

        final ItemPrinter<ItemT> ip = getItemPrinter();
        CharSequence[] items = new CharSequence[availableItems.size()];
        for (int i = 0; i < availableItems.size(); ++i) {
            items[i] = ip.itemToString(availableItems.get(i));
        }

        int checked = -1;
        if (selectedItems.size() > 0) {
            checked = selectedItems.keyAt(0);
            selectedItems.put(checked, getItemAt(checked));
        }

        builder.setSingleChoiceItems(items, checked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItems.clear();
                selectedItems.put(which, getItemAt(which));
            }
        });
    }

    protected void setupMultiChoiceDialog(AlertDialog.Builder builder) {
        final List<ItemT> availableItems = getAvailableItems();

        final ItemPrinter<ItemT> ip = getItemPrinter();
        CharSequence[] items = new CharSequence[availableItems.size()];
        boolean[] checked = new boolean[availableItems.size()];
        for (int i = 0; i < availableItems.size(); ++i) {
            items[i] = ip.itemToString(getItemAt(i));
            if (selectedItems.get(i) != null) {
                checked[i] = true;
            } else {
                checked[i] = false;
            }
        }

        builder.setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) selectedItems.put(which, getItemAt(which));
                else selectedItems.delete(which);
            }
        });
    }

    DialogInterface.OnClickListener dialogButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                notifyItemsSelected();
            } else {
                notifyDialogCancelled();
            }
            dialog.dismiss();
        }
    };

    protected void readArguments() {
        final Bundle args = getArguments();

        pickerId = args.getInt(ARG_PICKER_ID);
        title = args.getString(ARG_TITLE);
        positiveButtonText = args.getString(ARG_POSITIVE_BUTTON_TEXT);
        negativeButtonText = args.getString(ARG_NEGATIVE_BUTTON_TEXT);
        enableMultipleSelection = args.getBoolean(ARG_ENABLE_MULTIPLE_SELECTION);

        setSelectedItems(args.getIntArray(ARG_SELECTED_ITEMS_INDICES));
    }

    // =============================================================================================
    // Dialog listeners
    // ==

    @Override
    public void addListener(ItemPickerListener<ItemT> l) {
        listeners.add(l);
    }

    @Override
    public void removeListener(ItemPickerListener<ItemT> l) {
        listeners.remove(l);
    }

    protected void notifyDialogCancelled() {
        for (ItemPickerListener<ItemT> listener : listeners) {
            listener.onCancelled(getPickerId());
        }
    }

    protected void notifyItemsSelected() {
        for (ItemPickerListener<ItemT> listener : listeners) {
            listener.onItemsSelected(getPickerId(), getSelectedIndices());
        }
    }

    // =============================================================================================
    // Other methods
    // ==

    public ItemPrinter<ItemT> getItemPrinter() {
        if (itemPrinter == null) {
            itemPrinter = new ItemPrinter.ToStringItemPrinter<ItemT>();
        }
        return itemPrinter;
    }

    @Override
    public int getPickerId() {
        return pickerId;
    }

    @Override
    public void setSelectedItems(int[] itemIndices) {
        selectedItems = new SparseArray();

        if (itemIndices != null) {
            final List<ItemT> availableItems = getAvailableItems();
            final int availableItemsCount = availableItems.size();

            for (int i : itemIndices) {
                if (i >= 0 && i < availableItemsCount) {
                    selectedItems.put(i, availableItems.get(i));
                }
            }
        }
    }

    @Override
    public int[] getSelectedIndices() {
        int[] selection = new int[selectedItems.size()];
        for (int i = 0; i < selectedItems.size(); ++i) {
            selection[i] = selectedItems.keyAt(i);
        }
        return selection;
    }

    @Override
    public boolean isSelectionEmpty() {
        return selectedItems.size() == 0;
    }

    /**
     * Get all the items that can be selected by the user
     *
     * @return
     */
    protected abstract List<ItemT> getAvailableItems();

    /**
     * Get
     *
     * @param position
     * @return
     */
    protected ItemT getItemAt(int position) {
        return getAvailableItems().get(position);
    }


}