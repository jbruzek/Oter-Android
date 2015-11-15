package com.joebruzek.oter.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.joebruzek.oter.R;
import com.joebruzek.oter.adapters.OterListAdapter;
import com.joebruzek.oter.models.Location;
import com.joebruzek.oter.models.Oter;

import java.util.ArrayList;

/**
 * Main Activity. This is the "home screen" of the app
 * It displays a list of the current oters
 */
public class OterListActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private OterListAdapter adapter;

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oter_list_activity);

        //test data
        ArrayList<Oter> testOters = new ArrayList<Oter>();
        for (int i = 0; i < 5; i++) {
            Oter o = new Oter();
            Location l = new Location();
            l.setName(getResources().getString(R.string.test_title));
            o.setLocation(l);
            o.setMessage(getResources().getString(R.string.test_text));
            o.setTime(15);
            testOters.add(o);
        }

        recyclerView = (RecyclerView)findViewById(R.id.oter_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //false because there will always be default locations
        adapter = new OterListAdapter(this, testOters);
        recyclerView.setAdapter(adapter);
    }

    //TODO: everything
}
