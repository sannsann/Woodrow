package com.stormcloud.woodrow;

import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.widget.RemoteViewsService;

import static com.stormcloud.woodrow.Theme.getCurrentThemeId;
import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_ENTRY_THEME;
import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_ENTRY_THEME_DEFAULT;

/**
 * Created by schhan on 2/23/15.
 */
public class WoodrowWidgetService extends RemoteViewsService {
    
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Context appContext = getApplicationContext();
        int currentThemeId = getCurrentThemeId(appContext, PREF_ENTRY_THEME, PREF_ENTRY_THEME_DEFAULT);
        ContextThemeWrapper context = new ContextThemeWrapper(appContext, currentThemeId);
        return new WordRemoteViewsFactory(context);
    }
}
