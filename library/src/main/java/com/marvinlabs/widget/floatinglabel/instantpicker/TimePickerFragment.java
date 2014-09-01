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
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment implements InstantPicker<TimeInstant> {

    public static final String ARG_SELECTED_INSTANT = "SelectedInstant";
    public static final String ARG_PICKER_ID = "PickerId";

    protected int pickerId;
    protected TimeInstant selectedInstant;

    protected ArrayList<InstantPickerListener<TimeInstant>> listeners = new ArrayList<InstantPickerListener<TimeInstant>>();

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
    public static TimePickerFragment newInstance(int pickerId, TimeInstant selectedInstant) {
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
            addListener((InstantPickerListener<TimeInstant>) activity);
        }

        if (getParentFragment() instanceof ItemPickerListener) {
            addListener((InstantPickerListener<TimeInstant>) getParentFragment());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onDetach() {
        if (getActivity() instanceof ItemPickerListener) {
            removeListener((InstantPickerListener<TimeInstant>) getActivity());
        }

        if (getParentFragment() instanceof ItemPickerListener) {
            removeListener((InstantPickerListener<TimeInstant>) getParentFragment());
        }

        // Persist the new selected items in the arguments
        getArguments().putParcelable(ARG_SELECTED_INSTANT, getSelectedInstant());

        super.onDetach();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        readArguments();

        int hour = selectedInstant.hourOfDay;
        int minute = selectedInstant.minuteOfHour;

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
        setSelectedInstant((TimeInstant) args.getParcelable(ARG_SELECTED_INSTANT));

        if (selectedInstant==null) {
            selectedInstant = TimeInstant.fromCalendar(new GregorianCalendar());
        }
    }

    // =============================================================================================
    // Dialog listeners
    // ==

    @Override
    public void addListener(InstantPickerListener<TimeInstant> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InstantPickerListener<TimeInstant> listener) {
        listeners.remove(listener);
    }

    protected void notifyInstantSelected() {
        for (InstantPickerListener<TimeInstant> listener : listeners) {
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
    public void setSelectedInstant(TimeInstant instant) {
        this.selectedInstant = instant;
    }

    @Override
    public TimeInstant getSelectedInstant() {
        return selectedInstant;
    }

    @Override
    public boolean isSelectionEmpty() {
        return selectedInstant == null;
    }
}