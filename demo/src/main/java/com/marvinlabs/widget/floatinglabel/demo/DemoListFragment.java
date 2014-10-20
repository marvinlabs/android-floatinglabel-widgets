package com.marvinlabs.widget.floatinglabel.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marvinlabs.widget.slideshow.demo.R;


public class DemoListFragment extends android.support.v4.app.ListFragment {

    public interface Listener {
        public void onDemoSelected(String name, Class<? extends Fragment> demoClass);
    }

    public static final Demo[] DEMOS = new Demo[]{
            new Demo("EditText widgets", TextWidgetsFragment.class),
            new Demo("AutoComplete widgets", AutoCompleteWidgetsFragment.class),
            new Demo("Item picker widgets", ItemWidgetsFragment.class),
            new Demo("Instant picker widgets", InstantWidgetsFragment.class),
            new Demo("Chooser widgets", ChooserWidgetsFragment.class)
    };

    public static DemoListFragment newInstance() {
        return new DemoListFragment();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_demo_list, null, false);

        ArrayAdapter<Demo> adapter = new ArrayAdapter<Demo>(getActivity(), android.R.layout.simple_list_item_1, DEMOS);
        setListAdapter(adapter);

        return root;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Demo d = DEMOS[position];

        if (getActivity() instanceof Listener) {
            ((Listener) getActivity()).onDemoSelected(d.name, d.fragmentClass);
        }
    }

    public static class Demo {
        String name;
        Class<? extends Fragment> fragmentClass;

        public Demo(String name, Class<? extends Fragment> fragmentClass) {
            this.name = name;
            this.fragmentClass = fragmentClass;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
