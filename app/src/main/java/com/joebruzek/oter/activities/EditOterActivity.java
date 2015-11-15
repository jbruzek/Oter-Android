package com.joebruzek.oter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.joebruzek.oter.R;
import com.joebruzek.oter.adapters.OterListAdapter;
import com.joebruzek.oter.models.Location;
import com.joebruzek.oter.models.Oter;

import java.util.ArrayList;

/**
 * Created by jbruzek on 11/15/15.
 */
public class EditOterActivity extends ActionBarActivity {

    private Oter oter;

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_oter);

        oter = (Oter)getIntent().getExtras().getParcelable("oter");

        Toolbar toolbar = (Toolbar)findViewById(R.id.edit_oter_toolbar);
        if (toolbar != null) {
            toolbar.setTitle(oter.getMessage());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    /**
     * When a menu item is selected.
     * @param item The item selected
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //if it's the back button, go back
        if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }

    //TODO: everything
}
