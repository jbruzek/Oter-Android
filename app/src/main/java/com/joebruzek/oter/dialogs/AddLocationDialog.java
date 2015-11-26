package com.joebruzek.oter.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.joebruzek.oter.R;
import com.joebruzek.oter.adapters.LocationListAdapter;
import com.joebruzek.oter.adapters.OterListAdapter;
import com.joebruzek.oter.models.Location;
import com.joebruzek.oter.utilities.HttpTask;
import com.joebruzek.oter.utilities.Strings;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Dialog for adding a location to an Oter.
 *
 * The calling Activity MUST implement the AddLocationDialog.AddLocationDialogListener interface
 *
 * Created by jbruzek on 11/13/15.
 */
public class AddLocationDialog extends DialogFragment implements HttpTask.HttpCaller {

    /**
     * Interface for sending information back to the calling activity
     */
    public interface AddLocationDialogListener {
        //Possibly in the future onDialogPositiveClick will also have a parameter for the location selected...?
        public void onAddLocationPositiveClick(AddLocationDialog l);
        public void onAddLocationNegativeClick(AddLocationDialog l);
    }

    private AddLocationDialogListener listener;
    private EditText search;
    private Button positive;
    private Button negative;
    private RecyclerView recyclerView;
    private ImageButton searchButton;
    private Location location;
    LocationListAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            // Instantiate the AddLocationDialogListener so we can send events to the host
            listener = (AddLocationDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement AddLocationDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //get the location from the bundle
        location = getArguments().getParcelable("location");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_location, null);
        builder.setView(v);

        search = (EditText) v.findViewById(R.id.dialog_add_location_search);
        positive = (Button) v.findViewById(R.id.dialog_add_location_positive_button);
        negative = (Button) v.findViewById(R.id.dialog_add_location_negative_button);
        recyclerView = (RecyclerView) v.findViewById(R.id.dialog_add_location_recycler_view);
        searchButton = (ImageButton) v.findViewById(R.id.dialog_add_location_search_button);

        final AddLocationDialog dis = this;
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelectedLocation() == null) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.location_error), Toast.LENGTH_SHORT).show();
                } else {
                    listener.onAddLocationPositiveClick(dis);
                }
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddLocationNegativeClick(dis);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSearch();
                search.clearFocus();
            }
        });
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    sendSearch();
                    search.clearFocus();
                    handled = true;
                }
                return handled;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        if (location == null) {
            adapter = new LocationListAdapter(getActivity(), new ArrayList<Location>());
        } else {
            adapter = new LocationListAdapter(getActivity(), location);
        }
        recyclerView.setAdapter(adapter);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * search something
     */
    private void sendSearch() {
        ((LocationListAdapter)recyclerView.getAdapter()).setLoading();

        //hide the keyboard
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);

        //send the search
        new HttpTask(this).execute(Strings.buildGooglePlacesQuery(getActivity(), search.getText().toString()));
    }

    /**
     * HttpTask has returned a JSONObject with information about locations
     * @param result
     */
    @Override
    public void processResults(JSONObject result) {
        //no results
        if (result == null) {
            //I'll get to this later I guess
            return;
        }

        ((LocationListAdapter)recyclerView.getAdapter()).setDataSet(Location.getLocationsFromJSON(result));
    }

    /**
     * Get the selected location from the adapter
     * @return
     */
    public Location getLocation() {
        Location l = adapter.getSelectedLocation();
        return l;
    }
}
