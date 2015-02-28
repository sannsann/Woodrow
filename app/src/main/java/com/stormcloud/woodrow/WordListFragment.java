package com.stormcloud.woodrow;

import android.app.Activity;
//import android.content.CursorLoader;
import android.database.Cursor;
//import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
        
//import com.stormcloud.woodrow.database.DatabaseHandler;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;
import com.stormcloud.woodrow.database.DatabaseHandler;
import com.stormcloud.woodrow.database.WordProvider;
import com.stormcloud.woodrow.model.Word;
import com.stormcloud.woodrow.widget.WoodrowSwipeListViewListener;

/**
 * Created by schhan on 2/20/15.
 */
public class WordListFragment extends ListFragment 
        implements LoaderManager.LoaderCallbacks<Cursor> {
    
    public static final String TAG = "WordListFragment";
    
    SimpleCursorAdapter mAdapter;
    
    OnWordSelected mWordSelectedListener;
    
    public interface OnWordSelected {
        public void onWordSelected(long id);
        
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        // Give some text to display if there is no data. 
        // In a real application this would come from a resource.
//        setEmptyText("Blank Slate");
        
        // We have a menu item to show in the action bar.
        setHasOptionsMenu(true);

        // Start out with a progress indicator.
//        setListShown(false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the content; Prepare the loader.
        // Either re-connect with an existing one, or start a new one.
        getLoaderManager().initLoader(0, null, this);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_word_list, null);
        
        View view = inflater.inflate(R.layout.fragment_word_list, container, false);
        
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mWordSelectedListener = (OnWordSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + 
                    " must implement OnWordClickedListener interface");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected
        mWordSelectedListener.onWordSelected(id);


        // Let's try deleting items!
//        DatabaseHandler.getInstance(getActivity()).removeWord(id);

    }


    /**
     * Begin LoadManager.LoadCallbacks
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // This is called when a new Loader needs to be created.
        // This sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are 
        // currently filter.
        
        return new CursorLoader(getActivity(),
                WordProvider.URI_WORDS, Word.FIELDS, null, null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in. 
        // (The framework will take care of closing the old cursor once we return.)
//        mAdapter.swapCursor(data);

        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.word_listitem, data, new String[] {
                Word.COL_WORD, Word.COL_DATEADDED,
                Word.COL_DEFINITION }, new int[] { R.id.cardWord,
                R.id.cardDateAdded, R.id.cardDefinition }, 0);
        
        
        ListView lv = getListView();
        lv.setAdapter(mAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed. We need to make sure we are not longer
        // using it.
        mAdapter.swapCursor(null);
    }
    
}
