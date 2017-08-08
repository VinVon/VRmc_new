package com.medvision.vrmc.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * 获取3阶贝塞尔运动轨迹
 * Created by raytine on 2017/8/7.
 */

public class BerziEvaluator implements TypeEvaluator<PointF> {
    private PointF p1,p2;
    public BerziEvaluator(PointF p1 , PointF p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public PointF evaluate(float t, PointF p0, PointF p3) {
        PointF pointF = new PointF();
        pointF.x = p0.x*(1-t)*(1-t)*(1-t)
                +3*p1.x*(1-t)*(1-t)*t
                +3*p2.x*(1-t)*t*t
                +p3.x*t*t*t;
        pointF.y = p0.y*(1-t)*(1-t)*(1-t)
                +3*p1.y*(1-t)*(1-t)*t
                +3*p2.y*(1-t)*t*t
                +p3.y*t*t*t;

        return pointF;
    }
}
