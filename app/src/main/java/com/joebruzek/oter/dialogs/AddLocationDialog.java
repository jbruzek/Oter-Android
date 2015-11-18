package com.joebruzek.oter.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.joebruzek.oter.R;
import com.joebruzek.oter.adapters.LocationListAdapter;
import com.joebruzek.oter.adapters.OterListAdapter;

import java.util.ArrayList;

/**
 * Dialog for adding a location to an Oter.
 *
 * The calling Activity MUST implement the AddLocationDialog.AddLocationDialogListener interface
 *
 * Created by jbruzek on 11/13/15.
 */
public class AddLocationDialog extends DialogFragment {

    /**
     * Interface for sending information back to the calling activity
     */
    public interface AddLocationDialogListener {
        //Possibly in the future onDialogPositiveClick will also have a parameter for the location selected...?
        public void onAddLocationPositiveClick(AddLocationDialog l);
        public void onAddLocationNegativeClick(AddLocationDialog l);
    }

    private AddLocationDialogListener listener;
    private Button positive;
    private Button negative;
    private RecyclerView recyclerView;

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
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_location, null);
        builder.setView(v);

        positive = (Button) v.findViewById(R.id.set_time_positive_button);
        negative = (Button) v.findViewById(R.id.set_time_negative_button);
        recyclerView = (RecyclerView) v.findViewById(R.id.dialog_add_location_recycler_view);

        final AddLocationDialog dis = this;
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddLocationPositiveClick(dis);
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddLocationNegativeClick(dis);
            }
        });

        //TestData
        ArrayList<String> testLocations = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            testLocations.add("Location " + i);
        }
        //end TestData

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        LocationListAdapter adapter = new LocationListAdapter(getActivity(), testLocations);
        recyclerView.setAdapter(adapter);

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
