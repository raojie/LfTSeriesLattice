package com.landfone.lattice.test.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Method: LfLog
 * Decription: 日志模块
 * Author: raoj
 * Date: 2018/1/5
 **/
public class LfLog {

    //是否初始化
    private boolean isInitialize = false;
    public static Activity mActivity;
    public static Context mContext;

    private LfLog() {
        if (!isInitialize) {
            configure();
        }
    }

    /**
     * 静态内部类实现单例模式
     */
    private static class LfLogHolder {
        private static final LfLog INSTANCE = new LfLog();
    }

    public static final LfLog getInstance() {
        return LfLogHolder.INSTANCE;
    }

    //初始化 log
    private Logger logger;

    public void configure() {
        if (mActivity != null)
            verifyStoragePermissions(mActivity);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final LogConfigurator logConfigurator = new LogConfigurator();

        //日志文件路径地址:SD卡下log文件夹的lf文件
//        String fileName = Environment.getExternalStorageDirectory()
//                + File.separator + "MTlogs"
//                + File.separator + "lf.log";
        String fileName = Environment
                .getExternalStorageDirectory()
                .getPath() + File.separator + "Logs" + "lfmt.log";

        //设置文件名
        logConfigurator.setFileName(fileName);
        //设置root日志输出级别 默认为DEBUG
        logConfigurator.setRootLevel(Level.DEBUG);
        // 设置日志输出级别
        logConfigurator.setLevel("org.apache", Level.ERROR);
        //设置 输出到日志文件的文字格式 默认 %d %-5p [%c{2}]-[%L] %m%n
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        //设置总文件大小
        logConfigurator.setMaxFileSize(1024 * 1024 * 5);
        //设置最大产生的文件个数
        logConfigurator.setMaxBackupSize(10);
        //是否显示内部初始化日志,默认为false
        logConfigurator.setInternalDebugging(false);
        logConfigurator.configure();
        RollingFileAppender rfa = new RollingFileAppender();
        rfa.setEncoding("GBK");
        logger = Logger.getLogger("log4j");
        logger.addAppender(rfa);
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    public static int v(String tag, String msg) {
        getInstance().logger.debug(tag + "->" + msg);
        return 0;
    }

    public static int d(String tag, String msg) {
        getInstance().logger.debug(tag + "->" + msg);
        return 0;
    }

    public static int e(String tag, String msg) {
        getInstance().logger.error(tag + "->" + msg);
        return 0;
    }


    public static int w(String tag, String msg) {
        getInstance().logger.warn(tag + "->" + msg);
        return 0;
    }

    public static int i(String tag, String msg) {
        getInstance().logger.info(tag + "->" + msg);
        return 0;
    }

    //交易状态日志
    public static int t(String tag, String msg) {
        getInstance().logger.trace(tag + "->" + msg);
        return 0;
    }

    public static void off() {
        if (getInstance().logger != null) {
            getInstance().logger.setLevel(Level.OFF);
        }
    }

    public static void on() {
        if (getInstance().logger != null) {
            getInstance().logger.setLevel(Level.DEBUG);
        }
    }
}
