package com.march.reaper;

import java.util.regex.Pattern;

/**
 * Project  : Reaper
 * Package  : com.march.reaper
 * CreateAt : 2016/11/19
 * Describe :
 *
 * @author chendong
 */

public class TestJava {
    public static void main(String[] args){
        String str = "http://m.xxxiao.com/wp-content/uploads/sites/3/2016/02/m.xxxiao.com_560e17c8be67b0d4045bc4b26402ce77-760x500.jpg";

        System.out.print(str.replaceAll("-\\d+x\\d+",""));
    }
}
