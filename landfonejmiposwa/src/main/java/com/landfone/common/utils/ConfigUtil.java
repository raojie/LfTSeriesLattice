package com.landfone.common.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by asus on 2017/5/11.
 */

public class ConfigUtil {
    private String TAG = this.getClass().getSimpleName();

    //读取配置文件
    public Properties loadConfig(String path, String file) {
        Properties properties = new Properties();
        try {
            makeRootDirectory(path, file);
            FileInputStream s = new FileInputStream(path + file);
            properties.load(s);
        } catch (Exception e) {
            Logz.e(e.getMessage());
            return null;
        }
        return properties;
    }

    //保存配置文件
    public boolean saveConfig(String path, String file, Properties properties) {
        try {
            File pathfile = new File(path + file);
            if (!pathfile.exists())
                pathfile.createNewFile();
            FileOutputStream s = new FileOutputStream(pathfile);
            properties.store(s, "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //判断目录是否存在，不存在则生成
    private String makeRootDirectory(String path, String file) {
        File pathfile = null;
        try {
            pathfile = new File(path);
            if (!pathfile.exists()) {
                pathfile.mkdir();
            }
            return path + file;
        } catch (Exception e) {
            e.printStackTrace();
            return path + file;
        }
    }

//    private void TestProp() {
//        Properties prop;
//        boolean b = false;
//        String s = "";
//        int i = 0;
//        prop = loadConfig("/mnt/sdcard/", "config.properties");
//        if (prop == null) {
//            //配置文件不存在的时候创建配置文件 初始化配置信息
//            prop = new Properties();
//            prop.put("bool", "yes");
//            prop.put("string", "aaaaaaaaaaaaaaaa");
//            prop.put("int", "110");//也可以添加基本类型数据 get时就需要强制转换成封装类型
//            saveConfig("/mnt/sdcard/", "config.properties", prop);
//        }
//        prop.put("bool", "no");//put方法可以直接修改配置信息，不会重复添加
//        b = (((String) prop.get("bool")).equals("yes")) ? true : false;//get出来的都是Object对象 如果是基本类型 需要用到封装类
//        s = (String) prop.get("string");
//        i = Integer.parseInt((String) prop.get("int"));
//        saveConfig("/mnt/sdcard/", "config.properties", prop);
//    }
}
