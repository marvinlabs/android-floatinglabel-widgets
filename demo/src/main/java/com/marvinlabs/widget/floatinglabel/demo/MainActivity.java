package com.marvinlabs.widget.floatinglabel.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.marvinlabs.widget.slideshow.demo.R;


public class MainActivity extends FragmentActivity implements DemoListFragment.Listener {

    DemoListFragment demoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity layout
        setContentView(R.layout.activity_singlepane);

        demoList = (DemoListFragment) getSupportFragmentManager().findFragmentByTag("DemoListFragment");
        if (demoList == null) {
            demoList = DemoListFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, demoList, "DemoListFragment")
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        setTitle(R.string.title_activity_main_widgets);
        super.onBackPressed();
    }

    @Override
    public void onDemoSelected(String name, Class<? extends Fragment> demoClass) {
        try {
            Fragment demo = demoClass.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, demo, "DemoFragment")
                    .addToBackStack(name)
                    .commit();

            setTitle(name);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
