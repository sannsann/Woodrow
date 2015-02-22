package com.stormcloud.woodrow.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.stormcloud.woodrow.model.Word;

public class WordProvider extends ContentProvider {
    
    // All URIs share these parts
    public static final String AUTHORITY = "com.stormcloud.woodrow.provider";
    public static final String SCHEME = "content://";
    
    // URIs
    // Used for all words
    public static final String WORDS = SCHEME + AUTHORITY + "/word";
    public static final Uri URI_WORDS = Uri.parse(WORDS);
    
    // Used for a single word, just add the id to the end
    public static final String WORD_BASE = WORDS + "/";
    
    public WordProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor result = null;
        
        if (URI_WORDS.equals(uri)) {
            result = DatabaseHandler
                    .getInstance(getContext())
                    .getReadableDatabase()
                    .query(Word.TABLE_NAME, Word.FIELDS, null, null, null,
                            null, null, null);
            
            result.setNotificationUri(getContext().getContentResolver(), URI_WORDS);
        }
        else if (uri.toString().startsWith(WORD_BASE)) {
            final long id = Long.parseLong(uri.getLastPathSegment());
            result = DatabaseHandler
                    .getInstance(getContext())
                    .getReadableDatabase()
                    .query(Word.TABLE_NAME, Word.FIELDS, 
                            Word.COL_ID + " IS ? ",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);

            result.setNotificationUri(getContext().getContentResolver(), URI_WORDS);
        }
        else {
        
            throw new UnsupportedOperationException("Not yet implemented");
            
        }
        
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
