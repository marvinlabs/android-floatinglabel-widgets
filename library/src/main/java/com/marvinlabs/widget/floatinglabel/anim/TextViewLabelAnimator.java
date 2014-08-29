package com.marvinlabs.widget.floatinglabel.anim;

import android.view.View;
import android.widget.TextView;

/**
 * A default animator to move our label up when asked to float. This uses a scale and alpha
 * transformation too when floating.
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 */
public class TextViewLabelAnimator<InputWidgetT extends TextView> extends DefaultLabelAnimator<InputWidgetT> {

    public TextViewLabelAnimator() {
        super();
    }

    public TextViewLabelAnimator(float alphaAnchored, float alphaFloating, float scaleAnchored, float scaleFloating) {
        super(alphaAnchored, alphaFloating, scaleAnchored, scaleFloating);
    }

    @Override
    protected float getTargetX(InputWidgetT inputWidget, View label, boolean isAnchored) {
        float x = inputWidget.getLeft();

        // Add left drawable size and padding when anchored
        if (isAnchored) {
            x += inputWidget.getCompoundPaddingLeft();
        } else {
            x += inputWidget.getPaddingLeft();
        }

        return x;
    }
}