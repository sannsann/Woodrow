package com.stormcloud.woodrow.model;

import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.stormcloud.woodrow.WordDetailActivity;
import com.stormcloud.woodrow.database.DatabaseHandler;

/**
 * Created by schhan on 2/23/15.
 */
public class WordIntentUtil {

//    static Intent createWordDetailIntent(Context context, long id) {
//        Intent intent = createWordIntent(context);
//        DatabaseHandler.getInstance(context).getWord(id);
//
//        // Goal crete an intent that starts a WordDetailActivity
//        // Activity needs an intent to start: Start activity using this intent.
//    }
    
    static PendingIntent createAddWordEventPendingIntent(Context context) {
        Intent intent = createWordIntent(context);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
 

    public static Intent createWordIntent(Context context) {
        Intent intent = new Intent(context, WordDetailActivity.class);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;

    }
}
