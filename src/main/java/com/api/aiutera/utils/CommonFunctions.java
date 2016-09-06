package com.api.aiutera.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bala on 9/5/16.
 */
public class CommonFunctions {

    /**
     * Method that return the current date
     *
     * @return
     */
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date); //2014/08/06 15:59:48
    }

    /**
     * Method to return the time difference
     *
     * @param startTime
     * @return
     */
    public static long getTimeDiff(long startTime) {
        return Math.abs((System.nanoTime() - startTime) / 1000000);
    }
}
