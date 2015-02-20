package com.stormcloud.woodrow;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.res.Resources.NotFoundException;
import android.util.TypedValue;
import android.widget.RemoteViews;

import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_TEXT_SIZE_SCALE;
import static com.stormcloud.woodrow.prefs.WoodrowPreferences.PREF_TEXT_SIZE_SCALE_DEFAULT;
import static java.lang.Float.parseFloat;


/**
 * Created by schhan on 2/17/15.
 */
public class RemoteViewsUtil {
    
    private static final String METHOD_SET_TEXT_SIZE = "setTextSize";
    private static final String METHOD_SET_BACKGROUND_COLOR = "setBackgroundColor";
    private static final String METHOD_SET_SINGLE_LINE = "setSingleLine";
    private static final String METHOD_SET_ALPHA = "setAlpha";
    private static final String METHOD_SET_COLOR_FILTER = "setColorFilter";
    
    private RemoteViewsUtil() {
        // prohibit instantiation
        
    }
    
    public static void setAlpha(RemoteViews rv, int viewId, int alpha) {
        rv.setInt(viewId, METHOD_SET_ALPHA, alpha);
    }
    
    public static void setColorFilter(RemoteViews rv, int viewId, int color) {
        rv.setInt(viewId, METHOD_SET_COLOR_FILTER, color);
    }
    
    public static void setTextSize(Context context, RemoteViews rv, int viewId, int dimenId) {
        rv.setFloat(viewId, METHOD_SET_TEXT_SIZE, getScaledValue(context, dimenId));
    }
    
    public static void setBackgroundColor(Context context, RemoteViews rv, int viewId, 
                                          int colorAttrId) {
        setBackgroundColor(rv, viewId, getColorValue(context, colorAttrId));
    }
    
    public static void setBackgroundColor(RemoteViews rv, int viewId, int color) {
        rv.setInt(viewId, METHOD_SET_BACKGROUND_COLOR, color);
    }
    
//    private static float getScaledValueInPixel(Context context, int dimenId) {
//        float resValue = getDimension(context, dimenId);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        float prefTextScale = parseFloat(prefs.getString(PREF_TEXT_SIZE_SCALE, PREF_TEXT_SIZE_SCALE_DEFAULT))
//        
//    }
    
    private static float getScaledValue(Context context, int dimenId) {
        float resValue = getDimension(context, dimenId);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        float density = context.getResources().getDisplayMetrics().density;
        float prefTextScale = parseFloat(prefs.getString(PREF_TEXT_SIZE_SCALE,
                PREF_TEXT_SIZE_SCALE_DEFAULT));
        return resValue * prefTextScale / density;
    }
    
    
    private static int getColorValue(Context context, int attrId) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(attrId, outValue, true);
        int colorId = outValue.resourceId;
        return context.getResources().getColor(colorId);
    }
    
    private static float getDimension(Context context, int dimenId) {
        try {
            return context.getResources().getDimension(dimenId);
        } catch (NotFoundException e) {
            // resource might not exist
            return 0f;
        }
    }
    
    public static void setSingleLine(RemoteViews rv, int viewId, boolean singleLine) {
        rv.setBoolean(viewId, METHOD_SET_SINGLE_LINE, singleLine);
    }
    
    public static void setImageFromAttr(Context context, RemoteViews rv, int viewId, int attrResId){
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(attrResId, outValue, true);
        setImage(rv, viewId, outValue.resourceId);
        
    }
    
    public static void setImage(RemoteViews rv, int viewId, int resId) {
        rv.setImageViewResource(viewId, resId); 
    }
}
