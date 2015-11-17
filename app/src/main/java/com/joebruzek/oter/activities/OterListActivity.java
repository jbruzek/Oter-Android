package com.joebruzek.oter.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joebruzek.oter.R;
import com.joebruzek.oter.adapters.ContactListAdapter;
import com.joebruzek.oter.adapters.CustomLinearLayoutManager;
import com.joebruzek.oter.adapters.OterListAdapter;
import com.joebruzek.oter.models.Location;
import com.joebruzek.oter.models.Oter;

import java.util.ArrayList;

/**
 * Main Activity. This is the "home screen" of the app
 * It displays a list of the current oters
 */
public class OterListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private OterListAdapter adapter;
    private FloatingActionButton fab;

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oter_list);

        findScreenElements();

        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }

        //test data
        ArrayList<Oter> testOters = new ArrayList<Oter>();
        for (int i = 0; i < 5; i++) {
            Oter o = new Oter();
            Location l = new Location();
            l.setName(getResources().getString(R.string.test_title));
            l.setLatitude(i + 0.123);
            l.setLongitude(i + 0.456);
            o.setLocation(l);
            o.setMessage(getResources().getString(R.string.test_text) + " - " + i);
            o.setTime(15);
            testOters.add(o);
        }

        recyclerView = (RecyclerView)findViewById(R.id.oter_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //CustomLinearLayoutManager tries to solve sizing issues
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new OterListAdapter(this, testOters);
        recyclerView.setAdapter(adapter);
    }


    /**
     * Find all the screen elements and assign them to local fields
     */
    private void findScreenElements() {
        toolbar = (Toolbar)findViewById(R.id.oter_list_toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.edit_oter_contacts_recycler_view);
        fab = (FloatingActionButton) findViewById(R.id.oter_list_fab);

        setClickListeners();
    }

    /**
     * set click listeners for screen elements
     */
    private void setClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), EditOterActivity.class));
            }
        });
    }
}
