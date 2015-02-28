package com.stormcloud.woodrow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//import android.support.v4.app.FragmentManager;

import com.stormcloud.woodrow.database.DatabaseHandler;
import com.stormcloud.woodrow.model.Word;

/**
 * Created by schhan on 2/20/15.
 */
public class WordListActivity extends FragmentActivity 
        implements WordListFragment.OnWordSelected {
    
    private boolean mIsTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        mIsTwoPane = false;
        
        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_activity, menu);
        return true;
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        if (R.id.newWord == item.getItemId()) {
            result = true;
            //Create a new word for the db
            Word w = new Word();
            DatabaseHandler.getInstance(this).putWord(w);
            // Open a new fragment with the new id
            onWordSelected(w.id);
        }
        
        return result;
    }

    @Override
    public void onWordSelected(long id) {

        if(mIsTwoPane) {
            
        } else {
            // In single-pane mode, start the detail activity for the selected item ID.
            Intent detailIntent = new Intent(this, WordDetailActivity.class);
            detailIntent.putExtra(WordDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
