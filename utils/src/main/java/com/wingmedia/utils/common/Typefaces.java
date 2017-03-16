package com.wingmedia.utils.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

/**
 * Type face util
 * Created by neo on 3/24/2016.
 */
public class Typefaces {
    private static final String TAG = "Typefaces";
    private static final Hashtable<String, Typeface> CACHE = new Hashtable<>();

    public static Typeface get(Context c, String font) {
        synchronized (CACHE) {
            if (!CACHE.containsKey(font)) {
                try {
                    String path = "fonts/" + font + "." + "ttf";
                    Typeface t = Typeface.createFromAsset(c.getAssets(), path);
                    CACHE.put(font, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + font
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return CACHE.get(font);
        }
    }
}
