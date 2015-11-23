package com.joebruzek.oter.activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.joebruzek.oter.database.OterDataLayer;
import com.joebruzek.oter.dialogs.AddLocationDialog;
import com.joebruzek.oter.dialogs.SetTimeDialog;
import com.joebruzek.oter.models.Contact;
import com.joebruzek.oter.models.Location;
import com.joebruzek.oter.models.Oter;
import com.joebruzek.oter.utilities.Contacts;
import com.joebruzek.oter.utilities.Strings;

import java.util.ArrayList;

/**
 * Created by jbruzek on 11/15/15.
 */
public class EditOterActivity extends AppCompatActivity implements SetTimeDialog.SetTimeDialogListener, AddLocationDialog.AddLocationDialogListener {

    private static final int CONTACT_PICKER_RESULT = 5013;

    private Oter oter;
    private Toolbar toolbar;
    private EditText editMessage;
    private ImageButton timeButton;
    private ImageButton locationButton;
    private ImageButton contactButton;
    private TextView timeText;
    private RecyclerView recyclerView;
    private Button scheduleButton;
    private Button deleteButton;
    private boolean newOter = true;
    private OterDataLayer dataLayer;

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
            newOter = false;
        } else {
            newOter = true;
            oter = new Oter();
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
        ContactListAdapter adapter = new ContactListAdapter(this, oter.getContacts(), true);
        recyclerView.setAdapter(adapter);

        dataLayer = new OterDataLayer(this);
        dataLayer.openDB();
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
        deleteButton = (Button) findViewById(R.id.edit_oter_delete_button);

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
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
            }
        });
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleButtonClicked();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButtonClicked();
            }
        });
    }

    /**
     * When the schedule button is clicked. Either add an oter to the database or update an oter in the database
     */
    private void scheduleButtonClicked() {
        //TODO: Add a LOT of invalid data checks so we don't screw up the database

        oter.setMessage(editMessage.getText().toString());

        //TODO: implement addLocationDialog
        Location l = new Location();
        l.setName("New oter location");
        l.setAddress("1234 Oter Lane");
        oter.setLocation(l);
        //end todo

        if (newOter) {
            dataLayer.insertOter(oter);
        } else {
            dataLayer.updateOter(oter);
        }

        finish();
    }

    /**
     * When the delete button is clicked. Show a dialog confirming the deletion
     */
    private void deleteButtonClicked() {
        //Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(this)
                .setMessage("Delete this Oter?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataLayer.removeOter(oter);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .show();
    }

    /**
     * Set the information on the screen to fit the current oter
     */
    private void setCurrentOterInformation() {
        if (!newOter) {
            toolbar.setTitle(getResources().getString(R.string.activity_edit_oter_name));
            editMessage.setText(oter.getMessage());

            scheduleButton.setText(getResources().getString(R.string.save_oter));

            deleteButton.setVisibility(View.VISIBLE);
        }

        setTimeText();
    }

    /**
     * Set the time text on the screen to indicate the user's choice
     */
    private void setTimeText() {
        if (oter.getLocation() == null) {
            timeText.setText(Strings.buildTimeString(oter.getTime()));
        } else {
            timeText.setText(Strings.buildTimeString(oter.getTime(), oter.getLocation().getName()));
        }
    }

    /**
     * Called when an activity returns a result to this activity. In this case it will be
     * when the user adds a contact
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            switch(requestCode){
                case CONTACT_PICKER_RESULT:
                    Uri result = data.getData();
                    handleContactSelected(result);
                    break;
            }
        }
    }

    /**
     * Handle a contact that the user selected
     * @param result
     */
    private void handleContactSelected(Uri result)
    {
        Contact c = Contacts.getContact(this, result);
        oter.getContacts().add(c.getNumber());

        ((ContactListAdapter)recyclerView.getAdapter()).getDataList().add(c.getNumber());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * When the user selects confirm on the setTimeDialog. Set the time to their choice.
     * @param l
     */
    @Override
    public void onSetTimePositiveClick(SetTimeDialog l) {
        oter.setTime(l.getTime());
        setTimeText();
        l.dismiss();
    }

    /**
     * When the user selects cancel on the setTimeDialog.
     * @param l
     */
    @Override
    public void onSetTimeNegativeClick(SetTimeDialog l) {
        l.dismiss();
    }

    /**
     * When the user selects confirm on the addLocationDialog
     * @param l
     */
    @Override
    public void onAddLocationPositiveClick(AddLocationDialog l) {

    }

    /**
     * When the user selects cancel on the addLocationDialog
     * @param l
     */
    @Override
    public void onAddLocationNegativeClick(AddLocationDialog l) {
        l.dismiss();
    }
}
