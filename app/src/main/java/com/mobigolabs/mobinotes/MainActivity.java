package com.mobigolabs.mobinotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    // Temp code to create
    Note mTempNote = new Note();

    public void createNewNote(Note n){
        //Temporary code
        mTempNote = n;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        assert button != null;

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //Create a new DialogShowNote called dialog
                DialogShowNote dialog = new DialogShowNote();

                // Send the note via the sendNoteSelected method
                dialog.sendNoteSelected(mTempNote);

                // Create the dialog
                dialog.show(getFragmentManager(), "123");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_add) {
            DialogNewNote dialog = new DialogNewNote();
            dialog.show(getFragmentManager(), "");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
