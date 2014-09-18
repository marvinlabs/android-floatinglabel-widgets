package com.marvinlabs.widget.floatinglabel.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.itempicker.FloatingLabelItemPicker;
import com.marvinlabs.widget.floatinglabel.itempicker.ItemPickerListener;
import com.marvinlabs.widget.floatinglabel.itempicker.StringPickerDialogFragment;
import com.marvinlabs.widget.slideshow.demo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class ItemWidgetsFragment extends Fragment implements ItemPickerListener<String>, FloatingLabelItemPicker.OnItemPickerEventListener<String> {

    FloatingLabelItemPicker<String> picker1;
    FloatingLabelItemPicker<String> picker2;

    public static ItemWidgetsFragment newInstance() {
        return new ItemWidgetsFragment();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item_widgets, null, false);


        // Spinners
        picker1 = (FloatingLabelItemPicker<String>) root.findViewById(R.id.picker1);
        picker1.setItemPickerListener(this);
        picker1.setAvailableItems(new ArrayList<String>(Arrays.asList("Item 1.1", "Item 1.2", "Item 1.3", "Item 1.4", "Item 1.5", "Item 1.6", "Item 1.7", "Item 1.8")));
        picker1.setWidgetListener(new FloatingLabelItemPicker.OnWidgetEventListener<String>() {
            @Override
            public void onShowItemPickerDialog(FloatingLabelItemPicker<String> source) {
                StringPickerDialogFragment itemPicker1 = StringPickerDialogFragment.newInstance(
                        source.getId(),
                        "Picker 1",
                        "OK", "Cancel",
                        true,
                        source.getSelectedIndices(),
                        new ArrayList<String>(source.getAvailableItems()));
                itemPicker1.setTargetFragment(ItemWidgetsFragment.this, 0);
                itemPicker1.show(getChildFragmentManager(), "ItemPicker1");
            }
        });

        picker2 = (FloatingLabelItemPicker<String>) root.findViewById(R.id.picker2);
        picker2.setItemPickerListener(this);
        picker2.setAvailableItems(new ArrayList<String>(Arrays.asList("Item 2.1", "Item 2.2", "Item 2.3", "Item 2.4")));
        picker2.setWidgetListener(new FloatingLabelItemPicker.OnWidgetEventListener<String>() {
            @Override
            public void onShowItemPickerDialog(FloatingLabelItemPicker source) {
                StringPickerDialogFragment itemPicker2 = StringPickerDialogFragment.newInstance(
                        source.getId(),
                        "Picker 2",
                        "OK", "Cancel",
                        false,
                        source.getSelectedIndices(),
                        new ArrayList<String>(source.getAvailableItems()));
                itemPicker2.setTargetFragment(ItemWidgetsFragment.this, 0);
                itemPicker2.show(getChildFragmentManager(), "ItemPicker2");
            }
        });

        return root;
    }

    // Implementation of the OnItemPickerEventListener interface

    @Override
    public void onSelectionChanged(FloatingLabelItemPicker<String> source, Collection<String> selectedItems) {
        Toast.makeText(getActivity(), source.getItemPrinter().printCollection(selectedItems), Toast.LENGTH_SHORT).show();
    }

    // Implementation of the InstantPickerListener interface

    @Override
    public void onCancelled(int pickerId) {
    }

    @Override
    public void onItemsSelected(int pickerId, int[] selectedIndices) {
        if (pickerId == R.id.picker1) {
            picker1.setSelectedIndices(selectedIndices);
        } else if (pickerId == R.id.picker2) {
            picker2.setSelectedIndices(selectedIndices);
        }
    }
}
