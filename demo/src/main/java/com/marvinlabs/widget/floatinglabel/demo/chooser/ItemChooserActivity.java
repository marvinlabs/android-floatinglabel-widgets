package com.marvinlabs.widget.floatinglabel.demo.chooser;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marvinlabs.widget.slideshow.demo.R;

/**
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 11/09/2014.
 */
public class ItemChooserActivity extends ListActivity {

    public static final String RES_SELECTED_PRODUCT = "SelectedProduct";

    public static final Product[] AVAILABLE_PRODUCTS = new Product[]{
            new Product("Apple", 20),
            new Product("Banana", 10),
            new Product("Lemon", 5),
            new Product("Mango", 30),
            new Product("Peach", 30),
    };

    protected ArrayAdapter<Product> adapter;

    // =============================================================================================
    // Factory methods
    // ==

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, ItemChooserActivity.class);
        return i;
    }

    // =============================================================================================
    // Lifecycle
    // ==

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, AVAILABLE_PRODUCTS);
        setListAdapter(adapter);
    }

    // =============================================================================================
    // Event handlers
    // ==

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Product selectedItem = adapter.getItem(position);

        Intent res = new Intent();
        res.putExtra(RES_SELECTED_PRODUCT, selectedItem);
        setResult(Activity.RESULT_OK, res);
        finish();
    }

    // =============================================================================================
    // Other methods
    // ==
}
