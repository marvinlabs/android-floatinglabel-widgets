package com.marvinlabs.widget.floatinglabel.autocomplete;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.marvinlabs.widget.floatinglabel.R;

import java.util.ArrayList;

/**
 * A helper base adapter to quickly implement the autocomplete view for long running operations
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 20/10/2014.
 */
public abstract class AsyncAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private ArrayList<String> resultList;

    public AsyncAutoCompleteAdapter(Context context) {
        this(context, R.layout.flw_widget_dropdown_item);
    }

    public AsyncAutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    /**
     * This method should be implemented by subclasses to provide the filtered data to display in
     * the autocomplete popup.
     *
     * @param constraint The characters already entered by the user
     * @return A list of Strings matching the user constraint
     */
    protected abstract ArrayList<String> asyncGetResults(CharSequence constraint);

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = asyncGetResults(constraint);

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}