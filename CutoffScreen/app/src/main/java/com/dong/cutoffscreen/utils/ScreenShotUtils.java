package com.dong.cutoffscreen.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Dong on 2018/6/30.
 */

public class ScreenShotUtils {

    private static final String TAG = "ScreenShotUtils";
    private static final ScreenShotUtils instance = new ScreenShotUtils();

    public static ScreenShotUtils getInstance(){
        return instance;
    }

    public void doScreenshot(Activity activity) {
        if (null == activity) {
            Utils.logd(TAG, "The activity sis null.");
            return;
        }
        Utils.logd(TAG, "doScreenshot.");
        // 获取屏幕
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            try {
                // 确保目录存在
                SDcardUtils.makesureFileDirExists(SDcardUtils.getScreenImageDir());
                // 图片文件路径 /storage/emulated/0/MSS/screenshot/20180630123322.png
                String filePath = SDcardUtils.getScreenImageDir() + TimeUtils.getCurTime() + ".png";
                Utils.loge(TAG, "filePath=" + filePath);

                File file = null;
                try {
                    file = new File(filePath);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
            }
        }
    }
}
