package fussen.yu.news.utils;

import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import org.json.JSONArray;

import java.util.List;

public class SportUtils {

    /**
     * 高德地图--描绘路线
     */
    public static void addPath(AMap aMap, List<Polyline> paths, List<LatLng> points, int color) {
        PolylineOptions polylineOptions = new PolylineOptions().addAll(points).width(UiUtils.dip2px(5)).color(color);
        Polyline polyline = aMap.addPolyline(polylineOptions);
        polyline.setDottedLine(true);
        paths.add(polyline);
//        points.clear();
    }

    public static void addPath(AMap aMap, List<LatLng> points, int color) {
        PolylineOptions polylineOptions = new PolylineOptions().addAll(points).width(UiUtils.dip2px(5)).geodesic(true).color(color);
        aMap.addPolyline(polylineOptions);
//        points.clear();

    }

    public static void addPath(AMap aMap, List<LatLng> points, List<Integer> colorList) {
        PolylineOptions polylineOptions = new PolylineOptions().addAll(points).width(UiUtils.dip2px(5)).colorValues(colorList).useGradient(true);
        aMap.addPolyline(polylineOptions);
//        points.clear();
    }


    public static void addDottedPath(AMap aMap, List<LatLng> points, int color) {
        PolylineOptions polylineOptions = new PolylineOptions().addAll(points).width(UiUtils.dip2px(5)).color(color).setDottedLine(true);
        aMap.addPolyline(polylineOptions);
//        points.clear();
    }

    public static Polyline addPolyline(AMap aMap, List<LatLng> points, int color) {
        PolylineOptions polylineOptions = new PolylineOptions().addAll(points).width(UiUtils.dip2px(5)).geodesic(true).color(color);
        return aMap.addPolyline(polylineOptions);
    }


    public static String getHeartTimeArray(int[] datas) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < datas.length; i++) {
            String time = datas[i] + "";
            jsonArray.put(time);
        }
        return jsonArray.toString();
    }

    public static String getAvgPaceValue(List<String> datas) {
        int length = datas.size();
        if (length == 0) {
            return "0";
        }
        int total = 0;
        for (int i = 0; i < length; i++) {
            String value = datas.get(i).trim();
            if (!("".equals(value))) {

            } else {
                total += Integer.parseInt(value);
            }
        }
        return total / length + "";
    }

    public static int[] hsvToRgb(float hue, float saturation, float value) {

        int h = (int) (hue * 6);
        float f = hue * 6 - h;
        float p = value * (1 - saturation);
        float q = value * (1 - f * saturation);
        float t = value * (1 - (1 - f) * saturation);

        switch (h) {
            case 0:
                return rgbToInt(value, t, p);
            case 1:
                return rgbToInt(q, value, p);
            case 2:
                return rgbToInt(p, value, t);
            case 3:
                return rgbToInt(p, q, value);
            case 4:
                return rgbToInt(t, p, value);
            case 5:
                return rgbToInt(value, p, q);
            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }
    }

    public static String rgbToString(float r, float g, float b) {
        String rs = Integer.toHexString((int) (r * 256));
        String gs = Integer.toHexString((int) (g * 256));
        String bs = Integer.toHexString((int) (b * 256));
        return rs + gs + bs;
    }

    public static int[] rgbToInt(float r, float g, float b) {
        return new int[]{(int) (r * 255.0), (int) (g * 255.0), (int) (b * 255.0)};
    }


    /**
     * 生成 绿-红之间的渐变色
     * 比如设置 1为绿色 10为红色 可以获得1-10之间的其他数 比如说5 的RGB值
     *
     * @param value   当前值
     * @param section 起始和终止值
     * @return RGB颜色值
     */
    public static int getColorBySection(float value, float[] section) {
        int r = 255;
        int g = 255;

        // 这里分5个等级
        float div = (value - section[0]) / (section[1] - section[0]);
        if (div < 0.2f) {
            r = 125;
        } else if (0.2f <= div && div < 0.4f) {
            r = 200;
        } else if (0.4f <= div && div < 0.6f) {
            g = 230;
        } else if (0.6f <= div && div < 0.8f) {
            g = 150;
        } else if (0.8f <= div && div < 1f) {
            g = 75;
        }
//        if (div<0.25f){
//            r = 102;
//        }else if (0.25f<=div && div<0.5f){
//            r = 204;
//        }else if (0.5f<=div && div<0.75f){
//            g = 204;
//        }else if (0.75f<=div && div<1f){
//            g = 102;
//        }
        return Color.rgb(r, g, 0);
    }


    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    /**
     * 计算卡路里
     * 默认是男性 跑步运动
     *
     * @param timeMin
     * @param speedMIN
     * @return
     */

    public static float getCalories(float timeMin, float speedMIN) {
        float mBodyWeight = (float) 62;//体重默认62kg
        float mBodyHeight = 170 / 100f;//身高默认170cm
        int mSex = 1;//男性=1；女性=2
        float calories = 0.0f;
        calories = (0.2f * speedMIN + 3.5f) * mBodyWeight / 200f;
        return calories * timeMin;
    }


}
