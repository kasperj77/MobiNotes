package com.mobigolabs.mobinotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteAdapter = new NoteAdapter();

        ListView listNote = (ListView) findViewById(R.id.listView);

        assert listNote != null;
        listNote.setAdapter(mNoteAdapter);

        // So we can long click it
        listNote.setLongClickable(true);

        // Now to detect long clicks and delete the note

        listNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int whichItem, long id) {
                // Ask NoteAdapter to delete this entry
                mNoteAdapter.deleteNote(whichItem);
                return true;
            }
        });

        // Handle clicks on the ListView
        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int whichItem, long id) {

        // Create a temporary Note Which is a reference to the Note that has just been clicked

                Log.i("info", "clicking item");
                Note tempNote = mNoteAdapter.getItem(whichItem);

                // Create a new dialog window
                DialogShowNote dialog = new DialogShowNote();

                // Send in a reference to the note to be shown
                dialog.sendNoteSelected(tempNote);

                // Show the dialog window with the note in it
                dialog.show(getFragmentManager(), "");
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
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_add) {
            DialogNewNote dialog = new DialogNewNote();
            dialog.show(getFragmentManager(), "");
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private NoteAdapter mNoteAdapter;

    public void createNewNote(Note n) {

        mNoteAdapter.addNote(n);
    }

    // NoteAdapter which is used to show the notes in a list and also save them (serialization)

    public class NoteAdapter extends BaseAdapter {

        // setting the mSerializer variable
            private JSONSerializer mSerializer;

        // NoteAdapter method which sets up the Serializer to store the JSON data into file
            public NoteAdapter(){
                mSerializer = new JSONSerializer("NoteToSelf.json",
                        MainActivity.this.getApplicationContext());
                try {
                    noteList = mSerializer.load();
                } catch (Exception e) {
                    noteList = new ArrayList<Note>();
                    Log.e("Error loading notes: ", "", e);
                }
            }

        // This is saving the notes when you leave the page.
            public void saveNotes(){
                try{
                    mSerializer.save(noteList);
                }
                catch(Exception e){
                    Log.e("Error Saving Notes","", e);
                }
            }

            public void deleteNote(int n){
                noteList.remove(n);
                notifyDataSetChanged();
        }


        // Creating an array list for the notes
            List<Note> noteList = new ArrayList<Note>();

            @Override
            public int getCount() {
                return noteList.size();
            }

            @Override
            public Note getItem(int whichItem) {
                // Returns the requested note
                return noteList.get(whichItem);
            }

            @Override
            public long getItemId(int whichItem) {
                // Method used internally
                return whichItem;
            }

            @Override
            public View getView(int whichItem, View view, ViewGroup viewGroup) {

			/*
				Prepare a list item to show our data
				The list item is contained in the view parameter
				The position of the data in our ArrayList is contained
				in whichItem parameter
			*/


                // Has view been inflated already
                if (view == null) {

                    // No. So do so here
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    view = inflater.inflate(R.layout.listitem, viewGroup, false);

                }// End if

                // Grab a reference to all our TextView and ImageView widgets
                TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
                TextView txtDescription = (TextView) view.findViewById(R.id.txtDescription);
                ImageView ivImportant = (ImageView) view.findViewById(R.id.imageViewImportant);
                ImageView ivTodo = (ImageView) view.findViewById(R.id.imageViewTodo);
                ImageView ivIdea = (ImageView) view.findViewById(R.id.imageViewIdea);


                // Hide any ImageView widgets that are not relevant
                Note tempNote = noteList.get(whichItem);


                if (!tempNote.isImportant()) {
                    ivImportant.setVisibility(View.GONE);
                }

                if (!tempNote.isToDo()) {
                    ivTodo.setVisibility(View.GONE);
                }

                if (!tempNote.isIdea()) {
                    ivIdea.setVisibility(View.GONE);
                }

                // Add the text to the heading and description
                txtTitle.setText(tempNote.getTitle());
                txtDescription.setText(tempNote.getDescription());

                return view;
            }

            public void addNote(Note n) {

                noteList.add(n);
                notifyDataSetChanged();

            }
        }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences mPrefs = getSharedPreferences("mobinotes", MODE_PRIVATE);
        boolean mSound = mPrefs.getBoolean("sound", true);
        int mAnimOption = mPrefs.getInt("anim option", SettingsActivity.FAST);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mNoteAdapter.saveNotes();
    }

}