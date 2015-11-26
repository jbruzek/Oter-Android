package com.joebruzek.oter.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.joebruzek.oter.R;
import com.joebruzek.oter.views.ContactIcon;

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
        public void onSetTimePositiveClick(SetTimeDialog l);
        public void onSetTimeNegativeClick(SetTimeDialog l);
    }

    private SetTimeDialogListener listener;
    private Button negative;
    private Button positive;
    private EditText picker;

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
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_set_time, null);
        builder.setView(v);

        positive = (Button) v.findViewById(R.id.set_time_positive_button);
        negative = (Button) v.findViewById(R.id.set_time_negative_button);
        picker = (EditText) v.findViewById(R.id.set_time_picker);


        final SetTimeDialog dis = this;
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSetTimePositiveClick(dis);
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSetTimeNegativeClick(dis);
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Get the time that is stored in the picker
     * @return int value of time
     */
    public int getTime() {
        return Integer.parseInt(picker.getText().toString());
    }
}
