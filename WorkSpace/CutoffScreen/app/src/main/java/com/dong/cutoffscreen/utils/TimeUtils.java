package com.dong.cutoffscreen.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Dong on 2018/6/30.
 */

public class TimeUtils {
    private static final String TAG = "TimeUtils";

    public static String getCurTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date(System.currentTimeMillis()));
    }


}
