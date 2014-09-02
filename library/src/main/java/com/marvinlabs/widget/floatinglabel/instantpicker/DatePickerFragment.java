package com.marvinlabs.widget.floatinglabel.instantpicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.marvinlabs.widget.floatinglabel.itempicker.ItemPickerListener;

public class DatePickerFragment<DateInstantT extends DateInstant> extends DialogFragment implements InstantPicker<DateInstantT> {

    public static final String ARG_SELECTED_INSTANT = "SelectedInstant";
    public static final String ARG_PICKER_ID = "PickerId";

    protected int pickerId;
    protected DateInstantT selectedInstant;

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
    public static <DateInstantT extends DateInstant> DatePickerFragment newInstance(int pickerId, DateInstantT selectedInstant) {
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
    public void onPause() {
        // Persist the new selected items in the arguments
        getArguments().putParcelable(ARG_SELECTED_INSTANT, getSelectedInstant());

        super.onPause();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        readArguments();

        int year = selectedInstant.getYear();
        int monthOfYear = selectedInstant.getMonthOfYear() + 1; // Because of Java constants
        int dayOfMonth = selectedInstant.getDayOfMonth();

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
        setSelectedInstant((DateInstantT) args.getParcelable(ARG_SELECTED_INSTANT));

        if (selectedInstant == null) {
            throw new RuntimeException("Missing picker argument: selected instant");
        }
    }

    // =============================================================================================
    // Dialog listeners
    // ==

    @SuppressWarnings("unchecked")
    protected void notifyInstantSelected() {
        if (getActivity() instanceof InstantPickerListener) {
            ((InstantPickerListener<DateInstantT>) getActivity()).onInstantSelected(getPickerId(), getSelectedInstant());
        }

        if (getParentFragment() instanceof InstantPickerListener) {
            ((InstantPickerListener<DateInstantT>) getParentFragment()).onInstantSelected(getPickerId(), getSelectedInstant());
        }

        if (getTargetFragment() instanceof InstantPickerListener) {
            ((InstantPickerListener<DateInstantT>) getTargetFragment()).onInstantSelected(getPickerId(), getSelectedInstant());
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
    public void setSelectedInstant(DateInstantT instant) {
        this.selectedInstant = instant;
    }

    @Override
    public DateInstantT getSelectedInstant() {
        return selectedInstant;
    }

    @Override
    public boolean isSelectionEmpty() {
        return selectedInstant == null;
    }
}