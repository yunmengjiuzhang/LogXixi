package com.wangfeixixi.logx;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@SuppressLint("SimpleDateFormat")
class CrashHandler implements UncaughtExceptionHandler {
    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    private static class CrashHandlerInner {
        private static CrashHandler instance = new CrashHandler();
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return CrashHandlerInner.instance;
    }

    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;

    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    /**
     * 初始化
     */
    public void init() {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
//        autoClear(5);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            SystemClock.sleep(1000);
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息; 否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null || !LogX.isApkInDebug())
            return false;
        try {
            // 使用Toast来显示异常信息
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(LogX.getContext(), "很抱歉,程序出现异常,即将重启.", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();
            // 收集设备参数信息
            collectDeviceInfo();
            // 保存日志文件
            saveCrashInfoFile(ex);
            SystemClock.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 收集设备参数信息
     */
    public void collectDeviceInfo() {
        try {
            PackageManager pm = LogX.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(LogX.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                infos.put("versionName", pi.versionName);
                infos.put("versionCode", String.valueOf(pi.versionCode));

                infos.put("第一次安装时间", String.valueOf(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK).format(new Date(pi.firstInstallTime))));
                infos.put("最近安装时间", String.valueOf(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK).format(new Date(pi.lastUpdateTime))));
            }
        } catch (NameNotFoundException e) {
            LogAndroid.e(e, "an error occured when collect package info");
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                LogAndroid.e(e, "an error occured when collect crash info");
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private void saveCrashInfoFile(Throwable ex) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("\r\n" + LogX.getTime() + "\n");
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\n");
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            writeFile(sb.toString());
        } catch (Exception e) {
            LogAndroid.e(e, "an error occured while writing file...");
            sb.append("an error occured while writing file...\r\n");
            writeFile(sb.toString());
        }
    }

    private void writeFile(String sb) throws Exception {
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {//判断sd卡是否存在
            File dir = new File(LogX.getDirpath());
            if (!dir.exists())
                dir.mkdirs();

            FileOutputStream fos = new FileOutputStream(LogX.getDirpath() + LogX.getCrashFileName(), true);
            fos.write(sb.getBytes());
            fos.flush();
            fos.close();
        }
    }

//    /**
//     * 文件删除
//     * @param autoClearDay 文件保存天数
//     */
//    public void autoClear(final int autoClearDay) {
//        FileUtil.delete(getDirpath(), new FilenameFilter() {
//
//            @Override
//            public boolean accept(File file, String filename) {
//                String s = FileUtil.getFileNameWithoutExtension(filename);
//                int day = autoClearDay < 0 ? autoClearDay : -1 * autoClearDay;
//                String date = "crash-" + DateUtil.getOtherDay(day);
//                return date.compareTo(s) >= 0;
//            }
//        });
//
//    }

}