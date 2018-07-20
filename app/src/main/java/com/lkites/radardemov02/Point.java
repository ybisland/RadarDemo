package com.lkites.radardemov02;

import static java.lang.Math.cos;

/**
 * Created by 22122 on 18-7-17.
 * Description：接收距离和角度，转化成直角坐标点，
 * 原点 （ 545，400）
 * 角度有正有负
 */


public class Point {

    private final double ORIGIN_X = 545.0f;
    private final double ORIGIN_Y = 400.0f;

    public float x, y;

    Point(int d, int a) {
        if (d < 600 && d >= 0 && a > -40 && a < 40) { //确保数据正常，0=<d<600, -40<a<40
            x = (float) (ORIGIN_X + d * cos(a));
            y = (float) (ORIGIN_Y - d * cos(a));
        } else {
            x = y = 50;
        }
    }
}
