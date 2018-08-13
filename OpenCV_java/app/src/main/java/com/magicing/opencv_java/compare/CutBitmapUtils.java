package com.magicing.opencv_java.compare;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Dong on 2018/8/6.
 */

public class CutBitmapUtils {
    private static final String TAG = "CutBitmapUtils";

    public static Bitmap cupBitmap(Bitmap bitmap, int x, int y, int width, int height) {
        Mat img = new Mat();
        // 缩小图片尺寸
        // Bitmap bm = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),true);
        // bitmap->mat
        Utils.bitmapToMat(bitmap, img);
        // 转成CV_8UC3格式
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGBA2RGB);

        // 设置抠图范围的左上角和右下角
        Rect rect = new Rect(x, y, width, height);
        // 生成遮板
        Mat firstMask = new Mat();
        Mat bgModel = new Mat();
        Mat fgModel = new Mat();
        Mat source = new Mat(1, 1, CvType.CV_8U, new Scalar(Imgproc.GC_PR_FGD));
        // 选取抠图区域，返回坐标和宽高（耗时严重）
        Imgproc.grabCut(img, firstMask, rect, bgModel, fgModel, 5, Imgproc.GC_INIT_WITH_RECT);

        Core.compare(firstMask, source, firstMask, Core.CMP_EQ);
        // 抠图
        Mat foreground = new Mat(img.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
        img.copyTo(foreground, firstMask);

        // mat->bitmap
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(foreground, bitmap1);
        return bitmap1;
    }

    /**
     * 裁剪图片并重新装换大小
     *
     * @param posX
     * @param posY
     * @param width
     * @param height
     */
    public static Bitmap imageCut(Bitmap bitmap, int posX, int posY, int width, int height) {
        String outFile = "/storage/emulated/0/MSS/cut/" + "MSS截屏_" + getCurTime() + ".png";
        //原始图像
        Mat image = new Mat();
        // 缩小图片尺寸
        // Bitmap bm = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),true);
        // bitmap->mat
        Utils.bitmapToMat(bitmap, image);
//        Mat image = Imgcodecs.imread(imagePath);
        //截取的区域：参数,坐标X,坐标Y,截图宽度,截图长度
        Rect rect = new Rect(posX, posY, width, height);
        //两句效果一样
        Mat sub = new Mat(image, rect); // Mat sub = image.submat(rect);
        Mat mat = new Mat();
        Size size = new Size(300, 300);
        Imgproc.resize(sub, mat, size);//将人脸进行截图并保存
        Imgcodecs.imwrite(outFile, mat);
        Log.e("Cut", "图片裁切成功，裁切后图片文件为=" + outFile);
        return null;
    }

    public static String getCurTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date(System.currentTimeMillis()));
    }
}
