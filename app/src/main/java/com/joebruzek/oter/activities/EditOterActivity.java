package com.joebruzek.oter.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.joebruzek.oter.R;
import com.joebruzek.oter.adapters.ContactListAdapter;
import com.joebruzek.oter.adapters.OterListAdapter;
import com.joebruzek.oter.dialogs.AddLocationDialog;
import com.joebruzek.oter.dialogs.SetTimeDialog;
import com.joebruzek.oter.models.Location;
import com.joebruzek.oter.models.Oter;
import com.joebruzek.oter.utilities.Strings;

import java.util.ArrayList;

/**
 * Created by jbruzek on 11/15/15.
 */
public class EditOterActivity extends AppCompatActivity implements SetTimeDialog.SetTimeDialogListener, AddLocationDialog.AddLocationDialogListener {

    private Oter oter;
    Toolbar toolbar;
    private EditText editMessage;
    private ImageButton timeButton;
    private ImageButton locationButton;
    private ImageButton contactButton;
    private TextView timeText;
    private RecyclerView recyclerView;
    private Button scheduleButton;

    public EditOterActivity() {
    }

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_oter);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            oter = (Oter) b.getParcelable("oter");
        }

        //Get the references to all the buttons and stuff
        findScreenElements();

        if (toolbar != null) {
            toolbar.setTitle(getResources().getString(R.string.activity_new_oter_name));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setCurrentOterInformation();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ArrayList<String> contacts = new ArrayList<String>();
        contacts.add("Joe Bruzek");
        contacts.add("Yash Pant");
        contacts.add("Mark Olsen");
        contacts.add("Alexia Lutz");
        ContactListAdapter adapter = new ContactListAdapter(this, contacts, true);
        recyclerView.setAdapter(adapter);
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

    /**
     * Find all the screen elements and assign them to local fields
     */
    private void findScreenElements() {
        toolbar = (Toolbar)findViewById(R.id.edit_oter_toolbar);
        editMessage = (EditText) findViewById(R.id.edit_oter_message);
        timeButton = (ImageButton) findViewById(R.id.edit_oter_time_button);
        locationButton = (ImageButton) findViewById(R.id.edit_oter_location_button);
        contactButton = (ImageButton) findViewById(R.id.edit_oter_contact_button);
        timeText = (TextView) findViewById(R.id.edit_oter_time);
        recyclerView = (RecyclerView)findViewById(R.id.edit_oter_contacts_recycler_view);
        scheduleButton = (Button) findViewById(R.id.edit_oter_schedule_button);

        setClickListeners();
    }

    /**
     * set the onClickListeners for the different option buttons on the screen
     */
    private void setClickListeners() {
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SetTimeDialog();
                newFragment.show(getFragmentManager(), "setTime");
            }
        });
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new AddLocationDialog();
                newFragment.show(getFragmentManager(), "addLocation");
            }
        });
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Clicked contact button", Toast.LENGTH_SHORT).show();
            }
        });
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Clicked schedule button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCurrentOterInformation() {
        if (oter != null) {
            toolbar.setTitle(getResources().getString(R.string.activity_edit_oter_name));
            editMessage.setText(oter.getMessage());

            setTimeText();

            scheduleButton.setText(getResources().getString(R.string.save_oter));
        }
    }

    private void setTimeText() {
        timeText.setText(Strings.buildTimeString(oter.getTime(), oter.getLocation().getName()));
    }

    @Override
    public void onSetTimePositiveClick(SetTimeDialog l) {
        oter.setTime(l.getTime());
        setTimeText();
        l.dismiss();
    }

    @Override
    public void onSetTimeNegativeClick(SetTimeDialog l) {
        l.dismiss();
    }

    @Override
    public void onAddLocationPositiveClick(AddLocationDialog l) {

    }

    @Override
    public void onAddLocationNegativeClick(AddLocationDialog l) {
        l.dismiss();
    }
}
