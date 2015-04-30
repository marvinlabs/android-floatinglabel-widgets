package com.marvinlabs.widget.floatinglabel.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.edittext.FloatingLabelEditText;
import com.marvinlabs.widget.slideshow.demo.R;


public class AnimateLayoutChangesCompatFragment extends Fragment implements FloatingLabelEditText.EditTextListener {

    public static AnimateLayoutChangesCompatFragment newInstance() {
        return new AnimateLayoutChangesCompatFragment();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_animate_layout_changes_compat, null, false);

        ((FloatingLabelEditText) root.findViewById(R.id.edit_text1)).setEditTextListener(this);
        ((FloatingLabelEditText) root.findViewById(R.id.edit_text2)).setEditTextListener(this);
        ((FloatingLabelEditText) root.findViewById(R.id.edit_text3)).setEditTextListener(this);
        ((FloatingLabelEditText) root.findViewById(R.id.edit_text4)).setEditTextListener(this);

        final ViewGroup viewGroup = (ViewGroup) root.findViewById(R.id.layout);

        Button button = (Button) root.findViewById(R.id.buttonTry);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeViewGroupVisibility(viewGroup);
            }
        });

        return root;
    }

    private void changeViewGroupVisibility(ViewGroup viewGroup) {
        if (viewGroup.getVisibility() == View.VISIBLE) {
            viewGroup.setVisibility(View.GONE);
        } else {
            viewGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTextChanged(FloatingLabelEditText source, String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
