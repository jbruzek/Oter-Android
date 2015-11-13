package com.joebruzek.oter.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

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
        public void onDialogPositiveClick(AddLocationDialog l);
        public void onDialogNegativeClick(AddLocationDialog l);
    }

    private AddLocationDialogListener listener;

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
        //TODO: Inflate the layout and add functionality
        return super.onCreateDialog(savedInstanceState);
    }
}
