package com.stormcloud.woodrow;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public enum Theme {

    WHITE(R.style.Theme_Woodrow_White),
    LIGHT(R.style.Theme_Woodrow_Light),
    DARK(R.style.Theme_Woodrow_Dark),
    BLACK(R.style.Theme_Woodrow_Black);

    private final int themeResId;

    private Theme(int themeResId) {
        this.themeResId = themeResId;
    }

    public static int getCurrentThemeId(Context context, String prefKey, String prefDefault) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return Theme.valueOf(prefs.getString(prefKey, prefDefault)).themeResId;
    }

}
