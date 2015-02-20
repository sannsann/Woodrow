package com.stormcloud.woodrow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.util.Log;
import android.widget.ToggleButton;

/**
 * Need this to know when the screen turns off so we can update Woodrow Widget.
 * Created by schhan on 2/10/15.
 */
public class WoodrowScreenReceiver extends BroadcastReceiver {

    public final String TAG = "WoodrowScreenReceiver";
    public static boolean wasScreenOn = true;

    private boolean screenOff;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // DO WHATEVER YOU NEED TO DO HERE

            // Turn the light on
            Log.e(TAG, "SCREEN TURNED ON");

            wasScreenOn = false;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // AND DO WHATEVER YOU NEED TO DO HERE
            Log.e(TAG, "SCREEN TURNED ON");
            wasScreenOn = true;
        }


        Intent i = new Intent(context, WoodrowUpdateService.class);
        i.putExtra("screen_state", screenOff);
        context.startService(i);
    }
}
