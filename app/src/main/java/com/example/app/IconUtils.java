package com.example.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.app.ui.XixiContentActivity;
import com.example.app.ui.XixiFileActivity;

public class IconUtils {


    public static void enable(boolean enable, Context context) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, XixiFileActivity.class);
        ComponentName componentName2 = new ComponentName(context, XixiContentActivity.class);
        if (enable) {
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
            packageManager.setComponentEnabledSetting(componentName2, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
            IconUtils.addShortcut(context, R.mipmap.leak_canary_icon);
        } else {
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            packageManager.setComponentEnabledSetting(componentName2, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            IconUtils.delShortcut(context);
        }
    }

    /**
     * 关闭组件
     *
     * @param context
     * @param cls
     */
    public static void show(Context context, @NonNull Class<?> cls) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, cls);
        int res = packageManager.getComponentEnabledSetting(componentName);
        if (res == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
                || res == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            // 隐藏应用图标
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        } else {
            // 显示应用图标
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
        }
    }


    /**
     * @param useCode =1、为活动图标 =2 为用普通图标 =3、不启用判断
     */
    public static void switchIcon(int useCode, Context context) {
        try {
            //要跟manifest的activity-alias 的name保持一致
            String icon_tag = ".ui.XixiFileActivity.ok";
            String icon_tag_1212 = ".ui.XixiFileActivity.no";
            if (useCode != 3) {
                PackageManager pm = context.getPackageManager();
                ComponentName normalComponentName = new ComponentName(context, icon_tag);
                //正常图标新状态
                int normalNewState = useCode == 2 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (pm.getComponentEnabledSetting(normalComponentName) != normalNewState) {//新状态跟当前状态不一样才执行
                    pm.setComponentEnabledSetting(normalComponentName, normalNewState, PackageManager.DONT_KILL_APP);
                }
                ComponentName actComponentName = new ComponentName(context, icon_tag_1212);
                //正常图标新状态
                int actNewState = useCode == 1 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (pm.getComponentEnabledSetting(actComponentName) != actNewState) {//新状态跟当前状态不一样才执行
                    pm.setComponentEnabledSetting(actComponentName, actNewState, PackageManager.DONT_KILL_APP);
                }
            } else {

            }
        } catch (Exception e) {

        }
    }

    /**
     * 添加当前应用的桌面快捷方式
     *
     * @param context
     */
    public static void addShortcut(Context context, int appIcon) {
//        // 获取当前应用名称
//        String title = null;
//        try {
//            final PackageManager pm = context.getPackageManager();
//            title = pm.getApplicationLabel(
//                    pm.getApplicationInfo(context.getPackageName(),
//                            PackageManager.GET_META_DATA)).toString();
//        } catch (Exception e) {
//        }


        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//        Intent shortcutIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        Intent shortcutIntent = new Intent(context, XixiFileActivity.class);
//        Intent shortcut = new Intent(context, XixiFileActivity.class);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        // 快捷方式名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "xixihaha");
        // 不允许重复创建（不一定有效）
        shortcut.putExtra("duplicate", false);
        // 快捷方式的图标
        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(context, appIcon);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

        context.sendBroadcast(shortcut);
    }

    /**
     * 删除当前应用的桌面快捷方式
     *
     * @param context
     */
    public static void delShortcut(Context context) {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
//        Intent shortcut = new Intent(context, XixiFileActivity.class);

        // 获取当前应用名称
//        String title = null;
//        try {
//            final PackageManager pm = context.getPackageManager();
//            title = pm.getApplicationLabel(
//                    pm.getApplicationInfo(context.getPackageName(),
//                            PackageManager.GET_META_DATA)).toString();
//        } catch (Exception e) {
//        }
        // 快捷方式名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "xixihahah");
        Intent shortcutIntent = new Intent(context, XixiFileActivity.class);
//        Intent shortcutIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());


        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        context.sendBroadcast(shortcut);
    }

    /**
     * 判断当前应用在桌面是否有桌面快捷方式
     *
     * @param context
     */
    public static boolean hasShortcut(Context context) {
        boolean result = false;
        String title = null;
        try {
            final PackageManager pm = context.getPackageManager();
            title = pm.getApplicationLabel(pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {

        }

        final String uriStr;
        if (android.os.Build.VERSION.SDK_INT < 8) {
            uriStr = "content://com.android.launcher.settings/favorites?notify=true";
        } else if (android.os.Build.VERSION.SDK_INT < 19) {
            uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
        } else {
            uriStr = "content://com.android.launcher3.settings/favorites?notify=true";
        }
        final Uri CONTENT_URI = Uri.parse(uriStr);
        final Cursor c = context.getContentResolver().query(CONTENT_URI, null,
                "title=?", new String[]{title}, null);
        if (c != null && c.getCount() > 0) {
            result = true;
        }
        return result;
    }
}
