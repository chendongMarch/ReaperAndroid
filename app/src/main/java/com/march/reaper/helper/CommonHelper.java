package com.march.reaper.helper;
import android.graphics.Color;
import java.util.Random;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.helper
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class CommonHelper {
    public static int randomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(150), random.nextInt(150), random.nextInt(150));
    }



}
