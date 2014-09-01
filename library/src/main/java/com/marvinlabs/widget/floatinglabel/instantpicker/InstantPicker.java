package com.marvinlabs.widget.floatinglabel.instantpicker;

import android.os.Parcelable;

/**
 * Something that allows the user to pick an instant (date/time). This could be a dialog, ...
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 *
 * @param <InstantT> The type of instant that can be picked
 */
public interface InstantPicker<InstantT extends Instant & Parcelable> {

    /**
     * Get the unique ID for this picker
     * @return
     */
    public int getPickerId();

    /**
     * Set the instant that is initially selected by the user
     *
     * @param instant The instant
     */
    public void setSelectedInstant(InstantT instant);

    /**
     * Get the instant currently selected
     *
     * @return an instant
     */
    public InstantT getSelectedInstant();

    /**
     * Returns true if no instant has been picked
     *
     * @return
     */
    public boolean isSelectionEmpty();

    /**
     * Set the listener that will receive events
     *
     * @param listener The listener
     */
    public void addListener(InstantPickerListener<InstantT> listener);

    /**
     * Unset a listener
     *
     * @param listener The listener
     */
    public void removeListener(InstantPickerListener<InstantT> listener);
}