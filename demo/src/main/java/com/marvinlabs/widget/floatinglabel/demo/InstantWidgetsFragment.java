package com.marvinlabs.widget.floatinglabel.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.instantpicker.DatePickerFragment;
import com.marvinlabs.widget.floatinglabel.instantpicker.FloatingLabelDatePicker;
import com.marvinlabs.widget.floatinglabel.instantpicker.FloatingLabelInstantPicker;
import com.marvinlabs.widget.floatinglabel.instantpicker.FloatingLabelTimePicker;
import com.marvinlabs.widget.floatinglabel.instantpicker.Instant;
import com.marvinlabs.widget.floatinglabel.instantpicker.InstantPickerListener;
import com.marvinlabs.widget.floatinglabel.instantpicker.JavaDateInstant;
import com.marvinlabs.widget.floatinglabel.instantpicker.JavaTimeInstant;
import com.marvinlabs.widget.floatinglabel.instantpicker.TimePickerFragment;
import com.marvinlabs.widget.slideshow.demo.R;


public class InstantWidgetsFragment extends Fragment implements InstantPickerListener, FloatingLabelInstantPicker.OnInstantPickerEventListener {

    FloatingLabelTimePicker<JavaTimeInstant> timePicker;
    FloatingLabelDatePicker<JavaDateInstant> datePicker;

    public static InstantWidgetsFragment newInstance() {
        return new InstantWidgetsFragment();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_instant_widgets, null, false);

        // Instant pickers
        datePicker = (FloatingLabelDatePicker<JavaDateInstant>) root.findViewById(R.id.date_picker);
        datePicker.setSelectedInstant(new JavaDateInstant());
        datePicker.setInstantPickerListener(this);
        datePicker.setWidgetListener(new FloatingLabelInstantPicker.OnWidgetEventListener<JavaDateInstant>() {
            @Override
            public void onShowInstantPickerDialog(FloatingLabelInstantPicker<JavaDateInstant> source) {
                DatePickerFragment<JavaDateInstant> pickerFragment = DatePickerFragment.<JavaDateInstant>newInstance(source.getId(), source.getSelectedInstant());
                pickerFragment.setTargetFragment(InstantWidgetsFragment.this, 0);
                pickerFragment.show(getChildFragmentManager(), "DatePicker");
            }
        });

        timePicker = (FloatingLabelTimePicker<JavaTimeInstant>) root.findViewById(R.id.time_picker);
        timePicker.setSelectedInstant(new JavaTimeInstant());
        timePicker.setInstantPickerListener(this);
        timePicker.setWidgetListener(new FloatingLabelInstantPicker.OnWidgetEventListener<JavaTimeInstant>() {
            @Override
            public void onShowInstantPickerDialog(FloatingLabelInstantPicker<JavaTimeInstant> source) {
                TimePickerFragment pickerFragment = TimePickerFragment.<JavaTimeInstant>newInstance(source.getId(), source.getSelectedInstant());
                pickerFragment.setTargetFragment(InstantWidgetsFragment.this, 0);
                pickerFragment.show(getChildFragmentManager(), "TimePicker");
            }
        });

        return root;
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

        if (pickerId == R.id.date_picker) {
            datePicker.setSelectedInstant((JavaDateInstant) instant);
        } else if (pickerId == R.id.time_picker) {
            timePicker.setSelectedInstant((JavaTimeInstant) instant);
        }
    }
}
