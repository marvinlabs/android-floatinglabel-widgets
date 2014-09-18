package com.marvinlabs.widget.floatinglabel.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.edittext.FloatingLabelEditText;
import com.marvinlabs.widget.slideshow.demo.R;


public class TextWidgetsFragment extends Fragment implements FloatingLabelEditText.EditTextListener {

    public static TextWidgetsFragment newInstance() {
        return new TextWidgetsFragment();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_text_widgets, null, false);

        ((FloatingLabelEditText) root.findViewById(R.id.edit_text1)).setEditTextListener(this);
        ((FloatingLabelEditText) root.findViewById(R.id.edit_text2)).setEditTextListener(this);
        ((FloatingLabelEditText) root.findViewById(R.id.edit_text3)).setEditTextListener(this);
        ((FloatingLabelEditText) root.findViewById(R.id.edit_text4)).setEditTextListener(this);

        return root;
    }

    @Override
    public void onTextChanged(FloatingLabelEditText source, String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
