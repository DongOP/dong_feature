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
    private static final ScreenShotUtils mInstance = new ScreenShotUtils();

    public static ScreenShotUtils getInstance() {
        return mInstance;
    }

    // 截图并保存到本地MSS/screenshot目录下
    public void doScreenshot(Activity activity) {
        if (null == activity) {
            Utils.logd(TAG, "The activity is null.");
            return;
        }
        Utils.logd(TAG, "doScreenshot.");
        // 获取屏幕
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        // 将截图存放到本地
        if (bmp != null) {
            try {
                // 确保目录存在
                SDcardUtils.makesureFileDirExists(SDcardUtils.getScreenImageDir());
                // 截图的名称
                String imageName = "MSS截屏_" + TimeUtils.getCurTime() + ".png";
                // 图片文件路径 /storage/emulated/0/MSS/screenshot/MSS截屏_20180630123322.png
                String filePath = SDcardUtils.getScreenImageDir() + imageName;
                Utils.logd(TAG, "filePath=" + filePath);

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

    /**
     * 从Stream中获取图片某个坐标的像素值
     */
    public int getStreamPixel(Activity activity, int x, int y) {
        if (null == activity) {
            Utils.logd(TAG, "The activity is null.");
            return -1;
        }
        // 获取屏幕
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        int px = bmp.getPixel(x, y);
        Utils.logd(TAG, "像素值=" + px);
        return px;
    }

}
