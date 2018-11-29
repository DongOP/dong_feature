package com.magicing.opencv_java.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Dong on 2018/9/21.
 */

public class MockUtils {

    public static void mockClickHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }
}
