package com.magicing.opencv_java.compare;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * 使用 OpenCV 比较图片、矩阵的相似度
 * 当 double 返回值 = 1 时，图片一样；
 * 否则不一样，返回值越接近 1 时则说明相似度越高。
 *
 * Created by Dong on 2018/8/5.
 */

public class CompareUtils {

    public static double comPareHist(Bitmap mBitmap1, Bitmap mBitmap2) {
        Mat mat1 = new Mat();
        Mat mat2 = new Mat();
        Utils.bitmapToMat(mBitmap1, mat1);
        Utils.bitmapToMat(mBitmap2, mat2);
        return comPareHist(mat1, mat2);
    }

    /**
     * 比较两个矩阵的相似度
     *
     * @param mat1
     * @param mat2
     * @return
     */
    public static double comPareHist(Mat mat1, Mat mat2) {
        Mat srcMat = new Mat();
        Mat desMat = new Mat();
        Imgproc.cvtColor(mat1, srcMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(mat2, desMat, Imgproc.COLOR_BGR2GRAY);
        srcMat.convertTo(srcMat, CvType.CV_32F);
        desMat.convertTo(desMat, CvType.CV_32F);
        double target = Imgproc.compareHist(srcMat, desMat, Imgproc.CV_COMP_CORREL);
        return target;
    }
}
