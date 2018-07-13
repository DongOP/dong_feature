package com.dong.cutoffscreen.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟生成json数据
 * <p>
 * Created by Dong on 2018/7/13.
 */

public class JsonDataUtils {

    private static final String TAG = "JsonDataUtils";

    private static final String BEGIN_TIME = "begin_time";
    private static final String END_TIME = "end_time";
    private static final String X_POINT = "x";
    private static final String Y_POINT = "y";
    private static final String BEGIN_POINT = "begin_point";
    private static final String END_POINT = "end_point";

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

}
