package com.stormcloud.woodrow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stormcloud.woodrow.database.DatabaseHandler;
import com.stormcloud.woodrow.model.Word;

/**
 * Created by schhan on 2/20/15.
 */
public class WordDetailFragment extends Fragment {

    public static final String TAG = "WordDetailFragment";
    
    /**
     * The fragment argument representing the item ID that this fragment represents 
     */
    public static final String ARG_ITEM_ID = "item_id";
    

    /**
     * The Word this fragment is presenting 
     */
    private Word mItem;

    /**
     * The UI elements showing the details of the Word* 
     */
    private TextView tvWord;
    private TextView tvDateAdded;
    private TextView tvDefinition;
            
   
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     * (e.g. upon screen orientation changes). 
     */
//    public WordDetailFragment() {}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider
            
            mItem = DatabaseHandler.getInstance(getActivity()).getWord(getArguments().getLong(ARG_ITEM_ID));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);
        
        if (mItem != null) {
            tvWord = (TextView) view.findViewById(R.id.textWord);
            tvWord.setText(mItem.word);
            
            tvDateAdded = (TextView) view.findViewById(R.id.textDateAdded);
            tvDateAdded.setText(mItem.dateadded);
            
            tvDefinition = (TextView) view.findViewById(R.id.textDefinition);
            tvDefinition.setText(mItem.definition);
        }
        
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        updateWordFromUI();
    }
    
    private void updateWordFromUI() {
        if(mItem != null) {
            mItem.word = tvWord.getText().toString();
            mItem.dateadded = tvDateAdded.getText().toString();
            mItem.definition = tvDefinition.getText().toString();
            
            DatabaseHandler.getInstance(getActivity()).putWord(mItem);
        }
        
    }
}
