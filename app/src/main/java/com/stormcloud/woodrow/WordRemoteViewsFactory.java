package com.stormcloud.woodrow;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.CursorLoader;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.stormcloud.woodrow.database.DatabaseHandler;
import com.stormcloud.woodrow.database.WordProvider;
import com.stormcloud.woodrow.model.IWordVisualizer;
import com.stormcloud.woodrow.model.VocabWordVisualizer;
import com.stormcloud.woodrow.model.Word;

import java.util.ArrayList;
import java.util.List;

import static com.stormcloud.woodrow.Theme.getCurrentThemeId;
import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_ENTRY_THEME_DEFAULT;
import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_ENTRY_THEME;
//import static com.stormcloud.woodrow.prefs.WoodrowPreferences
//import static com.stormcloud.woodrow.prefs.WoodrowPreferences

/**
 * Created by schhan on 2/23/15.
 */

public class WordRemoteViewsFactory implements RemoteViewsFactory {
    
    private final Context context;
    private final List<Word> wordEntries;
    private final List<IWordVisualizer<?>> wordProviders;


    public WordRemoteViewsFactory(Context context) {
        this.context = context;
        wordProviders = new ArrayList<>();
        wordProviders.add(new VocabWordVisualizer(context));
        wordEntries = new ArrayList<>();
        
        // Populate wordEntries using items from DB
    }

    @Override
    public void onCreate() {
//        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.woodrow_widget);
//        rv.setPendingIntentTemplate(R.id.widget_vocab_list), ;

//        Cursor cursor = new CursorLoader(getActivity(),
//                WordProvider.URI_WORDS, Word.FIELDS, null, null,
//                null);


        final SQLiteDatabase db = DatabaseHandler.getInstance(context).getReadableDatabase();
        final Cursor cursor = db.query(Word.TABLE_NAME,
                Word.FIELDS, null, null, null,
                null, null, null);

        while(cursor.moveToNext()) {
            Word word = new Word(cursor);
            wordEntries.add(word);
        }
//        Word firstWord = new Word();
//        firstWord.word = "onus";
//        firstWord.definition = "used to refer to something that is one's duty or obligation";
//        firstWord.dateadded = "02/22/2022";

        // We would probably add all the entries into this list after retrieving from the DB
//        wordEntries.add(firstWord);
        

    }

    @Override
    public void onDestroy() { wordEntries.clear(); }

    @Override
    public int getCount() { return wordEntries.size(); }

    @Override
    public RemoteViews getViewAt(int position) {
//        if (position < wordEntries.size()) {
//            Word word = wordEntries.get(position);
////            if (word instanceof )
//            for (IWordVisualizer<?> wordProvider : wordProviders) {
//                if(word.getClass().isAssignableFrom(wordProvider.getSupportedWordEntryType())) {
//                    return wordProvider.getRemoteView(word);
//                }
//            }
//            RemoteViews
            
            Word word = wordEntries.get(position);
        
            RemoteViews card = new RemoteViews(context.getPackageName(), R.layout.word_listitem);
            card.setTextViewText(R.id.cardWord, word.word);
            card.setTextViewText(R.id.cardDateAdded, word.dateadded);
            card.setTextViewText(R.id.cardDefinition, word.definition);
//
            return card;
//        }
        
 


//        return null;
    }

    @Override
    public void onDataSetChanged() {

        context.setTheme(getCurrentThemeId(context, PREF_ENTRY_THEME, PREF_ENTRY_THEME_DEFAULT));
        wordEntries.clear();
        List<Word> words = new ArrayList<>();
        for (IWordVisualizer<?> wordProvider : wordProviders) {
            words.addAll(wordProvider.getWordEntries());
        }
        
        updateWidgetVocabularyList(words);

    }
    
    private void updateWidgetVocabularyList(List<Word> wordList) {
        if (!wordList.isEmpty()) {

        }
        
    }
    
    

    @Override
    public RemoteViews getLoadingView() {return null; }

    @Override
    public int getViewTypeCount() {
        int result = 3; // we have 3 because of the "left", "right" and "center" day headers
//        for (IEventVisualizer<?> eventProvider : eventProviders) {
//            result += eventProvider.getViewTypeCount();
//        }
        return result;
    }

    @Override
    public long getItemId(int position) { return position; }

    /**
     * Not sure of use for this method...  02/23/2015
     * @return
     */
    @Override
    public boolean hasStableIds() { return true; }
}
