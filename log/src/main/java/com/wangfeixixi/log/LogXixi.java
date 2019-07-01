package com.wangfeixixi.log;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogXixi {

    /**
     * 需要设置当前application
     *
     * @param application Application
     */
    public static void init(Application application) {
        ctx = application;
        CrashHandler.getInstance().init();
    }

    /**
     * 设置sdk文件夹名字
     *
     * @param name String
     */
    public static void setDirName(String name) {
        dirName = name;
    }

    public static void setTag(String tag) {
        TAG = tag;
    }

    static String TAG = "xixi";

    /**
     * 判断当前应用是否是debug状态
     *
     * @return boolean
     */
    static boolean isApkInDebug() {
        if (getContext() == null) return false;
        try {
            ApplicationInfo info = getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }


    static void copyAssets(String name) {
        if (getContext() == null) return;
        try {
            AssetManager assets = getContext().getResources().getAssets();
            InputStream inStream = assets.open(name);
            String newPath = LogXixi.getDirPath() + File.separator + name;
            int bytesum = 0;
            int byteread = 0;
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1444];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * sd卡 文件夹名字
     */
    private static String dirName = "xixi";

    /**
     * 获取文件存储目录
     *
     * @return String path
     */
    public static String getDirPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + dirName + File.separator;
    }


    static String getCrashFileName() {
        return "crash_" + getTime() + ".log";
    }

    // 用于格式化日期,作为日志文件名的一部分
    static String getTime() {
        // 用于格式化日期,作为日志文件名的一部分
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK).format(new Date());
    }


    static final int MAX_BYTES = 500 * 1024; // 500K averages to a 4000 lines per file

    static Application getContext() {
        if (ctx == null)
            // TODO: 2019/6/18
//            throw new RuntimeException("please init LogXixi");
            return null;
        else
            return ctx;
    }

    private static Application ctx;


}
