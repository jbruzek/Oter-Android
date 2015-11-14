package com.joebruzek.oter.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.joebruzek.oter.R;

/**
 * Main Activity. This is the "home screen" of the app
 * It displays a list of the current oters
 */
public class OterListActivity extends ActionBarActivity {

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //TODO: everything
}
