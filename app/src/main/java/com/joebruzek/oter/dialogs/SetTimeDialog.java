package com.joebruzek.oter.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Dialog for adding a time to an oter
 *
 * Calling activity MUST implement the SetTimeDialog.SetTimeDialogListener interface
 * Created by jbruzek on 11/13/15.
 */
public class SetTimeDialog extends DialogFragment {
    /**
     * Interface for sending information back to the calling activity
     */
    public interface SetTimeDialogListener {
        //Possibly in the future onDialogPositiveClick will also have a parameter for the time selected...?
        public void onDialogPositiveClick(SetTimeDialog l);
        public void onDialogNegativeClick(SetTimeDialog l);
    }

    private SetTimeDialogListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            // Instantiate the AddLocationDialogListener so we can send events to the host
            listener = (SetTimeDialogListener) activity;
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
