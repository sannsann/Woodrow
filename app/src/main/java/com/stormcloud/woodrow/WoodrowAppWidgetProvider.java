package com.stormcloud.woodrow;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.RemoteViews;

import static android.graphics.Color.alpha;
import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;
import static com.stormcloud.woodrow.RemoteViewsUtil.setAlpha;
import static com.stormcloud.woodrow.RemoteViewsUtil.setColorFilter;
import static com.stormcloud.woodrow.RemoteViewsUtil.setImageFromAttr;
//import static com.stormcloud.woodrow.RemoteViewsUtil.setTextC;
import static com.stormcloud.woodrow.Theme.getCurrentThemeId;
import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_BACKGROUND_COLOR;
import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_BACKGROUND_COLOR_DEFAULT;
import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_HEADER_THEME;
import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_HEADER_THEME_DEFAULT;
import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_SHOW_HEADER;

public class WoodrowAppWidgetProvider extends AppWidgetProvider {

//    public final static String TAG = "WoodrowWidget";

    @Override
    public void onUpdate(Context baseContext, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // appWidgetIds = You can have multiple of the same widget or different versions of the
        // widget on the homescreen so you must make sure to update them all
        
        // super.onUpdate(context, appWidgetManager, appWidgetIds); 
        // The super fucks up the rest of the code.
        // Dont use it.
        
        int themeId = getCurrentThemeId(baseContext, PREF_HEADER_THEME, PREF_HEADER_THEME_DEFAULT);
        Context context = new ContextThemeWrapper(baseContext, themeId);

        for (int widgetId : appWidgetIds) {

//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//            PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.woodrow_widget);
//            rv.setOnClickPendingIntent(R.id.overflow_menu, pending);

//            configureBackground(context, rv);
            configureActionBar(context, rv);
            appWidgetManager.updateAppWidget(widgetId, rv);
        }

    }
    
    private void configureBackground(Context context, RemoteViews rv) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.getBoolean(PREF_SHOW_HEADER, true)) {
            rv.setViewVisibility(com.stormcloud.woodrow.R.id.action_bar, View.VISIBLE);
        } else {
            rv.setViewVisibility(com.stormcloud.woodrow.R.id.action_bar, View.GONE);
        }
        int color = prefs.getInt(PREF_BACKGROUND_COLOR, PREF_BACKGROUND_COLOR_DEFAULT);
        int opaqueColor = Color.rgb(red(color), green(color), blue(color));
        setColorFilter(rv, com.stormcloud.woodrow.R.id.background_image, opaqueColor);
        setAlpha(rv, com.stormcloud.woodrow.R.id.background_image, alpha(color));
    }
    
    private void configureActionBar(Context context, RemoteViews rv) {

//        rv.setOnClickPendingIntent(R.id.calendar_current_date, createOpenCalendarPendingIntent(context));
//        String formattedDate = DateUtils.formatDateTime(context, System.currentTimeMillis(),
//                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY);
//        rv.setTextViewText(R.id.calendar_current_date, formattedDate.toUpperCase(Locale.getDefault()));
//        setTextColorFromAttr(context, rv, R.id.calendar_current_date, R.attr.header);
//        setActionIcons(context, rv);
//        .
//        .
//        .
//        Intent intent = CalendarIntentUtil.createNewEventIntent();
//        if (isIntentAvailable(context, intent)) {
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            rv.setOnClickPendingIntent(R.id.add_event, pendingIntent);
//        } else {
//            rv.setViewVisibility(R.id.add_event, View.GONE);
//        }       
        
        setActionIcons(context, rv);
        Intent startConfigIntent = new Intent(context, WoodrowConfigurationActivity.class);
        PendingIntent menuPendingIntent = PendingIntent.getActivity(context, 0, startConfigIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.overflow_menu, menuPendingIntent);
    }
    
    private void setActionIcons(Context context, RemoteViews rv) {
        setImageFromAttr(context, rv, R.id.add_vocab, R.attr.header_action_add_vocab);
        setImageFromAttr(context, rv, R.id.overflow_menu, R.attr.header_action_overflow);
        int themeId = getCurrentThemeId(context, PREF_HEADER_THEME, PREF_HEADER_THEME_DEFAULT);
        int alpha = 255;
        if (themeId == com.stormcloud.woodrow.R.style.Theme_Woodrow_Dark || themeId == com.stormcloud.woodrow.R.style.Theme_Woodrow_Light) {
            alpha = 154;
        }
        setAlpha(rv, R.id.add_vocab, alpha);
        setAlpha(rv, R.id.overflow_menu, alpha);
        
    }
}
