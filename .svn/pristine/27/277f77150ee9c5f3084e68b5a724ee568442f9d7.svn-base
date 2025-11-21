package com.jsdc.gsgwxb.utils;

import java.util.ArrayList;
import java.util.List;

public class CoordinateTransform {
    private static final double PI = 3.14159265358979323846;
    private static final double AXIS = 6378245.0; // 长半轴
    private static final double OFFSET = 0.00669342162296594323; // 偏心率平方
    /**
     * WGS-84 转 GCJ-02
     */
    public static double[] wgs84ToGcj02(double lng, double lat) {
        if (outOfChina(lng, lat)) {
            return new double[]{lng, lat};
        }
        double dLat = transformLat(lng - 105.0, lat - 35.0);
        double dLng = transformLng(lng - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - OFFSET * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((AXIS * (1 - OFFSET)) / (magic * sqrtMagic) * PI);
        dLng = (dLng * 180.0) / (AXIS / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = lat + dLat;
        double mgLng = lng + dLng;
        return new double[]{mgLng, mgLat};
    }
    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y +
                0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) +
                20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) +
                40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) +
                320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }
    private static double transformLng(double x, double y) {
        double ret = 300.0 + x + 2.0 * y +
                0.1 * x * x + 0.1 * x * y +
                0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) +
                20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) +
                40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) +
                300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }
    private static boolean outOfChina(double lng, double lat) {
        return lng < 72.004 || lng > 137.8347 || lat < 0.8293 || lat > 55.8271;
    }


    public static List<double[]> batchWgs84ToGcj02(List<double[]> gpsList) {
        List<double[]> result = new ArrayList<>();
        for (double[] gps : gpsList) {
            result.add(wgs84ToGcj02(gps[0], gps[1]));
        }
        return result;
    }

}
