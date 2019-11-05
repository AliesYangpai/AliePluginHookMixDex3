package org.alie.aliepluginhookmixdex3.plugin;

import android.content.Context;


import org.alie.aliepluginhookmixdex3.plugin.hook.base.BaseHook;
import org.alie.aliepluginhookmixdex3.plugin.hook.hookImpl.IActivityManagerHook;

import java.lang.reflect.Method;

/**
 *
 */

public class HookFactory {
    private static final HookFactory ourInstance = new HookFactory();

    public static HookFactory getInstance() {
        return ourInstance;
    }
    private HookFactory() {
    }


    public void installHook(BaseHook baseHook, ClassLoader classLoader) {
        try {
            baseHook.onInit(classLoader);
        } catch (Exception e) {

        }

    }
//    宿主   application  调用
    public final void installHook(Context context, ClassLoader classLoader) {
//    hook类非常多  宿主   打电话 服务 不需要  hook  判断当前进程
        if(!isPluginService(context)){
//            宿主进程
            installHook(new IActivityManagerHook(), classLoader);
        }else {
//            插件进程


        }
    }

    public boolean isPluginService(Context context) {
        String processName = getProcessName(context);
        if (processName != null) {
            return processName.contains(":Plugin");
        }
        return false;
    }
    private static String getProcessName(Context context) {

        String processName = null;
        Class<?> ActivityThread = null;
        try {
            ActivityThread = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = ActivityThread.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object am = currentActivityThread.invoke(null);
            Method getProcessName = ActivityThread.getDeclaredMethod("getProcessName");
            getProcessName.setAccessible(true);
            processName = (String) getProcessName.invoke(am);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
