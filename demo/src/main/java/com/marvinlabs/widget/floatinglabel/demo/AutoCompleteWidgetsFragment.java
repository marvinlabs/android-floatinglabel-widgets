package com.marvinlabs.widget.floatinglabel.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.autocomplete.AsyncAutoCompleteAdapter;
import com.marvinlabs.widget.floatinglabel.autocomplete.FloatingLabelAutoCompleteTextView;
import com.marvinlabs.widget.slideshow.demo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class AutoCompleteWidgetsFragment extends Fragment implements FloatingLabelAutoCompleteTextView.EditTextListener {

    FloatingLabelAutoCompleteTextView textView1;
    FloatingLabelAutoCompleteTextView textView2;
    FloatingLabelAutoCompleteTextView textView3;

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

        textView2 = (FloatingLabelAutoCompleteTextView) root.findViewById(R.id.edit_text2);
        textView2.setInputWidgetAdapter(new CountriesAutoCompleteAdapter(getActivity()));
        textView2.setEditTextListener(this);

        textView3 = (FloatingLabelAutoCompleteTextView) root.findViewById(R.id.edit_text3);
        textView3.setInputWidgetAdapter(new CountriesAutoCompleteAdapter(getActivity()));
        textView3.setEditTextListener(this);
        return root;
    }

    @Override
    public void onTextChanged(FloatingLabelAutoCompleteTextView source, String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets the countries available in the Android SDK. Adds a small 1s delay just to simulate an
     * async operation.
     */
    static class CountriesAutoCompleteAdapter extends AsyncAutoCompleteAdapter {

        public CountriesAutoCompleteAdapter(Context context) {
            super(context);
        }

        @Override
        protected ArrayList<String> asyncGetResults(CharSequence constraint) {
            ArrayList<String> countries = new ArrayList<String>();

            String[] isoCountries = Locale.getISOCountries();
            for (String country : isoCountries) {
                Locale locale = new Locale("en", country);
                String name = locale.getDisplayCountry();

                if (name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                    countries.add(name);
                }
            }

            Collections.sort(countries);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { /* Ignored */ }

            return countries;
        }
    }
}
