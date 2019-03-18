package com.magicing.opencv_java.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class CodeFormatUtils {

    /**
     * 将Bitmap图片Base64编码
     *
     * @param bitmap
     * @return String 类型的图片数据
     */
    public static String bitmapToString(Bitmap bitmap) {
        String strbm = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 参数如果为100那么就不压缩
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
            byte[] bytes = baos.toByteArray();
            strbm = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strbm;
    }

    /**
     * 将String类型图片数据，以Base64解码成Bitmap
     *
     * @return Bitmap图片
     */
    public static Bitmap stringToBitmap(String msg) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(msg, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
