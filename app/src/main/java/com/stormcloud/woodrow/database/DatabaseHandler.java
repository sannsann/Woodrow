package com.stormcloud.woodrow.database;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stormcloud.woodrow.model.Word;

import java.util.List;

/**
 * Created by schhan on 2/20/15.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vocabulary";

    private static DatabaseHandler mHandler;
    private final Context mContext;

    public static DatabaseHandler getInstance(final Context context) {
        if (mHandler == null) {
            mHandler = new DatabaseHandler(context);
        }
        return mHandler;
    }

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // Good idea to use the process context here
        this.mContext = context.getApplicationContext();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
        db.execSQL(Word.CREATE_TABLE);

//        Word word = new Word();
//        word.word = "onus";
//        word.definition = "used to refer to something that is one's duty or responsibility";
//        word.dateadded = "02212015";
//        db.insert(Word.TABLE_NAME, null, word.getContent());
//
//        Word word1 = new Word();
//        word1.word = "test";
//        word1.definition = "an example";
//        word1.dateadded = "02212015";
//        db.insert(Word.TABLE_NAME, null, word1.getContent());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Left alone for now
    }

    /**
     * synchronized methods because we are going to use loaders to load the data,
     * which run on separate threads. We want to make sure that only one thread
     * is reading/writing at any given time.
     */

    public synchronized Word getWord(final long id) {

        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(Word.TABLE_NAME,
                Word.FIELDS, Word.COL_ID + " IS ?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Word item = null;
        if (cursor.moveToFirst()) {
            item = new Word(cursor);
        }
        cursor.close();

        return item;
    }
    
//    public Word getRandomWord() {
//        final SQLiteDatabase db = this.getReadableDatabase();
//
//        // Build our db query query
//
//        // Get the row count of our db
////        db.query()
////
//        final Cursor = db.query(Word.TABLE_NAME,
//                Word.FIELDS, Word.COL_ID + " IS ?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//
//    }

    public long getDBRowCount() {

        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(Word.TABLE_NAME,
                Word.FIELDS, null,
                null, null, null, null, null);
        
        long count = cursor.getCount();
        
        cursor.close();

        return count;
    }

    /**
     * Don't have separate insert and update methods
     * If the program wants to save a word, then it wants to save it regardless if
     * it is already present in the db or not.
     * <p/>
     * Do we need to rethink this? 2/20/15 SCC*
     */

    public synchronized boolean putWord(final Word word) {

        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        if (word.id > -1) {
            result += db.update(Word.TABLE_NAME, word.getContent(),
                    Word.COL_ID + " IS ?",
                    new String[]{String.valueOf(word.id)});
        }

        if (result > 0) {
            success = true;
        } else {
            // Update failed or wasn't possible, insert instead
            final long id = db.insert(Word.TABLE_NAME, null,
                    word.getContent());

            if (id > -1) {
                word.id = id;
                success = true;
            }
        }

        if (success) {
            notifyProviderOnWordChange();
        }

        return success;
    }

//    public synchronized boolean updateWord(final Word word) {
//        return false;
//        
//    }
//
//    public synchronized int removeWord(final Word word) {
//
//        final SQLiteDatabase db = this.getWritableDatabase();
//        final int result = db.delete(Word.TABLE_NAME,
//                Word.COL_ID + " IS ?",
//                new String[]{Long.toString(word.id)});
//
//        // result is the number of rows that were deleted.
//        // In this case it should never be anything except zero or one.
//        if (result > 0) {
//            notifyProviderOnWordChange();
//        }
//        return result;
//
//    }
    
    public synchronized int removeWord(final long id) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final int result = db.delete(Word.TABLE_NAME,
                Word.COL_ID + " IS ?",
                new String[]{Long.toString(id)});

        // result is the number of rows that were deleted.
        // In this case it should never be anything except zero or one.
        if (result > 0) {
            notifyProviderOnWordChange();
        }
        return result;
    }

    private void notifyProviderOnWordChange() {
        mContext.getContentResolver().notifyChange(
                WordProvider.URI_WORDS, null, false);
    }


//    public List<Word> getAllWords() {}
}
