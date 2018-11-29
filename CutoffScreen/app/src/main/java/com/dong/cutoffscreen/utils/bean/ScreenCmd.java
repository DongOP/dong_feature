package com.dong.cutoffscreen.utils.bean;

/**
 * Created by Dong on 2018/8/28.
 */

public class ScreenCmd {
    // 开始时的x、y坐标
    private String xStart;
    private String yStart;
    // 结束时的x、y坐标
    private String xEnd;
    private String yEnd;
    private String picture; // 图片
    private String beginTime; // 按下时间
    private String endTime; // 抬起时间

    public ScreenCmd() {
    }

    public ScreenCmd(String xStart, String yStart, String xEnd, String yEnd, String picture, String beginTime, String endTime) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.picture = picture;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public String getxStart() {
        return xStart;
    }

    public void setxStart(String xStart) {
        this.xStart = xStart;
    }

    public String getyStart() {
        return yStart;
    }

    public void setyStart(String yStart) {
        this.yStart = yStart;
    }

    public String getxEnd() {
        return xEnd;
    }

    public void setxEnd(String xEnd) {
        this.xEnd = xEnd;
    }

    public String getyEnd() {
        return yEnd;
    }

    public void setyEnd(String yEnd) {
        this.yEnd = yEnd;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    @Override
    public String toString() {
        return "ScreenCmd{" +
                "xStart='" + xStart + '\'' +
                ", yStart='" + yStart + '\'' +
                ", xEnd='" + xEnd + '\'' +
                ", yEnd='" + yEnd + '\'' +
                ", picture='" + picture + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
