package com.marvinlabs.widget.floatinglabel.edittext;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.marvinlabs.widget.floatinglabel.FloatingLabelWidgetBase;
import com.marvinlabs.widget.floatinglabel.LabelAnimator;
import com.marvinlabs.widget.floatinglabel.R;
import com.marvinlabs.widget.floatinglabel.anim.TextViewLabelAnimator;

/**
 * An implementation of the floating label input widget for Android's EditText
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014.
 */
public class FloatingLabelEditText extends FloatingLabelWidgetBase<EditText> {

    // =============================================================================================
    // Lifecycle
    // ==

    public FloatingLabelEditText(Context context) {
        super(context);
    }

    public FloatingLabelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingLabelEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // =============================================================================================
    // Overridden methods
    // ==

    @Override
    protected void afterLayoutInflated(Context context, AttributeSet attrs, int defStyle) {
        final int inputType;
        final int drawableRightId;
        final int drawableLeftId;
        final int drawablePadding;

        if (attrs == null) {
            inputType = InputType.TYPE_CLASS_TEXT;
            drawableRightId = 0;
            drawableLeftId = 0;
            drawablePadding=0;
        } else {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingLabelEditText, defStyle, 0);

            inputType = a.getInt(R.styleable.FloatingLabelEditText_android_inputType, InputType.TYPE_CLASS_TEXT);

            a.recycle();

            a = context.obtainStyledAttributes(attrs, R.styleable.FloatingLabelWidgetBase, defStyle, 0);

            drawableRightId = a.getResourceId(R.styleable.FloatingLabelWidgetBase_android_drawableRight, 0);
            drawableLeftId = a.getResourceId(R.styleable.FloatingLabelWidgetBase_android_drawableLeft, 0);
            drawablePadding = a.getDimensionPixelSize(R.styleable.FloatingLabelWidgetBase_android_drawablePadding, 0);

            a.recycle();
        }

        final EditText inputWidget = getInputWidget();

        inputWidget.setInputType(inputType);
        inputWidget.addTextChangedListener(new EditTextWatcher());
        inputWidget.setCompoundDrawablesWithIntrinsicBounds(drawableLeftId, 0, drawableRightId, 0);
        inputWidget.setCompoundDrawablePadding(drawablePadding);
    }

    @Override
    protected int getDefaultLayoutId() {
        return R.layout.flw_widget_floating_label_edittext;
    }

    @Override
    protected void restoreInputWidgetState(Parcelable inputWidgetState) {
        getInputWidget().onRestoreInstanceState(inputWidgetState);
        // setLabelAnchored(isEditTextEmpty());
    }

    @Override
    protected Parcelable saveInputWidgetInstanceState() {
        return getInputWidget().onSaveInstanceState();
    }

    @Override
    protected void setInitialWidgetState() {
        setLabelAnchored(isEditTextEmpty());
    }

    @Override
    protected LabelAnimator<EditText> getDefaultLabelAnimator() {
        return new TextViewLabelAnimator<EditText>();
    }

    // =============================================================================================
    // Delegate methods for the input widget
    // ==

    /**
     * Delegate method for the input widget
     */
    public Editable getInputWidgetText() {
        return getInputWidget().getText();
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetText(CharSequence text, TextView.BufferType type) {
        getInputWidget().setText(text, type);
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetTextSize(float size) {
        getInputWidget().setTextSize(size);
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetTextSize(int unit, float size) {
        getInputWidget().setTextSize(unit, size);
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetKeyListener(KeyListener input) {
        getInputWidget().setKeyListener(input);
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetTypeface(Typeface tf, int style) {
        getInputWidget().setTypeface(tf, style);
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetTextColor(int color) {
        getInputWidget().setTextColor(color);
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetTextColor(ColorStateList colors) {
        getInputWidget().setTextColor(colors);
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetText(CharSequence text) {
        getInputWidget().setText(text);
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetText(int resid) {
        getInputWidget().setText(resid);
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetInputType(int type) {
        getInputWidget().setInputType(type);
    }

    /**
     * Delegate method for the input widget
     */
    public void addInputWidgetTextChangedListener(TextWatcher watcher) {
        getInputWidget().addTextChangedListener(watcher);
    }

    /**
     * Delegate method for the input widget
     */
    public void removeInputWidgetTextChangedListener(TextWatcher watcher) {
        getInputWidget().removeTextChangedListener(watcher);
    }

    // =============================================================================================
    // Other methods
    // ==

    /**
     * @return true if the input widget is empty
     */
    private boolean isEditTextEmpty() {
        return getInputWidget().getText().toString().isEmpty();
    }

    /**
     * TextWatcher that changes the floating label state when the EditText content changes between
     * empty and not empty.
     */
    private class EditTextWatcher implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() == 0) {
                anchorLabel();
            } else {
                floatLabel();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Ignored
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Ignored
        }
    }
}
