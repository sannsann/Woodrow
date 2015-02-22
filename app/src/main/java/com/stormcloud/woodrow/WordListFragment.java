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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
        
//import com.stormcloud.woodrow.database.DatabaseHandler;
import com.stormcloud.woodrow.database.WordProvider;
import com.stormcloud.woodrow.model.Word;

/**
 * Created by schhan on 2/20/15.
 */
public class WordListFragment extends ListFragment 
        implements LoaderManager.LoaderCallbacks<Cursor> {
    
    public static final String TAG = "WordListFragment";
    
    SimpleCursorAdapter mAdapter;
    
    OnWordClickedListener mWordClickListener;
    
    public interface OnWordClickedListener {
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
        
        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.word_listitem, null, new String[] {
                Word.COL_WORD, Word.COL_DATEADDED,
                Word.COL_DEFINITION }, new int[] { R.id.cardWord,
                R.id.cardDateAdded, R.id.cardDefinition }, 0);
        
        setListAdapter(mAdapter);
        
        // Start out with a progress indicator.
//        setListShown(false);
        
        // Prepare the loader. Either reconnect with an existing one or start a new one.
//        getLoaderManager().initLoader(0, null, this);
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setListAdapter();

        // Load the content; Prepare the loader.
        // Either re-connect with an existing one, or start a new one.
        getLoaderManager().initLoader(0, null, this);
        
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.word_listitem, null, new String[] {
                Word.COL_WORD, Word.COL_DATEADDED,
                Word.COL_DEFINITION }, new int[] { R.id.cardWord,
                R.id.cardDateAdded, R.id.cardDefinition }, 0);
        
        setListAdapter(adapter);
                
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_word_list, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mWordClickListener = (OnWordClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + 
                    " must implement OnWordClickedListener interface");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
        Log.i("WordListFragment", "Item clicked: " + id);
        
        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected
//        mWordClickListener.onWordClicked(getListAdapter().getItemId(position));
    }

    
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
        
        // This list should now be shown.
        if (isResumed()) {
//            setListShown(true);
        } else {
//            setListShown(false);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed. We need to make sure we are not longer
        // using it.
        mAdapter.swapCursor(null);
    }
    
}
