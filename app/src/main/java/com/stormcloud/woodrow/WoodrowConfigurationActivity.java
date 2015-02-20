package com.stormcloud.woodrow;

import android.annotation.TargetApi;
import android.os.Build;
import android.preference.PreferenceActivity;

import java.util.List;

/**
 * Created by schhan on 2/18/15.
 */
public class WoodrowConfigurationActivity extends PreferenceActivity {
    
    private static final String PREFERENCES_PACKAGE_NAME = "com.stormcloud.woodrow.prefs";
    
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preferences_header, target);
        
    }
    
    @Override
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected boolean isValidFragment(String fragmentName) {
     
        if (fragmentName.startsWith(PREFERENCES_PACKAGE_NAME)) {
            return true;
        }
        return super.isValidFragment(fragmentName);
        
    }
}
