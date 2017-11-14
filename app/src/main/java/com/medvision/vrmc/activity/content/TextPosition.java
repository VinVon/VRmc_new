package com.medvision.vrmc.activity.content;

import android.widget.TextView;

/**
 * Created by raytine on 2017/10/30.
 */

public class TextPosition {
    private int position;
    private TextView tv ;

    public TextPosition(int position, TextView tv) {
        this.position = position;
        this.tv = tv;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }
}
