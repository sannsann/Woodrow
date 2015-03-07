package com.stormcloud.woodrow.model;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * A class representation of a row in table "Vocabulary"
 * Created by schhan on 2/20/15.
 */
public class Word {
    
    // SQL convention says Table name should be "singlular", so not Words
    public static final String TABLE_NAME = "Word";
    
    // Naming the id column with an underscore is good to be consistent
    // with other Android idioms. This is ALWAYS needed
    public static final String COL_ID = "_id";
    
    // These fields can be any other data related fields.
    public static final String COL_WORD = "word";
    public static final String COL_DEFINITION = "definition";
    public static final String COL_DATEADDED = "dateadded";
    
    // For database projection so order is consistent
    public static final String[] FIELDS = { COL_ID, COL_WORD, COL_DEFINITION, COL_DATEADDED };

    /**
     *  The SQL code that relates a Table for storing Words in.
     *
     *  Note that the last row does NOT end in a comma like the others.*
     *  This is a common source of error.
     */
    
    public static final String CREATE_TABLE = 
            "CREATE TABLE " + TABLE_NAME + "(" 
            + COL_ID        + " INTEGER PRIMARY KEY,"
            + COL_WORD      + " TEXT NOT NULL DEFAULT '',"
            + COL_DEFINITION+ " TEXT '',"
            + COL_DATEADDED + " TEXT NOT NULL DEFAULT ''"
            + ")";
    
    // Fields corresponding to database columns
    public long id = -1;
    public String word = "";
    public String definition = "";
    public String dateadded = "";

    /**
     * No need to do anything, fields are already set to default values above 
     */
    public Word() {
        
        
        
    }

    /**
     * Convert information from the database into a Word object 
     */
    public Word(final Cursor cursor) {
        // Indices expected to match order in FIELDS!
        this.id = cursor.getLong(0);
        this.word = cursor.getString(1);
        this.definition = cursor.getString(2);
        this.dateadded = cursor.getString(3);
    }

    /**
     * Return the fields in a ContentValues object, suitable for insertion into the db.
     */
    public ContentValues getContent() {
        
        final ContentValues values = new ContentValues();
        
        // Note the ID is NOT included here
        values.put(COL_WORD, word);
        values.put(COL_DEFINITION, definition);
        values.put(COL_DATEADDED, dateadded);
        
        return values;
        
    }
}
