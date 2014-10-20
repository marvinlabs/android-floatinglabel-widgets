package com.marvinlabs.widget.floatinglabel.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.autocomplete.FloatingLabelAutoCompleteTextView;
import com.marvinlabs.widget.slideshow.demo.R;


public class AutoCompleteWidgetsFragment extends Fragment implements FloatingLabelAutoCompleteTextView.EditTextListener {

    FloatingLabelAutoCompleteTextView textView1;

    public static AutoCompleteWidgetsFragment newInstance() {
        return new AutoCompleteWidgetsFragment();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_autocomplete_widgets, null, false);

        textView1 = (FloatingLabelAutoCompleteTextView) root.findViewById(R.id.edit_text1);
        textView1.setInputWidgetAdapter(new ArrayAdapter<String>(getActivity(), R.layout.flw_widget_dropdown_item, new String[]{"one", "two", "three"}));
        textView1.setInputWidgetThreshold(1);
        textView1.setEditTextListener(this);

        return root;
    }

    @Override
    public void onTextChanged(FloatingLabelAutoCompleteTextView source, String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
