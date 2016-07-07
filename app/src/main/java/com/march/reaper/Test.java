package com.march.reaper;

/**
 * Created by march on 16/7/2.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String url = "http://m.xxxiao.com/wp-content/uploads/sites/3/2015/12/m.xxxiao.com_4abf68c0984ffc82292ad0e322db8f57-683x1024.jpg";
        System.out.print(url.replaceAll("-\\d+x\\d+.jpg",".jpg"));
    }
}
