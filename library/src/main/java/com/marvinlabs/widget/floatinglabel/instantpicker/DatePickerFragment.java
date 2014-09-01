package com.marvinlabs.widget.floatinglabel.instantpicker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.marvinlabs.widget.floatinglabel.itempicker.ItemPickerListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment implements InstantPicker<DateInstant> {

    public static final String ARG_SELECTED_INSTANT = "SelectedInstant";
    public static final String ARG_PICKER_ID = "PickerId";

    protected int pickerId;
    protected DateInstant selectedInstant;

    protected ArrayList<InstantPickerListener<DateInstant>> listeners = new ArrayList<InstantPickerListener<DateInstant>>();

    // =============================================================================================
    // Factory methods
    // ==

    /**
     * Create a new date picker
     *
     * @param pickerId        The id of the item picker
     * @param selectedInstant The positions of the items already selected
     * @return The arguments bundle
     */
    public static DatePickerFragment newInstance(int pickerId, DateInstant selectedInstant) {
        DatePickerFragment f = new DatePickerFragment();

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
            addListener((InstantPickerListener<DateInstant>) activity);
        }

        if (getParentFragment() instanceof ItemPickerListener) {
            addListener((InstantPickerListener<DateInstant>) getParentFragment());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onDetach() {
        if (getActivity() instanceof ItemPickerListener) {
            removeListener((InstantPickerListener<DateInstant>) getActivity());
        }

        if (getParentFragment() instanceof ItemPickerListener) {
            removeListener((InstantPickerListener<DateInstant>) getParentFragment());
        }

        // Persist the new selected items in the arguments
        getArguments().putParcelable(ARG_SELECTED_INSTANT, getSelectedInstant());

        super.onDetach();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        readArguments();

        int year = selectedInstant.year;
        int monthOfYear = selectedInstant.monthOfYear + 1; // Because of Java constants
        int dayOfMonth = selectedInstant.dayOfMonth;

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), dateSetListener, year, monthOfYear, dayOfMonth);
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            selectedInstant.setYear(year);
            selectedInstant.setMonthOfYear(monthOfYear - 1); // Because of Java constants
            selectedInstant.setDayOfMonth(dayOfMonth);
            notifyInstantSelected();
        }
    };

    protected void readArguments() {
        final Bundle args = getArguments();

        pickerId = args.getInt(ARG_PICKER_ID);
        setSelectedInstant((DateInstant) args.getParcelable(ARG_SELECTED_INSTANT));

        if (selectedInstant == null) {
            selectedInstant = DateInstant.fromCalendar(new GregorianCalendar());
        }
    }

    // =============================================================================================
    // Dialog listeners
    // ==

    @Override
    public void addListener(InstantPickerListener<DateInstant> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InstantPickerListener<DateInstant> listener) {
        listeners.remove(listener);
    }

    protected void notifyInstantSelected() {
        for (InstantPickerListener<DateInstant> listener : listeners) {
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
    public void setSelectedInstant(DateInstant instant) {
        this.selectedInstant = instant;
    }

    @Override
    public DateInstant getSelectedInstant() {
        return selectedInstant;
    }

    @Override
    public boolean isSelectionEmpty() {
        return selectedInstant == null;
    }
}