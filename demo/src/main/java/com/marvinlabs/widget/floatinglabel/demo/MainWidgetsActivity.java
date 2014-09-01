package com.marvinlabs.widget.floatinglabel.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.marvinlabs.widget.floatinglabel.instantpicker.DatePickerFragment;
import com.marvinlabs.widget.floatinglabel.instantpicker.FloatingLabelDatePicker;
import com.marvinlabs.widget.floatinglabel.instantpicker.FloatingLabelInstantPicker;
import com.marvinlabs.widget.floatinglabel.instantpicker.FloatingLabelTimePicker;
import com.marvinlabs.widget.floatinglabel.instantpicker.Instant;
import com.marvinlabs.widget.floatinglabel.instantpicker.InstantPickerListener;
import com.marvinlabs.widget.floatinglabel.instantpicker.JavaDateInstant;
import com.marvinlabs.widget.floatinglabel.instantpicker.JavaTimeInstant;
import com.marvinlabs.widget.floatinglabel.instantpicker.TimePickerFragment;
import com.marvinlabs.widget.floatinglabel.itempicker.FloatingLabelItemPicker;
import com.marvinlabs.widget.floatinglabel.itempicker.ItemPickerListener;
import com.marvinlabs.widget.floatinglabel.itempicker.StringPickerDialogFragment;
import com.marvinlabs.widget.slideshow.demo.R;

import java.util.ArrayList;
import java.util.Arrays;


public class MainWidgetsActivity extends FragmentActivity implements ItemPickerListener<String>, InstantPickerListener {

    FloatingLabelItemPicker<String> picker1;
    FloatingLabelItemPicker<String> picker2;
    FloatingLabelTimePicker<JavaTimeInstant> timePicker;
    FloatingLabelDatePicker<JavaDateInstant> datePicker;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity layout
        setContentView(R.layout.activity_main_widgets);

        // Spinners
        picker1 = (FloatingLabelItemPicker<String>) findViewById(R.id.picker1);
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
                itemPicker1.show(getSupportFragmentManager(), "ItemPicker1");
            }
        });

        picker2 = (FloatingLabelItemPicker<String>) findViewById(R.id.picker2);
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
                itemPicker2.show(getSupportFragmentManager(), "ItemPicker2");
            }
        });

        // Instant pickers
        datePicker = (FloatingLabelDatePicker<JavaDateInstant>) findViewById(R.id.date_picker);
        datePicker.setSelectedInstant(new JavaDateInstant());
        datePicker.setWidgetListener(new FloatingLabelInstantPicker.OnWidgetEventListener<JavaDateInstant>() {
            @Override
            public void onShowInstantPickerDialog(FloatingLabelInstantPicker<JavaDateInstant> source) {
                DatePickerFragment<JavaDateInstant> pickerFragment = DatePickerFragment.<JavaDateInstant>newInstance(source.getId(), source.getSelectedInstant());
                pickerFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        timePicker = (FloatingLabelTimePicker<JavaTimeInstant>) findViewById(R.id.time_picker);
        timePicker.setSelectedInstant(new JavaTimeInstant());
        timePicker.setWidgetListener(new FloatingLabelInstantPicker.OnWidgetEventListener<JavaTimeInstant>() {
            @Override
            public void onShowInstantPickerDialog(FloatingLabelInstantPicker<JavaTimeInstant> source) {
                TimePickerFragment pickerFragment = TimePickerFragment.<JavaTimeInstant>newInstance(source.getId(), source.getSelectedInstant());
                pickerFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });
    }

    // Implementation of the ItemPickerListener interface

    @Override
    public void onCancelled(int pickerId) {
    }

    @Override
    public void onItemsSelected(int pickerId, int[] selectedIndices) {
        if (pickerId==R.id.picker1) {
            picker1.setSelectedIndices(selectedIndices);
        } else if (pickerId==R.id.picker2) {
            picker2.setSelectedIndices(selectedIndices);
        }
    }

    // Implementation of the InstantPickerListener interface

    @Override
    public void onInstantSelected(int pickerId, Instant instant) {
        if (pickerId==R.id.date_picker) {
            datePicker.setSelectedInstant((JavaDateInstant) instant);
        } else if (pickerId==R.id.time_picker) {
            timePicker.setSelectedInstant((JavaTimeInstant) instant);
        }
    }
}
