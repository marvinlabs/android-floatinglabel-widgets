package com.marvinlabs.widget.floatinglabel.instantpicker;

/**
 * Listener that gets notified on picker events. If an activity implements this interface, it
 * will automatically get notified.
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 *
 * @param <InstantT> The type of instant presented in the dialog
 */
public interface InstantPickerListener<InstantT extends Instant> {

    /**
     * The dialog has been closed and the user does not want to change the selection
     *
     * @param pickerId The id of the item picker
     */
    public void onCancelled(int pickerId);

    /**
     * The dialog has been closed and items have been selected
     *
     * @param pickerId      The id of the item picker
     * @param instant The instant that has been selected
     */
    public void onInstantSelected(int pickerId, InstantT instant);

}