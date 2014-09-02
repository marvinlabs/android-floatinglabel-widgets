package com.marvinlabs.widget.floatinglabel.instantpicker;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.marvinlabs.widget.floatinglabel.itempicker.ItemPickerListener;

import java.util.ArrayList;

public class TimePickerFragment<TimeInstantT extends TimeInstant> extends DialogFragment implements InstantPicker<TimeInstantT> {

    public static final String ARG_SELECTED_INSTANT = "SelectedInstant";
    public static final String ARG_PICKER_ID = "PickerId";

    protected int pickerId;
    protected TimeInstantT selectedInstant;

    protected ArrayList<InstantPickerListener<TimeInstantT>> listeners = new ArrayList<InstantPickerListener<TimeInstantT>>();

    // =============================================================================================
    // Factory methods
    // ==

    /**
     * Create a new time picker
     *
     * @param pickerId        The id of the item picker
     * @param selectedInstant The positions of the items already selected
     * @return The arguments bundle
     */
    public static <TimeInstantT extends TimeInstant> TimePickerFragment newInstance(int pickerId, TimeInstantT selectedInstant) {
        TimePickerFragment f = new TimePickerFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PICKER_ID, pickerId);
        args.putParcelable(ARG_SELECTED_INSTANT, selectedInstant);
        f.setArguments(args);

        return f;
    }

    // =============================================================================================
    // Fragment methods
    // ==

    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ItemPickerListener) {
            addListener((InstantPickerListener<TimeInstantT>) activity);
        }

        if (getParentFragment() instanceof ItemPickerListener) {
            addListener((InstantPickerListener<TimeInstantT>) getParentFragment());
        }

        if (getTargetFragment() instanceof ItemPickerListener) {
            addListener((InstantPickerListener<TimeInstantT>) getTargetFragment());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onDetach() {
        if (getActivity() instanceof ItemPickerListener) {
            removeListener((InstantPickerListener<TimeInstantT>) getActivity());
        }

        if (getParentFragment() instanceof ItemPickerListener) {
            removeListener((InstantPickerListener<TimeInstantT>) getParentFragment());
        }

        if (getTargetFragment() instanceof ItemPickerListener) {
            removeListener((InstantPickerListener<TimeInstantT>) getTargetFragment());
        }

        // Persist the new selected items in the arguments
        getArguments().putParcelable(ARG_SELECTED_INSTANT, getSelectedInstant());

        super.onDetach();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        readArguments();

        int hour = selectedInstant.getHourOfDay();
        int minute = selectedInstant.getMinuteOfHour();

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), timeSetListener, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            selectedInstant.setHourOfDay(hourOfDay);
            selectedInstant.setMinuteOfHour(minute);
            notifyInstantSelected();
        }
    };

    protected void readArguments() {
        final Bundle args = getArguments();

        pickerId = args.getInt(ARG_PICKER_ID);
        setSelectedInstant((TimeInstantT) args.getParcelable(ARG_SELECTED_INSTANT));

        if (selectedInstant == null) {
            throw new RuntimeException("Missing picker argument: selected instant");
        }
    }

    // =============================================================================================
    // Dialog listeners
    // ==

    @Override
    public void addListener(InstantPickerListener<TimeInstantT> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InstantPickerListener<TimeInstantT> listener) {
        listeners.remove(listener);
    }

    protected void notifyInstantSelected() {
        for (InstantPickerListener<TimeInstantT> listener : listeners) {
            listener.onInstantSelected(getPickerId(), getSelectedInstant());
        }
    }

    // =============================================================================================
    // Other methods
    // ==

    @Override
    public int getPickerId() {
        return pickerId;
    }

    @Override
    public void setSelectedInstant(TimeInstantT instant) {
        this.selectedInstant = instant;
    }

    @Override
    public TimeInstantT getSelectedInstant() {
        return selectedInstant;
    }

    @Override
    public boolean isSelectionEmpty() {
        return selectedInstant == null;
    }
}