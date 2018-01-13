package com.landfone.lattice.test.utils;

/**
 * Method: SleepUtils
 * Decription:
 * Author: raoj
 * Date: 2017/11/30
 **/
public class SleepUtils {

    public static void _sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
