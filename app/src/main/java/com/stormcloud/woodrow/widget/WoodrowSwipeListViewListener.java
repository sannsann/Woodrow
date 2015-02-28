package com.stormcloud.woodrow.widget;

import android.content.Context;

import com.fortysevendeg.swipelistview.SwipeListViewListener;
import com.stormcloud.woodrow.database.DatabaseHandler;

/**
 * Created by schhan on 2/22/15.
 */
public class WoodrowSwipeListViewListener implements SwipeListViewListener {

    Context mContext;
    public WoodrowSwipeListViewListener(Context context) {
        
        this.mContext = context;
        
    }
    
    
    @Override
    public void onOpened(int i, boolean b) {

    }

    @Override
    public void onClosed(int i, boolean b) {

    }

    @Override
    public void onListChanged() {

    }

    @Override
    public void onMove(int i, float v) {

    }

    @Override
    public void onStartOpen(int i, int i2, boolean b) {

    }

    @Override
    public void onStartClose(int i, boolean b) {

    }

    @Override
    public void onClickFrontView(int i) {

    }

    @Override
    public void onClickBackView(int i) {

    }

    @Override
    public void onDismiss(int[] reverseSortedPositions) {

        DatabaseHandler.getInstance(mContext).removeWord(reverseSortedPositions[0]);
        
    }

    @Override
    public int onChangeSwipeMode(int i) {
        return 0;
    }

    @Override
    public void onChoiceChanged(int i, boolean b) {

    }

    @Override
    public void onChoiceStarted() {

    }

    @Override
    public void onChoiceEnded() {

    }

    @Override
    public void onFirstListItem() {

    }

    @Override
    public void onLastListItem() {

    }
}
