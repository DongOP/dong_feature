package com.dong.cutoffscreen.utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于构造 json 数据
 * 基于 builder 模式的 Json 数据工具类
 * <p>
 * Created by Dong on 2018/7/31.
 */

public class JsonBuildUtils {
    private static final String TAG = "JsonBuildUtils";
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

    private String beginTime;
    private String endTime;
    private int xStartPoint;
    private int yStartPoint;
    private int xEndPoint;
    private int yEndPoint;
    private int xImageRes;
    private int yImageRes;
    private float density;

    private JsonBuildUtils(Builder builder) {
        this.beginTime = builder.beginTime;
        this.endTime = builder.endTime;
        this.xStartPoint = builder.xStartPoint;
        this.yStartPoint = builder.yStartPoint;
        this.xEndPoint = builder.xEndPoint;
        this.yEndPoint = builder.yEndPoint;
        this.xImageRes = builder.xImageRes;
        this.yImageRes = builder.yImageRes;
        this.density = builder.density;
    }

    // 构建操作机json数据
    public String buildOperatingPhoneJson() {
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
        LogUtils.logd(TAG, "builder模式，JsonBuildUtils = " + jObject.toString());

        return jObject.toString();
    }

    public static class Builder {

        private String beginTime;
        private String endTime;
        private int xStartPoint;
        private int yStartPoint;
        private int xEndPoint;
        private int yEndPoint;
        private int xImageRes;
        private int yImageRes;
        private float density;

        public Builder setBeginTime(String beginTime) {
            this.beginTime = beginTime;
            return this;
        }

        public Builder setEndTime(String endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder setxStartPoint(int xStartPoint) {
            this.xStartPoint = xStartPoint;
            return this;
        }

        public Builder setyStartPoint(int yStartPoint) {
            this.yStartPoint = yStartPoint;
            return this;
        }

        public Builder setxEndPoint(int xEndPoint) {
            this.xEndPoint = xEndPoint;
            return this;
        }

        public Builder setyEndPoint(int yEndPoint) {
            this.yEndPoint = yEndPoint;
            return this;
        }

        public Builder setxImageRes(int xImageRes) {
            this.xImageRes = xImageRes;
            return this;
        }

        public Builder setyImageRes(int yImageRes) {
            this.yImageRes = yImageRes;
            return this;
        }

        public Builder setDensity(float density) {
            this.density = density;
            return this;
        }

        public JsonBuildUtils create() {
            return new JsonBuildUtils(this);
        }
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getxStartPoint() {
        return xStartPoint;
    }

    public void setxStartPoint(int xStartPoint) {
        this.xStartPoint = xStartPoint;
    }

    public int getyStartPoint() {
        return yStartPoint;
    }

    public void setyStartPoint(int yStartPoint) {
        this.yStartPoint = yStartPoint;
    }

    public int getxEndPoint() {
        return xEndPoint;
    }

    public void setxEndPoint(int xEndPoint) {
        this.xEndPoint = xEndPoint;
    }

    public int getyEndPoint() {
        return yEndPoint;
    }

    public void setyEndPoint(int yEndPoint) {
        this.yEndPoint = yEndPoint;
    }

    public int getxImageRes() {
        return xImageRes;
    }

    public void setxImageRes(int xImageRes) {
        this.xImageRes = xImageRes;
    }

    public int getyImageRes() {
        return yImageRes;
    }

    public void setyImageRes(int yImageRes) {
        this.yImageRes = yImageRes;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    @Override
    public String toString() {
        return "JsonBuildUtils{" +
                "beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", xStartPoint=" + xStartPoint +
                ", yStartPoint=" + yStartPoint +
                ", xEndPoint=" + xEndPoint +
                ", yEndPoint=" + yEndPoint +
                ", xImageRes=" + xImageRes +
                ", yImageRes=" + yImageRes +
                ", density=" + density +
                '}';
    }
}
