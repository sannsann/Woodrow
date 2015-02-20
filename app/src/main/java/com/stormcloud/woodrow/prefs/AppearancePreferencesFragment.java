package com.stormcloud.woodrow.prefs;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.stormcloud.woodrow.R;

/**
 * Created by schhan on 2/17/15.
 */
public class AppearancePreferencesFragment extends PreferenceFragment {
    
    private static final String BACKGROUND_COLOR_DIALOG = "backgroundColorDialog";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_appearance);
    }
    
    @Override
    public void onPause() {
        super.onPause();

    }
}
