package com.mobigolabs.mobinotes;
import org.json.JSONException;
import org.json.JSONObject;

// Creating the note class and setting its member variables along with getters and setters

public class Note {

    private String mTitle;
    private String mDescription;
    private boolean mIdea;
    private boolean mToDo;
    private boolean mImportant;

    private static final String JSON_TITLE = "title";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_IDEA = "idea" ;
    private static final String JSON_TODO = "todo";
    private static final String JSON_IMPORTANT = "important";

    // Methods added inchapter 7
    // Constructor
    // Only used when new is called with a JSONObject
    public Note(JSONObject jo) throws JSONException {
        mTitle =  jo.getString(JSON_TITLE);
        mDescription = jo.getString(JSON_DESCRIPTION);
        mIdea = jo.getBoolean(JSON_IDEA);
        mToDo = jo.getBoolean(JSON_TODO);
        mImportant = jo.getBoolean(JSON_IMPORTANT);
    }

    // Now we must provide an empty default constructor for when we create a Note
    public Note (){

    }

    public JSONObject convertToJSON() throws JSONException{
        JSONObject jo = new JSONObject();

        jo.put(JSON_TITLE, mTitle);
        jo.put(JSON_DESCRIPTION, mDescription);
        jo.put(JSON_IDEA, mIdea);
        jo.put(JSON_TODO, mToDo);
        jo.put(JSON_IMPORTANT, mImportant);

        return jo;
    }




    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public boolean isIdea() {
        return mIdea;
    }

    public void setIdea(boolean mIdea) {
        this.mIdea = mIdea;
    }

    public boolean isToDo() {
        return mToDo;
    }

    public void setToDo(boolean mToDo) {
        this.mToDo = mToDo;
    }

    public boolean isImportant() {
        return mImportant;
    }

    public void setImportant(boolean mImportant) {
        this.mImportant = mImportant;
    }

}
