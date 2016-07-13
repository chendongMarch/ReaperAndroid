package com.march.reaper.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by march on 16/7/13.
 * 颜色
 */
public class ColorUtils {

    public static int randomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
