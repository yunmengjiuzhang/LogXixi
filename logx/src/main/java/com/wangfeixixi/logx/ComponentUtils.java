package com.wangfeixixi.logx;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

public class ComponentUtils {
    /**
     * 关闭组件
     *
     * @param cls
     */
    public static void ComponentEnable(boolean enable, @NonNull Class<?> cls) {
        PackageManager packageManager = LogXConfig.getContext().getPackageManager();
        ComponentName componentName = new ComponentName(LogXConfig.getContext(), cls);
        int res = packageManager.getComponentEnabledSetting(componentName);

        if (enable) {
            if (res == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT || res == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                // 显示应用图标
                packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
            }
        } else {
            if (res == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT || res == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            } else {
                // 隐藏应用图标
                packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            }
        }


    }


    /**
     * @param useCode =1、为活动图标 =2 为用普通图标 =3、不启用判断
     */
    private void switchIcon(int useCode, Context context) {
        try {
            //要跟manifest的activity-alias 的name保持一致
            String icon_tag = "com.weechan.shidexianapp.icon_tag";
            String icon_tag_1212 = "com.weechan.shidexianapp.icon_tag_1212";
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
            }
        } catch (Exception e) {

        }
    }
}
