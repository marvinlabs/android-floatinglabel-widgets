package com.marvinlabs.widget.floatinglabel;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.marvinlabs.widget.floatinglabel.anim.DefaultLabelAnimator;

/**
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 */
public abstract class FloatingLabelWidgetBase<InputWidgetT extends View> extends FrameLayout {

    private static final String SAVE_STATE_KEY_LABEL = "saveStateLabel";
    private static final String SAVE_STATE_KEY_PARENT = "saveStateParent";
    private static final String SAVE_STATE_KEY_INPUT_WIDGET = "saveStateInputWidget";

    private static final String SAVE_STATE_TAG = "saveStateTag";

    /**
     * true when the view has gone through at least one layout pass
     */
    private boolean isLaidOut = false;

    /**
     * When init is complete, child views can no longer be added
     */
    private boolean initCompleted = false;

    /**
     * Reference to the TextView used as the label
     */
    private TextView floatingLabel;

    /**
     * LabelAnimator that animates the appearance and disappearance of the label TextView
     */
    private LabelAnimator<InputWidgetT> labelAnimator;

    /**
     * Holds saved state if any is waiting to be restored
     */
    private Bundle savedState;

    /**
     * The input widget
     */
    private InputWidgetT inputWidget;

    // =============================================================================================
    // Lifecycle
    // ==

    public FloatingLabelWidgetBase(Context context) {
        this(context, null, 0);
    }

    public FloatingLabelWidgetBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingLabelWidgetBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int childLeft = getPaddingLeft();
        final int childRight = right - left - getPaddingRight();

        int childTop = getPaddingTop();
        final int childBottom = bottom - top - getPaddingBottom();

        layoutChild(floatingLabel, childLeft, childTop, childRight, childBottom);
        layoutChild(getInputWidget(), childLeft, childTop + floatingLabel.getMeasuredHeight(), childRight, childBottom);
    }

    private void layoutChild(View child, int parentLeft, int parentTop, int parentRight, int parentBottom) {
        if (child.getVisibility() != GONE) {
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            int childLeft;
            final int childTop = parentTop + lp.topMargin;

            int gravity = lp.gravity;
            if (gravity == -1) {
                gravity = Gravity.TOP | Gravity.START;
            }

            final int layoutDirection;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutDirection = LAYOUT_DIRECTION_LTR;
            } else {
                layoutDirection = getLayoutDirection();
            }

            final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);

            switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                case Gravity.CENTER_HORIZONTAL:
                    childLeft = parentLeft + (parentRight - parentLeft - width) / 2 + lp.leftMargin - lp.rightMargin;
                    break;
                case Gravity.RIGHT:
                    childLeft = parentRight - width - lp.rightMargin;
                    break;
                case Gravity.LEFT:
                default:
                    childLeft = parentLeft + lp.leftMargin;
            }

            child.layout(childLeft, childTop, childLeft + width, childTop + height);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Restore any state that's been pending before measuring
        if (savedState != null) {
            Parcelable childState = savedState.getParcelable(SAVE_STATE_KEY_LABEL);
            floatingLabel.onRestoreInstanceState(childState);

            childState = savedState.getParcelable(SAVE_STATE_KEY_INPUT_WIDGET);
            // Because View#onRestoreInstanceState is protected, we got to ask subclasses to do it for us
            restoreInputWidgetState(childState);

            savedState = null;
        }
        measureChild(floatingLabel, widthMeasureSpec, heightMeasureSpec);
        measureChild(getInputWidget(), widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    protected int measureHeight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        int result = 0;
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getInputWidget().getMeasuredHeight() + floatingLabel.getMeasuredHeight();
            result += getPaddingTop() + getPaddingBottom();
            result = Math.max(result, getSuggestedMinimumHeight());

            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    protected int measureWidth(int widthMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        int result = 0;
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = Math.max(getInputWidget().getMeasuredWidth(), floatingLabel.getMeasuredWidth());
            result = Math.max(result, getSuggestedMinimumWidth());
            result += getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    // =============================================================================================
    // State
    // ==

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle savedState = (Bundle) state;
            if (savedState.getBoolean(SAVE_STATE_TAG, false)) {
                // Save our state for later since children will have theirs restored after this
                // and having more than one FloatLabel in an Activity or Fragment means you have
                // multiple views of the same ID
                this.savedState = savedState;

                restoreAdditionalInstanceState(savedState);

                super.onRestoreInstanceState(savedState.getParcelable(SAVE_STATE_KEY_PARENT));
                return;
            }
        }

        super.onRestoreInstanceState(state);
    }

    /**
     * Give the opportunity to child classes to restore additional state variables they had saved
     *
     * @param savedState The state of the floating label widget
     */
    protected void restoreAdditionalInstanceState(Bundle savedState) {
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();

        final Bundle saveState = new Bundle();
        saveState.putParcelable(SAVE_STATE_KEY_INPUT_WIDGET, saveInputWidgetInstanceState());
        saveState.putParcelable(SAVE_STATE_KEY_LABEL, floatingLabel.onSaveInstanceState());
        saveState.putParcelable(SAVE_STATE_KEY_PARENT, superState);
        saveState.putBoolean(SAVE_STATE_TAG, true);

        putAdditionalInstanceState(saveState);

        return saveState;
    }

    /**
     * Give the opportunity to child classes to save additional state variables
     *
     * @param saveState The state of the floating label widget
     */
    protected void putAdditionalInstanceState(Bundle saveState) {
    }

    /**
     * Restore the saved state of the input widget
     *
     * @param inputWidgetState The state of the input widget
     */
    protected void restoreInputWidgetState(Parcelable inputWidgetState) {
    }

    /**
     * Save the input widget state. Usually you will simply call the widget's onSaveInstanceState
     * method
     *
     * @return The saved input widget state
     */
    protected Parcelable saveInputWidgetInstanceState() {
        return new Bundle();
    }

    // =============================================================================================
    // Floating label
    // ==

    /**
     * Set the inital state for the label
     */
    protected void setInitialWidgetState() {
        setLabelAnchored(true);
    }

    /**
     * Delegate method for the floating label animator
     */
    public boolean isLabelAnchored() {
        return getLabelAnimator().isAnchored();
    }

    /**
     * Delegate method for the floating label animator
     */
    public void anchorLabel() {
        if (!isLaidOut) return;
        getLabelAnimator().anchorLabel(getInputWidget(), getFloatingLabel());
    }

    /**
     * Delegate method for the floating label animator
     */
    public void floatLabel() {
        if (!isLaidOut) return;
        getLabelAnimator().floatLabel(getInputWidget(), getFloatingLabel());
    }

    /**
     * Delegate method for the floating label animator
     */
    public void setLabelAnchored(boolean isAnchored) {
        if (!isLaidOut) return;
        getLabelAnimator().setLabelAnchored(getInputWidget(), getFloatingLabel(), isAnchored);
    }

    /**
     * Specifies a new LabelAnimator to handle calls to show/hide the label
     *
     * @param labelAnimator LabelAnimator to use; null causes use of the default LabelAnimator
     */
    public void setLabelAnimator(LabelAnimator labelAnimator) {
        if (labelAnimator == null) {
            this.labelAnimator = new DefaultLabelAnimator();
        } else {
            if (this.labelAnimator != null) {
                labelAnimator.setLabelAnchored(getInputWidget(), getFloatingLabel(), this.labelAnimator.isAnchored());
            }
            this.labelAnimator = labelAnimator;
        }

        if (isInEditMode()) {
            this.labelAnimator.setLabelAnchored(getInputWidget(), getFloatingLabel(), false);
        }
    }

    /**
     * Get the animator for the label
     *
     * @return
     */
    public LabelAnimator<InputWidgetT> getLabelAnimator() {
        return labelAnimator;
    }

    /**
     * The default animator to use for the label. That method is called in init so that subclasses
     * can provide their own specialized animator if appropriate.
     *
     * @return
     */
    protected LabelAnimator<InputWidgetT> getDefaultLabelAnimator() {
        return new DefaultLabelAnimator<InputWidgetT>();
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelText(int resid) {
        floatingLabel.setText(resid);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelText(CharSequence hint) {
        floatingLabel.setText(hint);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelTypeface(Typeface tf, int style) {
        floatingLabel.setTypeface(tf, style);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelTypeface(Typeface tf) {
        floatingLabel.setTypeface(tf);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelTextColor(ColorStateList colors) {
        floatingLabel.setTextColor(colors);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelColor(int color) {
        floatingLabel.setTextColor(color);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelAllCaps(boolean allCaps) {
        floatingLabel.setAllCaps(allCaps);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelTextSize(float size) {
        floatingLabel.setTextSize(size);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelTextSize(int unit, float size) {
        floatingLabel.setTextSize(unit, size);
    }

    /**
     * Delegate method for the floating label TextView
     */
    protected TextView getFloatingLabel() {
        return floatingLabel;
    }

    // =============================================================================================
    // ViewGroup overrides
    // ==

    @Override
    public void addView(View child) {
        if (initCompleted) {
            throw new UnsupportedOperationException("You cannot add child views to a FloatLabel");
        } else {
            super.addView(child);
        }
    }

    @Override
    public void addView(View child, int index) {
        if (initCompleted) {
            throw new UnsupportedOperationException("You cannot add child views to a FloatLabel");
        } else {
            super.addView(child, index);
        }
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        if (initCompleted) {
            throw new UnsupportedOperationException("You cannot add child views to a FloatLabel");
        } else {
            super.addView(child, index, params);
        }
    }

    @Override
    public void addView(View child, int width, int height) {
        if (initCompleted) {
            throw new UnsupportedOperationException("You cannot add child views to a FloatLabel");
        } else {
            super.addView(child, width, height);
        }
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        if (initCompleted) {
            throw new UnsupportedOperationException("You cannot add child views to a FloatLabel");
        } else {
            super.addView(child, params);
        }
    }

    // =============================================================================================
    // Other methods
    // ==

    /**
     * Specifies the layout to be inflated for the widget content. That layout must at least declare
     * an EditText with id "flw_floating_label" and an input widget of the proper class with id
     * "flw_input_widget"
     *
     * @return The ID of the layout to inflate and attach to this view group
     */
    protected abstract int getDefaultLayoutId();

    /**
     * Get the input widget we are using
     *
     * @return The input widget
     */
    public InputWidgetT getInputWidget() {
        return inputWidget;
    }

    /**
     * Returns the saved state of this widget
     *
     * @return
     */
    protected Bundle getSavedState() {
        return savedState;
    }

    /**
     * Initialise the widget: read attributes, inflate layout and set the basic properties
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    protected void init(Context context, AttributeSet attrs, int defStyle) {
        // Load custom attributes
        final int layoutId;
        final CharSequence floatLabelText;
        final int floatLabelTextColor;
        final float floatLabelTextSize;

        if (attrs == null) {
            layoutId = getDefaultLayoutId();
            floatLabelText = null;
            floatLabelTextColor = 0x66000000;
            floatLabelTextSize = getResources().getDimensionPixelSize(R.dimen.flw_defaultLabelTextSize);
        } else {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingLabelWidgetBase, defStyle, 0);

            layoutId = a.getResourceId(R.styleable.FloatingLabelWidgetBase_android_layout, getDefaultLayoutId());
            floatLabelText = a.getText(R.styleable.FloatingLabelWidgetBase_flw_labelText);
            floatLabelTextColor = a.getColor(R.styleable.FloatingLabelWidgetBase_flw_labelTextColor, 0x66000000);
            floatLabelTextSize = a.getDimension(R.styleable.FloatingLabelWidgetBase_flw_labelTextSize, getResources().getDimensionPixelSize(R.dimen.flw_defaultLabelTextSize));

            a.recycle();
        }

        inflateWidgetLayout(context, layoutId);

        getFloatingLabel().setFocusableInTouchMode(false);
        getFloatingLabel().setFocusable(false);

        setLabelAnimator(getDefaultLabelAnimator());
        setLabelText(floatLabelText);
        setLabelColor(floatLabelTextColor);
        setLabelTextSize(TypedValue.COMPLEX_UNIT_PX, floatLabelTextSize);

        afterLayoutInflated(context, attrs, defStyle);

        isLaidOut = false;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                isLaidOut = true;
                setInitialWidgetState();
                if (Build.VERSION.SDK_INT >= 16) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        // Mark init as complete to prevent accidentally breaking the view by
        // adding children
        initCompleted = true;
        onInitCompleted();
    }

    /**
     * Can be overriden to do something after we are done with init
     */
    protected void onInitCompleted() {
    }

    /**
     * Can be overriden to do something after the layout inflation
     */
    protected void afterLayoutInflated(Context context, AttributeSet attrs, int defStyle) {
    }

    /**
     * Inflate the widget layout and make sure we have everything in there
     *
     * @param context  The context
     * @param layoutId The id of the layout to inflate
     */
    private void inflateWidgetLayout(Context context, int layoutId) {
        inflate(context, layoutId, this);

        floatingLabel = (TextView) findViewById(R.id.flw_floating_label);
        if (floatingLabel == null) {
            throw new RuntimeException("Your layout must have a TextView whose ID is @id/flw_floating_label");
        }

        View iw = findViewById(R.id.flw_input_widget);
        if (iw == null) {
            throw new RuntimeException("Your layout must have an input widget whose ID is @id/flw_input_widget");
        }
        try {
            inputWidget = (InputWidgetT) iw;
        } catch (ClassCastException e) {
            throw new RuntimeException("The input widget is not of the expected type");
        }
    }
}
