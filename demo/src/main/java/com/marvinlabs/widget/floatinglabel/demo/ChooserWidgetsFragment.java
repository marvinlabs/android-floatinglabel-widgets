package com.marvinlabs.widget.floatinglabel.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.demo.chooser.ItemChooserActivity;
import com.marvinlabs.widget.floatinglabel.demo.chooser.Product;
import com.marvinlabs.widget.floatinglabel.itemchooser.FloatingLabelItemChooser;
import com.marvinlabs.widget.slideshow.demo.R;


public class ChooserWidgetsFragment extends Fragment implements FloatingLabelItemChooser.OnItemChooserEventListener<Product> {

    public static final int REQUEST_CHOOSE_PRODUCT = 0x1234;

    FloatingLabelItemChooser<Product> chooser1;

    public static ChooserWidgetsFragment newInstance() {
        return new ChooserWidgetsFragment();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chooser_widgets, null, false);


        // Choosers
        chooser1 = (FloatingLabelItemChooser<Product>) root.findViewById(R.id.chooser1);
        chooser1.setItemChooserListener(this);
        chooser1.setWidgetListener(new FloatingLabelItemChooser.OnWidgetEventListener<Product>() {
            @Override
            public void onShowItemChooser(FloatingLabelItemChooser<Product> source) {
                startActivityForResult(ItemChooserActivity.newIntent(getActivity()), REQUEST_CHOOSE_PRODUCT);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_PRODUCT) {
            if (resultCode == Activity.RESULT_OK) {
                Product selectedProduct = data.getParcelableExtra(ItemChooserActivity.RES_SELECTED_PRODUCT);
                chooser1.setSelectedItem(selectedProduct);
                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // Implementation of the OnItemPickerEventListener interface

    @Override
    public void onSelectionChanged(FloatingLabelItemChooser<Product> source, Product selectedItem) {
        Toast.makeText(getActivity(), source.getItemPrinter().print(selectedItem), Toast.LENGTH_SHORT).show();
    }
}
