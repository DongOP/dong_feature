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

public class PointTransformUtils {

    private static final String TAG = "PointTransformUtils";
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
//        float densityTarget = DeviceInfoUtils.getDensity(context);
        // 存放转换后的坐标
        Map<String, Integer> targetMap = new HashMap<>();
        float xProportion = 0;
        float yProportion = 0;
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
                xProportion = (float) xTargetImageRes / xImageRes;
                yProportion = (float) yTargetImageRes / yImageRes;
                LogUtils.logd(TAG, "changeXY, begin_time=" + begin_time + ", end_time=" + end_time + ", begin_x=" + x + ", begin_y=" + y + ", density=" + density + ", xImageRes=" + xImageRes + ", yImageRes=" + yImageRes +
                        ", xTargetImageRes=" + xTargetImageRes + ", yTargetImageRes=" + yTargetImageRes);
            }
        } catch (JSONException e) {
            LogUtils.loge(TAG, "JSONException=" + e.toString());
            e.printStackTrace();
        }
        // 解析数据 end
        // 密度是大于0，context不能为空。否则返回
        if (density <= 0 || null == context || x < 0 || y < 0 || xProportion == 0) {
            LogUtils.loge(TAG, "数据错误，需检查数据来源。");
            return null;
        }
        // 处理相同密度、不同分辨率的坐标
//            LogUtils.loge(TAG, "操作机与执行机 密度相同");
        if (x == xTargetImageRes) {
            // y轴相同时
            if (y == yTargetImageRes) {
                LogUtils.logd(TAG, "操作机与执行机 x、y轴");
                targetMap.put(X_TARGET, x);
                targetMap.put(Y_TARGET, y);
                return targetMap;
            } else {
                // x轴相同y轴不同,换算公式：y3 = y * (y2 / y1)
                int yTarget = (int) (y * yProportion);
                LogUtils.logd(TAG, "操作机与执行机 x轴相同、y轴不同=" + yProportion);
                targetMap.put(X_TARGET, x);
                targetMap.put(Y_TARGET, yTarget);
                return targetMap;
            }
        } else {
            // x轴不同，y轴相同,换算公式：x3 = x * (x2 / x1)
            if (y == yTargetImageRes) {
                int desX = (int) (x * xProportion);
                LogUtils.logd(TAG, "操作机与执行机 y轴相同,x轴不同" + xProportion);
                targetMap.put(X_TARGET, desX);
                targetMap.put(Y_TARGET, y);
                return targetMap;
            } else {
                // x轴不同，y轴不同.换算公式：x3 = x * (x2 / x1)、y3 = y * (y2 / y1)
                int desX = (int) (x * xProportion);
                int desY = (int) (y * yProportion);
                LogUtils.logd(TAG, "操作机与执行机 x y轴不同，分辨率比=" + xProportion + "， y的比例=" + yProportion);
                targetMap.put(X_TARGET, desX);
                targetMap.put(Y_TARGET, desY);
                return targetMap;
            }
        }

    }


}
