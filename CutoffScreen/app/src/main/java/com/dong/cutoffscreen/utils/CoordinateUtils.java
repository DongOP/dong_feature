package com.dong.cutoffscreen.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 坐标转换的工具类
 * <p>
 * Created by Dong on 2018/7/18.
 */

public class CoordinateUtils {

    private static final String TAG = "CoordinateUtils";
    private static final String X_TARGET = "xTarget";
    private static final String Y_TARGET = "yTarget";

    /**
     * 根据分辨率和密度 转换坐标
     *
     * @param msg 执行机传来的json数据
     */
    public static Map<String, Integer> changeXY(Context context, String msg) {
        // 操作机的分辨率、密度
        int xImageRes = -1;
        int yImageRes = -1;
        int density = 0;
        // 操作机的坐标点
        int x = -1;
        int y = -1;
        // 执行机的分辨率、密度
        int xTargetImageRes = DeviceInfoUtils.getWidthPixels(context);
        int yTargetImageRes = DeviceInfoUtils.getHeightPixels(context);
        float densityTarget = DeviceInfoUtils.getDensity(context);
        // 存放转换后的坐标
        Map<String, Integer> targetMap = new HashMap<>();
        int xProportion = 0;
        int yProportion = 0;
        // 解析数据 start
        try {
            JSONObject jsonObject = new JSONObject(msg);
            if (!jsonObject.optString(JsonDataUtils.BEGIN_TIME).equals("")) {
                String begin_time = jsonObject.optString(JsonDataUtils.BEGIN_TIME);
                String end_time = jsonObject.optString(JsonDataUtils.END_TIME);
                String begin_point = jsonObject.optString(JsonDataUtils.BEGIN_POINT);
                density = jsonObject.optInt(JsonDataUtils.DENSITY);
                xImageRes = jsonObject.optInt(JsonDataUtils.X_IMAGE_RESOLUTION);
                yImageRes = jsonObject.optInt(JsonDataUtils.Y_IMAGE_RESOLUTION);
                // 解析起始坐标点
                JSONArray jsonBP = new JSONArray(begin_point);
                if (jsonBP.length() > 0) {
                    for (int i = 0; i < jsonBP.length(); i++) {
                        JSONObject json = jsonBP.getJSONObject(i);
                        x = json.optInt(JsonDataUtils.X_POINT);
                        y = json.optInt(JsonDataUtils.Y_POINT);
                    }
                }
                xProportion = xTargetImageRes / xImageRes;
                yProportion = yTargetImageRes / yImageRes;
                LogUtils.logd(TAG, "changeXY, begin_time=" + begin_time + ", end_time=" + end_time + ", begin_x=" + x + ", begin_y=" + y + ", density=" + density + ", xImageRes=" + xImageRes + ", yImageRes=" + yImageRes +
                        ", xTargetImageRes=" + xTargetImageRes + ", yTargetImageRes=" + yTargetImageRes);
            }
        } catch (JSONException e) {
            LogUtils.loge(TAG, "JSONException=" + e.toString());
            e.printStackTrace();
        }
        // 解析数据 end
        // 密度是大于0，context不能为空。否则返回
        if (density <= 0 || null == context || x < 0 || y < 0) {
            LogUtils.loge(TAG, "数据错误，需检查数据来源。");
            return null;
        }
        // 密度相同时
//        if (density == DeviceInfoUtils.getDensity(context)) {
        // 处理相同密度、不同分辨率的坐标
//            LogUtils.loge(TAG, "操作机与执行机 密度相同");
        if (x == xTargetImageRes) {
            // y轴相同时
            if (y == yTargetImageRes) {
                LogUtils.logd(TAG, "操作机与执行机 x、y轴");
//                targetMap.clear();
                targetMap.put(X_TARGET, x);
                targetMap.put(Y_TARGET, y);
                return targetMap;
            } else {
                // x轴相同y轴不同,换算公式：y3 = y * (y2 / y1)
//                targetMap.clear();
                int yTarget = y * yProportion;
                LogUtils.logd(TAG, "操作机与执行机 x轴相同、y轴不同=" + yProportion);
                targetMap.put(X_TARGET, x);
                targetMap.put(Y_TARGET, yTarget);
                return targetMap;
            }
        } else {
            // x轴不同，y轴相同,换算公式：x3 = x * (x2 / x1)
            if (y == yTargetImageRes) {
//                targetMap.clear();
                int desX = x * xProportion;
                LogUtils.logd(TAG, "操作机与执行机 y轴相同,x轴不同" + xProportion);
                targetMap.put(X_TARGET, desX);
                targetMap.put(Y_TARGET, y);
                return targetMap;
            } else {
//                targetMap.clear();
                // x轴不同，y轴不同.换算公式：x3 = x * (x2 / x1)、y3 = y * (y2 / y1)
                int desX = x * xProportion;
                int desY = y * yProportion;
                LogUtils.logd(TAG, "操作机与执行机 x y轴不同，分辨率比=" + xProportion + "， y的比例=" + yProportion);
                targetMap.put(X_TARGET, desX);
                targetMap.put(Y_TARGET, desY);
                return targetMap;
            }
        }

//        } else { // 密度不同时
//            LogUtils.loge(TAG, "操作机与执行机的密度不同");
//            if (x == xTargetImageRes) {
//                if (y == yTargetImageRes) {
//                    // 密度不同、x相同、y相同(分辨率相同)
//                    int desX = (int)(x * (densityTarget / density));
//                    int desY = (int)(y * (densityTarget / density));
//                    targetMap.clear();
//                    targetMap.put("xTarget", desX);
//                    targetMap.put("yTarget", desY);
//                    return targetMap;
//                } else {
//                    // 密度不同、x相同、y不同
//                    int desX = (int)(x * (densityTarget / density));
//                    int tempY = (int)(y * (densityTarget / density));
//                    int desY = tempY * (yTargetImageRes / yImageRes);
//                    targetMap.clear();
//                    targetMap.put("xTarget", desX);
//                    targetMap.put("yTarget", desY);
//                    return targetMap;
//                }
//            } else {
//                if (y == yTargetImageRes) {
//                    // 密度不同、x不同、y相同
//
//                } else {
//                    // 密度不同、x不同、y不同
//                    // 密度不同、x相同、y不同
////                    int tempX = (int)(x * (densityTarget / density));
////                    int tempY = (int)(y * (densityTarget / density));
////                    int desY = tempY * (yTargetImageRes / yImageRes);
////                    int desX = tempX * (xTargetImageRes / xImageRes);
//                    int tempX = x * (xTargetImageRes / xImageRes);
//                    int desX = (int)(tempX * (densityTarget / density));
//                    int tempY = y * (yTargetImageRes / yImageRes);
//                    int desY = (int)(tempY * (densityTarget / density));
//                    targetMap.clear();
//                    targetMap.put("xTarget", desX);
//                    targetMap.put("yTarget", desY);
//                    return targetMap;
//                }
//            }
//        }

//        return null;
    }

    // 解析操作机json数据
    public static void parseOperatingPhoneJson(String msg) {
        try {
            JSONObject jsonObject = new JSONObject(msg);
            if (!jsonObject.optString(JsonDataUtils.BEGIN_TIME).equals("")) {
                String begin_time = jsonObject.optString(JsonDataUtils.BEGIN_TIME);
                String end_time = jsonObject.optString(JsonDataUtils.END_TIME);
                String begin_point = jsonObject.optString(JsonDataUtils.BEGIN_POINT);
                int density = jsonObject.optInt(JsonDataUtils.DENSITY);
                int xImageRes = jsonObject.optInt(JsonDataUtils.X_IMAGE_RESOLUTION);
                int yImageRes = jsonObject.optInt(JsonDataUtils.Y_IMAGE_RESOLUTION);

                // 解析起始坐标点
                JSONArray jsonBP = new JSONArray(begin_point);
                int begin_x = 0;
                int begin_y = 0;
                if (jsonBP.length() > 0) {
                    for (int i = 0; i < jsonBP.length(); i++) {
                        JSONObject json = jsonBP.getJSONObject(i);
                        begin_x = json.optInt(JsonDataUtils.X_POINT);
                        begin_y = json.optInt(JsonDataUtils.Y_POINT);
                    }
                }
                LogUtils.logd(TAG, "parseOperatingPhoneJson, begin_time=" + begin_time + ", end_time=" + end_time + ", begin_x=" + begin_x + ", begin_y=" + begin_y + ", density=" + density + ", x分辨率=" + xImageRes + ", y分辨率=" + yImageRes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
