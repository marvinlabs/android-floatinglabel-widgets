package com.marvinlabs.widget.floatinglabel.autocomplete;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.marvinlabs.widget.floatinglabel.FloatingLabelTextViewBase;
import com.marvinlabs.widget.floatinglabel.LabelAnimator;
import com.marvinlabs.widget.floatinglabel.R;
import com.marvinlabs.widget.floatinglabel.anim.TextViewLabelAnimator;

/**
 * An implementation of the floating label input widget for Android's EditText
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 20/10/2014.
 */
public class FloatingLabelAutoCompleteTextView extends FloatingLabelTextViewBase<AutoCompleteTextView> {

    public interface EditTextListener {
        public void onTextChanged(FloatingLabelAutoCompleteTextView source, String text);
    }

    /**
     * The listener to notify when the selection changes
     */
    protected EditTextListener editTextListener;

    // =============================================================================================
    // Lifecycle
    // ==

    public FloatingLabelAutoCompleteTextView(Context context) {
        super(context);
    }

    public FloatingLabelAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingLabelAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // =============================================================================================
    // Overridden methods
    // ==

    @Override
    protected void afterLayoutInflated(Context context, AttributeSet attrs, int defStyle) {
        super.afterLayoutInflated(context, attrs, defStyle);

        final CharSequence completionHint;
        final int completionThreshold;
        final int popupBackground;
        final int dropDownWidth;
        final int dropDownHeight;

        if (attrs == null) {
            completionHint = "";
            completionThreshold = 1;
            dropDownHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            dropDownWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            popupBackground = getDefaultPopupBackgroundResId();
        } else {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingLabelAutoCompleteTextView, defStyle, 0);
            completionHint = a.getText(R.styleable.FloatingLabelAutoCompleteTextView_android_completionHint);
            completionThreshold = a.getInt(R.styleable.FloatingLabelAutoCompleteTextView_android_completionThreshold, 1);
            dropDownHeight = a.getDimensionPixelSize(R.styleable.FloatingLabelAutoCompleteTextView_android_dropDownHeight, ViewGroup.LayoutParams.WRAP_CONTENT);
            dropDownWidth = a.getDimensionPixelSize(R.styleable.FloatingLabelAutoCompleteTextView_android_dropDownWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupBackground = a.getResourceId(R.styleable.FloatingLabelAutoCompleteTextView_android_popupBackground, getDefaultPopupBackgroundResId());
            a.recycle();
        }

        final AutoCompleteTextView inputWidget = getInputWidget();
        inputWidget.setCompletionHint(completionHint);
        inputWidget.setThreshold(completionThreshold);
        inputWidget.setDropDownWidth(dropDownWidth);
        inputWidget.setDropDownHeight(dropDownHeight);
        inputWidget.setDropDownBackgroundResource(popupBackground);
        inputWidget.addTextChangedListener(new EditTextWatcher());
    }

    protected int getDefaultPopupBackgroundResId() {
        return R.drawable.bg_dropdown_panel;
    }

    @Override
    protected int getDefaultLayoutId() {
        return R.layout.flw_widget_floating_label_autocomplete_textview;
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
    protected LabelAnimator<AutoCompleteTextView> getDefaultLabelAnimator() {
        return new TextViewLabelAnimator<AutoCompleteTextView>();
    }

    // =============================================================================================
    // Delegate methods for the input widget
    // ==

    /**
     * Delegate method for the input widget
     */
    public void setInputWidgetThreshold(int threshold) {
        getInputWidget().setThreshold(threshold);
    }

    /**
     * Delegate method for the input widget
     */
    public <T extends ListAdapter & Filterable> void setInputWidgetAdapter(T adapter) {
        getInputWidget().setAdapter(adapter);
    }

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

    public EditTextListener getEditTextListener() {
        return editTextListener;
    }

    public void setEditTextListener(EditTextListener editTextListener) {
        this.editTextListener = editTextListener;
    }

    /**
     * Called when the text within the input widget is updated
     *
     * @param s The new text
     */
    protected void onTextChanged(String s) {
        if(!isFloatOnFocusEnabled()){
            if (s.length() == 0) {
                anchorLabel();
            } else {
                floatLabel();
            }
        }

        if (editTextListener != null) editTextListener.onTextChanged(this, s);
    }

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
            FloatingLabelAutoCompleteTextView.this.onTextChanged(s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Ignored
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }
}
