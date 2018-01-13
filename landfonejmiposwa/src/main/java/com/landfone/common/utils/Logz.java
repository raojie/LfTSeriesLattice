package com.landfone.common.utils;

import android.os.Environment;
import android.provider.CalendarContract;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asus on 2017/6/14.
 */

public class Logz {
    private static Logz INSTANCE;

    private static String cache_path_name = "LFLogcat";
    private static String cache_file_name = "LF";
    private static boolean idDebug = true;

    private static String className;//类名
    private static String methodName;//方法名
    private static int lineNumber;//行号

    public static Logz getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Logz();
        }
        return INSTANCE;
    }

    public void setCachePathName(String path) {
        cache_path_name = path;
    }

    public void setCacheFileName(String file) {
        cache_file_name = file;
    }

    public static void setDebug(boolean debug) {
        idDebug = debug;
    }

    static boolean isDebug() {
        return idDebug;
    }

    private static void getMethodNames(StackTraceElement[] mElement) {
        className = mElement[1].getFileName();
        methodName = mElement[1].getMethodName();
        lineNumber = mElement[1].getLineNumber();
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(className);
        buffer.append("(").append(methodName).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }


    public static void v(String log) {
        getMethodNames(new Throwable().getStackTrace());
        Logz.v(className, log);
    }

    public static void v(String TAG, String log) {
        if (!isDebug()) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.v(TAG, createLog(log));
        saveToFile("V/" + createLog(log));
    }

    public static void d(String log) {
        getMethodNames(new Throwable().getStackTrace());
        Logz.d(className, log);
    }

    public static void d(String TAG, String log) {
        if (!isDebug()) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.d(TAG, createLog(log));
        saveToFile("D/" + createLog(log));
    }

    public static void i(String log) {
        getMethodNames(new Throwable().getStackTrace());
        Logz.i(className, log);
    }

    public static void i(String TAG, String log) {
        if (!isDebug()) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.i(TAG, createLog(log));
        saveToFile("I/" + createLog(log));
    }

    public static void w(String log) {
        getMethodNames(new Throwable().getStackTrace());
        Logz.w(className, log);
    }

    public static void w(String TAG, String log) {
        if (!isDebug()) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.w(TAG, createLog(log));
        saveToFile("W/" + createLog(log));
    }

    public static void e(String log) {
        getMethodNames(new Throwable().getStackTrace());
        Logz.e(className, log);
    }

    public static void e(String TAG, String log) {
        if (!isDebug()) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.e(TAG, createLog(log));
        saveToFile("E/" + createLog(log));
    }

    public static void wtf(String log) {
        getMethodNames(new Throwable().getStackTrace());
        Logz.wtf(className, log);
    }

    public static void wtf(String TAG, String log) {
        if (!isDebug()) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(TAG, createLog(log));
        saveToFile("WTF/" + createLog(log));
    }


    private static String getTime() {
        return "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())) + "] ";
    }

    private static String date() {
        return new SimpleDateFormat("yyyy_MM_dd").format(new Date(System.currentTimeMillis()));
    }

    private static File getFile() {
        File logPath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            logPath = Environment.getExternalStorageDirectory();
        }
        File cachePath = new File(logPath + File.separator + cache_path_name);
        if (!cachePath.exists()) {
            cachePath.mkdir();
        }
        File filePath = new File(cachePath + File.separator + cache_file_name + date() + ".txt");
        return filePath;
    }

    private static void saveToFile(String log) {
        try {
            FileWriter writer = new FileWriter(getFile(), true);
            writer.write(getTime() + log + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
