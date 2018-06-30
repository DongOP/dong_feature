package com.dong.cutoffscreen.utils;

import android.util.Log;


/**
 *
 */

public class Utils {
    public static final boolean isDebug = true;
    
    public static void logd(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void loge(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

}
