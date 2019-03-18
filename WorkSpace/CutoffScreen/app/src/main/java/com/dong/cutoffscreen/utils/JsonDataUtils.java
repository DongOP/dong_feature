package com.dong.cutoffscreen.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟生成、解析json数据
 * <p>
 * Created by Dong on 2018/7/13.
 */

public class JsonDataUtils {

    private static final String TAG = "JsonDataUtils";
    // 动作发生的开始、结束时间
    public static final String BEGIN_TIME = "begin_time";
    public static final String END_TIME = "end_time";
    // 操作机的坐标和密度
    public static final String X_POINT = "x";
    public static final String Y_POINT = "y";
    public static final String DENSITY = "density";
    // 操作机分辨率
    public static final String Y_IMAGE_RESOLUTION = "y_image_resolution";
    public static final String X_IMAGE_RESOLUTION = "x_image_resolution";
    // 操作机开始、结束的坐标
    public static final String BEGIN_POINT = "begin_point";
    public static final String END_POINT = "end_point";

    // 构建（主席的）json数据
    public static String buildJson() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(BEGIN_TIME, "begin_time_value");
        map.put(END_TIME, "end_time_value");
        Map<String, Object> mapBegin = new HashMap<String, Object>();
        mapBegin.put(X_POINT, 1);
        mapBegin.put(Y_POINT, 2);
        Map<String, Object> mapEnd = new HashMap<String, Object>();
        mapEnd.put(X_POINT, 100);
        mapEnd.put(Y_POINT, 200);
        List<Map> jsonBegin = new ArrayList<Map>();
        jsonBegin.add(mapBegin);
        List<Map> jsonEnd = new ArrayList<Map>();
        jsonEnd.add(mapEnd);
        map.put(BEGIN_POINT, jsonBegin);
        map.put(END_POINT, jsonEnd);

        JSONObject jObject = new JSONObject(map);
        // {"begin_time":"begin_time_value","end_point":[{"y":200,"x":100}],"end_time":"end_time_value","begin_point":[{"y":2,"x":1}]}
        LogUtils.logd(TAG, "buildJson, jObject=" + jObject.toString());
        return jObject.toString();
    }

    // 解析（主席的）json数据
    public static void parseJson(String msg) {
        try {
            JSONObject jsonObject = new JSONObject(msg);
            if (!jsonObject.optString(BEGIN_TIME).equals("")) {
                String begin_time = jsonObject.optString(BEGIN_TIME);
                String end_time = jsonObject.optString(END_TIME);
                String begin_point = jsonObject.optString(BEGIN_POINT);
                String end_point = jsonObject.optString(END_POINT);

                // 解析起始坐标点
                JSONArray jsonBP = new JSONArray(begin_point);
                int begin_x = 0;
                int begin_y = 0;
                if (jsonBP.length() > 0) {
                    for (int i = 0; i < jsonBP.length(); i++) {
                        JSONObject json = jsonBP.getJSONObject(i);
                        begin_x = json.optInt(X_POINT);
                        begin_y = json.optInt(Y_POINT);
                    }
                }
                // 解析结束坐标点
                JSONArray jsonEP = new JSONArray(end_point);
                int end_x = 0;
                int end_y = 0;
                if (jsonEP.length() > 0) {
                    for (int i = 0; i < jsonEP.length(); i++) {
                        JSONObject json = jsonEP.getJSONObject(i);
                        end_x = json.optInt(X_POINT);
                        end_y = json.optInt(Y_POINT);
                    }
                }
                LogUtils.logd(TAG, "parseJson, begin_time=" + begin_time + ", end_time=" + end_time + ", begin_x=" + begin_x + ", begin_y" + begin_y + ", end_x=" + end_x + ", end_y" + end_y);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 构建操作机json数据
    public static String buildOperatingPhoneJson(String beginTime, String endTime, int xStartPoint, int yStartPoint,
                                                 int xEndPoint, int yEndPoint, int xImageRes, int yImageRes, float density) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(BEGIN_TIME, beginTime);
        map.put(END_TIME, endTime);
        Map<String, Object> mapStart = new HashMap<String, Object>();
        mapStart.put(X_POINT, xStartPoint);
        mapStart.put(Y_POINT, yStartPoint);
        Map<String, Object> mapEnd = new HashMap<String, Object>();
        mapEnd.put(X_POINT, xEndPoint);
        mapEnd.put(Y_POINT, yEndPoint);
        List<Map> jsonBegin = new ArrayList<Map>();
        jsonBegin.add(mapStart);
        List<Map> jsonEnd = new ArrayList<Map>();
        jsonEnd.add(mapEnd);
        map.put(BEGIN_POINT, jsonBegin);
        map.put(END_POINT, jsonEnd);
        map.put(X_IMAGE_RESOLUTION, xImageRes);
        map.put(Y_IMAGE_RESOLUTION, yImageRes);
        map.put(DENSITY, density);

        JSONObject jObject = new JSONObject(map);
        // {"begin_time":"begin_time_value","end_point":[{"y":200,"x":100}],"end_time":"end_time_value","begin_point":[{"y":2,"x":1}]}
        LogUtils.logd(TAG, "buildOperatingPhoneJson, jObject=" + jObject.toString());

        return jObject.toString();
    }

    // mock 操作机数据
    public static String doMockBuild() {
        String beginTime = "beginTime";
        String endTime = "endTime";
        int xStartPoint = 100;
        int yStartPoint = 1000;
        int xEndPoint = 100;
        int yEndPoint = 200;
        int xImageRes = 1080;
        int yImageRes = 1920;
        float density = 3;
//        return buildOperatingPhoneJson(beginTime, endTime, xStartPoint, yStartPoint, xEndPoint, yEndPoint, xImageRes, yImageRes, density);
        // 验证builder模式
        JsonBuildUtils.Builder jsonBuilder = new JsonBuildUtils.Builder();
        String msg = jsonBuilder
                .setBeginTime(beginTime)
                .setEndTime(endTime)
                .setxStartPoint(xStartPoint)
                .setyStartPoint(yStartPoint)
                .setxEndPoint(xEndPoint)
                .setyEndPoint(yEndPoint)
                .setxImageRes(xImageRes)
                .setyImageRes(yImageRes)
                .setDensity(density)
                .create()
                .buildOperatingPhoneJson();

        return msg;
    }

    // 解析操作机json数据
    public static void parseOperatingPhoneJson(String msg) {
        try {
            JSONObject jsonObject = new JSONObject(msg);
            if (!jsonObject.optString(BEGIN_TIME).equals("")) {
                String begin_time = jsonObject.optString(BEGIN_TIME);
                String end_time = jsonObject.optString(END_TIME);
                String begin_point = jsonObject.optString(BEGIN_POINT);
                int density = jsonObject.optInt(DENSITY);
                int xImageRes = jsonObject.optInt(X_IMAGE_RESOLUTION);
                int yImageRes = jsonObject.optInt(Y_IMAGE_RESOLUTION);

                // 解析起始坐标点
                JSONArray jsonBP = new JSONArray(begin_point);
                int begin_x = 0;
                int begin_y = 0;
                if (jsonBP.length() > 0) {
                    for (int i = 0; i < jsonBP.length(); i++) {
                        JSONObject json = jsonBP.getJSONObject(i);
                        begin_x = json.optInt(X_POINT);
                        begin_y = json.optInt(Y_POINT);
                    }
                }
                LogUtils.logd(TAG, "parseOperatingPhoneJson, begin_time=" + begin_time + ", end_time=" + end_time + ", begin_x=" + begin_x + ", begin_y=" + begin_y + ", density=" + density + ", x分辨率=" + xImageRes + ", y分辨率=" + yImageRes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
