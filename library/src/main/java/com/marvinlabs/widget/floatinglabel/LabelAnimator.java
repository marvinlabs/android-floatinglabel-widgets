package com.marvinlabs.widget.floatinglabel;

import android.view.View;

/**
 * Interface for providing custom animations to the label TextView.
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 */
public interface LabelAnimator<InputWidgetT extends View> {

    /**
     * Tells if the label is currently anchored or on the contrary is floating
     *
     * @return true if the label is anchored to the input widget
     */
    public boolean isAnchored();

    /**
     * Called when the label should be floating on top of the input widget
     *
     * @param label TextView to animate
     */
    public void floatLabel(InputWidgetT inputWidget, View label);

    /**
     * Called when the label should be anchored on top of the input widget
     *
     * @param label TextView to animate
     */
    public void anchorLabel(InputWidgetT inputWidget, View label);

    /**
     * Sets the state of the label. No need to animate here.
     *
     * @param isAnchored true if the label is initially anchored
     */
    public void setLabelAnchored(InputWidgetT inputWidget, View label, boolean isAnchored);
}