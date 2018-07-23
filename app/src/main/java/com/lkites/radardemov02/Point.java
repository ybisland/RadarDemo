package com.lkites.radardemov02;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by 22122 on 18-7-17.
 * Description：接收距离和角度，转化成直角坐标点，
 * 原点 （ 545，400）
 * 在原点时，图像是处于屏幕外显示不出来，所以也不需要设置visibility
 * cos接收的参数是弧度，wifi接收的是角度
 * 角度有正有负
 */


public class Point {

    private final double ORIGIN_X = 545.0;
    private final double ORIGIN_Y = 400.0;
    private final double PI = 3.1415926;

    public float x, y;

    Point(int d, int a) {   //cos接收的参数是弧度，wifi接收的是角度
        if (d < 600 && d >= 0 && a > -45 && a < 45) { //确保数据正常，0=<d<600, -45<a<45
            double radian = (a / 180.0) * PI;
            x = (float) (ORIGIN_X + d * sin(radian));
            y = (float) (ORIGIN_Y - d * cos(radian));
        } else {
            x = y = 0;
        }
    }
}
