package com.dong.cutoffscreen.utils;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dong on 2018/7/27.
 * <p>
 * 计算坐标周围坐标的像素
 * 记录像素值、像素数
 */

public class CalcPointPX {
    private static final String TAG = "CalcPointPX";
    // 平均像素值
    private static final String AVG_PX_VALUE = "avg_px_value";
    // 像素数量
    private static final String PX_NUMBER = "px_num";
    // 不计算的像素数量
    private static final int NO_AVG_NUMBER = 1;

    private static final CalcPointPX mInstance = new CalcPointPX();

    public static CalcPointPX getInstance() {
        return mInstance;
    }

    /**
     * 计算坐标周围坐标的像素
     *
     * @param x         x轴
     * @param y         y轴
     * @param xImageRes x轴分辨率
     * @param yImageRes y轴分辨率
     * @return 平均像素值、像素数
     */
    public static Map<String, Integer> calcPoint(Activity activity, int x, int y, int xImageRes, int yImageRes) {
        // x , y 必须在十分之一到十分之久的区间，否则不计算平均值
        if ((xImageRes / 10) < x || x < xImageRes * (9 / 10)) {
            if ((yImageRes / 10) < y || y < yImageRes * (9 / 10)) {
                LogUtils.logd(TAG, "符合平均像素值的转换要求");
                return doCalc(activity, x, y, xImageRes, yImageRes);
            } else {
                LogUtils.loge(TAG, "y 不符合计算要求，将返回单个坐标像素信息");
                return noCalcAvg(activity, x, y);
            }
        } else {
            LogUtils.loge(TAG, "x 不符合平均像素值的转换要求，将返回单个坐标像素信息");
            return noCalcAvg(activity, x, y);
        }
    }

    // 开始计算周围坐标的平均值
    private static Map<String, Integer> doCalc(Activity activity, int x, int y, int xImageRes, int yImageRes) {


        return null;
    }

    // 返回单个坐标像素值 和像素数 = 1
    private static Map<String, Integer> noCalcAvg(Activity activity, int x, int y) {
        Map<String, Integer> noCalcMap = new HashMap<String, Integer>();
        noCalcMap.put(AVG_PX_VALUE, ScreenShotUtils.getInstance().getStreamPixel(activity, x, y));
        noCalcMap.put(PX_NUMBER, NO_AVG_NUMBER);
        return noCalcMap;
    }
}
