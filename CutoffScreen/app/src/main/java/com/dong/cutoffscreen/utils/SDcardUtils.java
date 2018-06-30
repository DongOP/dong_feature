package com.dong.cutoffscreen.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Dong on 2018/6/30.
 */

public class SDcardUtils {

    private static final String TAG = "SDcardUtils";
    // 截图保存路径
    public final static String SCREEN_IMAGE_FOLDER = "MSS/screenshot";

    public static String getScreenImageDir() {
        if (!SDcardUtils.isSDcardMounted()) {
            return "";
        }
        return String.format("%s/%s/", SDcardUtils.getSDCardPath(), SCREEN_IMAGE_FOLDER);
    }

    public static boolean makesureFileDirExists(String dirName) {
        File file = new File(dirName);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static boolean isSDcardMounted() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    // 将byte[]写入到文本文件中
    public static void writeToFile(byte[] byteContent, String filePath, String fileName) {
        makesureFileDirExists(filePath);

        String strFilePath = filePath + fileName;
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fout = new FileOutputStream(file);
            fout.write(byteContent);
            fout.close();
            Utils.logd("WXCode", "获取并写入成功，图片byte[]文件保存在=" + strFilePath);
        } catch (Exception e) {
            Utils.loge("WXCode", "Error on write File:" + e);
        }
    }

    // 读取本地文件下的byte[]文件
    public static Bitmap readLocalByte(String filePath, String fileName) {
        File file = new File(filePath, fileName);
        if (file.exists()) {
            try {
                FileInputStream is = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if (null != bitmap) {
                    return bitmap;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将字节数组转换为Bitmap对象
     *
     * @param bytes 图片byte[]数据
     */
    public static Bitmap getPicFromBytes(byte[] bytes) {
        if (bytes != null) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return null;
    }

}
