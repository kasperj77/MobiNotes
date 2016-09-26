package com.mobigolabs.mobinotes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

/**
 * Created by jordan on 9/24/16.
 */
public class DialogNewNote extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        // creating the alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // creating the inflater for activity
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // inflating the dialog new note layout
        View dialogView = inflater.inflate(R.layout.dialog_new_note, null);


        // get a reference to each of the UI widgets, marking as final because they will be used
        // in an anonymous class. ---------------------------------------------------------------

        final EditText editTitle = (EditText) dialogView.findViewById(R.id.editTitle);
        final EditText editDescription = (EditText) dialogView.findViewById(R.id.editDescription);
        final CheckBox checkBoxIdea = (CheckBox) dialogView.findViewById(R.id.checkBoxIdea);
        final CheckBox checkBoxTodo = (CheckBox) dialogView.findViewById(R.id.checkBoxTodo);
        final CheckBox checkBoxImportant = (CheckBox) dialogView.findViewById(R.id.checkBoxImportant);

        Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
        Button btnOK = (Button) dialogView.findViewById(R.id.btnOK);

        builder.setView(dialogView).setMessage("Add a new note");



        // Handle the cancel button onClick -----------------------------------------------------

        btnCancel.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        // Handle the OK button onClick-----------------------------------------------------------

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                // Create a new note
                Note newNote = new Note();

                //Set its variables to match the users entries on the form
                newNote.setTitle(editTitle.getText().toString());
                newNote.setDescription(editDescription.getText().toString());
                newNote.setIdea(checkBoxIdea.isChecked());
                newNote.setToDo(checkBoxTodo.isChecked());
                newNote.setImportant(checkBoxImportant.isChecked());

                // Get a reference to MainActivity
                MainActivity callingActivity = (MainActivity) getActivity();

                // Pass newNote back to MainActivity
                callingActivity.createNewNote(newNote);

                //Quit the dialog
                dismiss();
            }
        });

        return builder.create();
    }
}
