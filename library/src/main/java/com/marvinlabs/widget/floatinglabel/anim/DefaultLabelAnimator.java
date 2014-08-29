package com.marvinlabs.widget.floatinglabel.anim;

import android.util.Log;
import android.view.View;

import com.marvinlabs.widget.floatinglabel.LabelAnimator;

/**
 * A default animator to move our label up when asked to float. This uses a scale and alpha
 * transformation too when floating.
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 */
public class DefaultLabelAnimator<InputWidgetT extends View> implements LabelAnimator<InputWidgetT> {

    public static final float ALPHA_ANCHORED = 0.8f;
    public static final float ALPHA_FLOATING = 1.0f;

    public static final float SCALE_ANCHORED = 1.0f;
    public static final float SCALE_FLOATING = 0.8f;

    private boolean isAnchored;
    private float alphaAnchored;
    private float alphaFloating;
    private float scaleAnchored;
    private float scaleFloating;

    public DefaultLabelAnimator() {
        this(ALPHA_ANCHORED, ALPHA_FLOATING, SCALE_ANCHORED, SCALE_FLOATING);
    }

    public DefaultLabelAnimator(float alphaAnchored, float alphaFloating, float scaleAnchored, float scaleFloating) {
        this.alphaAnchored = alphaAnchored;
        this.alphaFloating = alphaFloating;
        this.scaleAnchored = scaleAnchored;
        this.scaleFloating = scaleFloating;
    }

    @Override
    public boolean isAnchored() {
        return isAnchored;
    }

    @Override
    public void floatLabel(InputWidgetT inputWidget, View label) {
        if (!isAnchored) return;
        setLabelAnchored(inputWidget, label, false, true);
    }

    @Override
    public void anchorLabel(InputWidgetT inputWidget, View label) {
        if (isAnchored) return;
        setLabelAnchored(inputWidget, label, true, true);
    }

    @Override
    public void setLabelAnchored(InputWidgetT inputWidget, View label, boolean isAnchored) {
        setLabelAnchored(inputWidget, label, isAnchored, false);
    }

    private void setLabelAnchored(InputWidgetT inputWidget, View label, boolean isAnchored, boolean animateLabel) {
        this.isAnchored = isAnchored;

        final float targetAlpha = getTargetAlpha(inputWidget, label, isAnchored);
        final float targetX = getTargetX(inputWidget, label, isAnchored);
        final float targetY = getTargetY(inputWidget, label, isAnchored);
        final float targetScale = getTargetScale(inputWidget, label, isAnchored);

        if (animateLabel) {
            label.setPivotX(0);
            label.setPivotY(0);
            label.animate()
                    .alpha(targetAlpha)
                    .x(targetX)
                    .y(targetY)
                    .scaleX(targetScale)
                    .scaleY(targetScale);
        } else {
            label.setAlpha(targetAlpha);
            label.setX(targetX);
            label.setY(targetY);
            label.setPivotX(0);
            label.setPivotY(0);
            label.setScaleX(targetScale);
            label.setScaleY(targetScale);
        }

        Log.d("DefaultLabelAnimator",
                String.format("Setting label state to (x=%.0f, y=%.0f, alpha=%.1f, scale=%.1f, anchored=%d)",
                        targetX, targetY,
                        targetAlpha,
                        targetScale,
                        isAnchored ? 1 : 0));
    }

    protected float getTargetAlpha(InputWidgetT inputWidget, View label, boolean isAnchored) {
        return isAnchored ? alphaAnchored : alphaFloating;
    }

    protected float getTargetScale(InputWidgetT inputWidget, View label, boolean isAnchored) {
        return isAnchored ? scaleAnchored : scaleFloating;
    }

    protected float getTargetX(InputWidgetT inputWidget, View label, boolean isAnchored) {
        return inputWidget.getLeft() + inputWidget.getPaddingLeft();
    }

    protected float getTargetY(InputWidgetT inputWidget, View label, boolean isAnchored) {
        if (isAnchored) {
            return inputWidget.getBottom() - inputWidget.getPaddingBottom() - label.getHeight();
        } else {
            final float targetScale = getTargetScale(inputWidget, label, isAnchored);
            return inputWidget.getTop() - targetScale * label.getHeight();
        }
    }
}