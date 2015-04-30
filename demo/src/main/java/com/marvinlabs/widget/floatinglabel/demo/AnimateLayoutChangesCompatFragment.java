package com.marvinlabs.widget.floatinglabel.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.autocomplete.FloatingLabelAutoCompleteTextView;
import com.marvinlabs.widget.floatinglabel.demo.chooser.ItemChooserActivity;
import com.marvinlabs.widget.floatinglabel.demo.chooser.Product;
import com.marvinlabs.widget.floatinglabel.edittext.FloatingLabelEditText;
import com.marvinlabs.widget.floatinglabel.instantpicker.DatePickerFragment;
import com.marvinlabs.widget.floatinglabel.instantpicker.FloatingLabelDatePicker;
import com.marvinlabs.widget.floatinglabel.instantpicker.FloatingLabelInstantPicker;
import com.marvinlabs.widget.floatinglabel.instantpicker.Instant;
import com.marvinlabs.widget.floatinglabel.instantpicker.InstantPickerListener;
import com.marvinlabs.widget.floatinglabel.instantpicker.JavaDateInstant;
import com.marvinlabs.widget.floatinglabel.itemchooser.FloatingLabelItemChooser;
import com.marvinlabs.widget.floatinglabel.itempicker.FloatingLabelItemPicker;
import com.marvinlabs.widget.floatinglabel.itempicker.ItemPickerListener;
import com.marvinlabs.widget.floatinglabel.itempicker.StringPickerDialogFragment;
import com.marvinlabs.widget.slideshow.demo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class AnimateLayoutChangesCompatFragment extends Fragment implements
        FloatingLabelEditText.EditTextListener,
        FloatingLabelAutoCompleteTextView.EditTextListener,
        FloatingLabelItemChooser.OnItemChooserEventListener<Product>,
        InstantPickerListener,
        FloatingLabelInstantPicker.OnInstantPickerEventListener,
        ItemPickerListener<String>,
        FloatingLabelItemPicker.OnItemPickerEventListener<String>{

    public static final int REQUEST_CHOOSE_PRODUCT = 0x1234;

    FloatingLabelItemPicker<String> picker1;
    FloatingLabelAutoCompleteTextView autoView1;
    FloatingLabelItemChooser<Product> chooser1;
    FloatingLabelDatePicker<JavaDateInstant> datePicker;

    public static AnimateLayoutChangesCompatFragment newInstance() {
        return new AnimateLayoutChangesCompatFragment();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_animate_layout_changes_compat, null, false);


        final ViewGroup viewGroup = (ViewGroup) root.findViewById(R.id.layout);

        Button button = (Button) root.findViewById(R.id.buttonTry);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeViewGroupVisibility(viewGroup);
            }
        });


        ((FloatingLabelEditText) root.findViewById(R.id.edit_text1)).setEditTextListener(this);

        autoView1 = (FloatingLabelAutoCompleteTextView) root.findViewById(R.id.auto_text1);
        autoView1.setInputWidgetAdapter(new ArrayAdapter<String>(getActivity(), R.layout.flw_widget_dropdown_item, new String[]{"one", "two", "three"}));
        autoView1.setInputWidgetThreshold(1);
        autoView1.setEditTextListener(this);

        chooser1 = (FloatingLabelItemChooser<Product>) root.findViewById(R.id.chooser1);
        chooser1.setItemChooserListener(this);
        chooser1.setWidgetListener(new FloatingLabelItemChooser.OnWidgetEventListener<Product>() {
            @Override
            public void onShowItemChooser(FloatingLabelItemChooser<Product> source) {
                startActivityForResult(ItemChooserActivity.newIntent(getActivity()), REQUEST_CHOOSE_PRODUCT);
            }
        });

        datePicker = (FloatingLabelDatePicker<JavaDateInstant>) root.findViewById(R.id.date_picker);
        datePicker.setSelectedInstant(new JavaDateInstant());
        datePicker.setInstantPickerListener(this);
        datePicker.setWidgetListener(new FloatingLabelInstantPicker.OnWidgetEventListener<JavaDateInstant>() {
            @Override
            public void onShowInstantPickerDialog(FloatingLabelInstantPicker<JavaDateInstant> source) {
                DatePickerFragment<JavaDateInstant> pickerFragment = DatePickerFragment.<JavaDateInstant>newInstance(source.getId(), source.getSelectedInstant());
                pickerFragment.setTargetFragment(AnimateLayoutChangesCompatFragment.this, 0);
                pickerFragment.show(getChildFragmentManager(), "DatePicker");
            }
        });

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
                itemPicker1.setTargetFragment(AnimateLayoutChangesCompatFragment.this, 0);
                itemPicker1.show(getChildFragmentManager(), "ItemPicker1");
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_PRODUCT) {
            if (resultCode == Activity.RESULT_OK) {
                Product selectedProduct = data.getParcelableExtra(ItemChooserActivity.RES_SELECTED_PRODUCT);
                chooser1.setSelectedItem(selectedProduct);
                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void changeViewGroupVisibility(ViewGroup viewGroup) {
        if (viewGroup.getVisibility() == View.VISIBLE) {
            viewGroup.setVisibility(View.GONE);
        } else {
            viewGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSelectionChanged(FloatingLabelItemChooser<Product> source, Product selectedItem) {
        Toast.makeText(getActivity(), source.getItemPrinter().print(selectedItem), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTextChanged(FloatingLabelEditText source, String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTextChanged(FloatingLabelAutoCompleteTextView source, String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    // Implementation of the OnItemPickerEventListener interface

    @Override
    public void onInstantChanged(FloatingLabelInstantPicker source, Instant instant) {
        Toast.makeText(getActivity(), source.getInstantPrinter().print(instant), Toast.LENGTH_SHORT).show();
    }

    // Implementation of the InstantPickerListener interface

    @Override
    public void onCancelled(int pickerId) {
    }

    @Override
    public void onInstantSelected(int pickerId, Instant instant) {
        Log.d("InstantWidgetsFragment", "InstantWidgetsActivity - Instant selected for picker " + pickerId + " : " + instant.toString());

            datePicker.setSelectedInstant((JavaDateInstant) instant);
    }

    // Implementation of the OnItemPickerEventListener interface

    @Override
    public void onSelectionChanged(FloatingLabelItemPicker<String> source, Collection<String> selectedItems) {
        Toast.makeText(getActivity(), source.getItemPrinter().printCollection(selectedItems), Toast.LENGTH_SHORT).show();
    }

    // Implementation of the InstantPickerListener interface

    @Override
    public void onItemsSelected(int pickerId, int[] selectedIndices) {
         picker1.setSelectedIndices(selectedIndices);
    }
}
