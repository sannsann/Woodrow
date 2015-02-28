package com.stormcloud.woodrow.model;

import android.widget.RemoteViews;

import java.util.List;

/**
 * Created by schhan on 2/24/15.
 */
public interface IWordVisualizer<T extends Word> {
    
    RemoteViews getRemoteView(Word word);
    
    int getViewTypeCount();
    
    List<T> getWordEntries();
    
    Class<? extends T> getSupportedWordEntryType();
}
