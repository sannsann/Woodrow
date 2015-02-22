package com.stormcloud.woodrow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;

import com.stormcloud.woodrow.WordListFragment.OnWordClickedListener;

/**
 * Created by schhan on 2/20/15.
 */
public class WordListActivity extends FragmentActivity implements OnWordClickedListener {
    
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
