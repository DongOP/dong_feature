package com.dong.cutoffscreen.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Dong on 2018/7/6.
 */

public class ImageUtils {
    private static final String TAG = "ImageUtils";
    private static final ImageUtils mInstance = new ImageUtils();

    // 通过单例模式获取实例
    public static ImageUtils getInstance() {
        return mInstance;
    }

    /**
     * 根据坐标获取图片RGBA信息
     *
     * @param x x
     * @param y
     */
    public void getImageRGBA(Bitmap bitmap, int x, int y) {
        // 获取图片坐标点的颜色
        int color = bitmap.getPixel(x, y);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int a = Color.alpha(color);
        Utils.logd(TAG, "r=" + r + ",g=" + g + ",b=" + b + ",a=" + a);
    }

    /**
     * 压缩图片比例和质量
     *
     * @param image 需要压缩的图片
     * @return 压缩后的图片
     */
    public Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        // 判断如果图片大于1M,避免内存溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            // 压缩50%，压缩后存放到baos中
            image.compress(Bitmap.CompressFormat.PNG, 50, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 设置高度为800f
        float hh = 800f;
        // 设置宽度为500f
        float ww = 500f;
        //缩放比,be=1表示不缩放
        int be = 1;
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        // 压缩好比例大小后再进行质量压缩
        return compressImageQuality(bitmap);
    }

    /**
     * 压缩图片比例和质量
     *
     * @param file 需要压缩的图片文件
     * @return 压缩后的图片
     */
    public Bitmap compressImage(Bitmap image, File file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);
        image.compress(Bitmap.CompressFormat.PNG, 100, fout);
        // 判断如果图片大于1M,避免内存溢出
//        if (baos.toByteArray().length / 1024 > 1024) {
//            baos.reset();
//            // 压缩50%，压缩后存放到baos中
//            image.compress(Bitmap.CompressFormat.PNG, 50, baos);
//        }
        FileInputStream fis = new FileInputStream(file);
        if (null == fis) {
            Utils.logd(TAG, "File is null.");
            return null;
        }

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;

//        Bitmap bitmap = BitmapFactory.decodeStream(fis, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 设置高度为800f
        float hh = 800f;
        // 设置宽度为500f
        float ww = 500f;
        //缩放比,be=1表示不缩放
        int be = 1;
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
//        isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(fis, null, newOpts);
        // 压缩好比例大小后再进行质量压缩
        return compressImageQuality(bitmap);
    }

    /**
     * 压缩图片质量
     *
     * @param image 需要质量压缩的图片
     * @return 压缩完成的图片
     */
    private Bitmap compressImageQuality(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，100表示不压缩，压缩后存放到baos中
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        int options = 100;
        // 确保压缩图片小于100kb
        while (baos.toByteArray().length / 1024 > 60) {
            baos.reset();
            // 按照options压缩
            image.compress(Bitmap.CompressFormat.PNG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

}
