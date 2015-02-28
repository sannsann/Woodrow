package com.stormcloud.woodrow.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.stormcloud.woodrow.database.WordProvider;

import java.util.List;

/**
 * Created by schhan on 2/24/15.
 */
public class VocabWordVisualizer implements IWordVisualizer<Word> {
    
    private final Context context;
//    private final WordProvider wordProvider;
    private final SharedPreferences prefs;

    public VocabWordVisualizer(Context context) {
        this.context = context;
//        wordProvider = new WordProvider(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public RemoteViews getRemoteView(Word word) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public List<Word> getWordEntries() {
        return null;
    }

    @Override
    public Class<? extends Word> getSupportedWordEntryType() {
        return null;
    }
}
