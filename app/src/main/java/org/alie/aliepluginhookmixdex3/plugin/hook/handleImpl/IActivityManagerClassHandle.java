package org.alie.aliepluginhookmixdex3.plugin.hook.handleImpl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.RemoteException;


import org.alie.aliepluginhookmixdex3.plugin.activity.ProxyActivity;
import org.alie.aliepluginhookmixdex3.plugin.core.PluginManager;
import org.alie.aliepluginhookmixdex3.plugin.hook.base.BaseClassHandle;
import org.alie.aliepluginhookmixdex3.plugin.hook.base.BaseMethodHandle;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by baby on 2018/4/16.
 */
//具体分发类
public class IActivityManagerClassHandle extends BaseClassHandle {
    @Override
    protected void init(Map<String, BaseMethodHandle> hookMethodHandles) {
        hookMethodHandles.put("startActivity", new StartActivity(mHostContext));
    }
    private static int findFirstIntentIndexInArgs(Object[] args) {
        if (args != null && args.length > 0) {
            int i = 0;
            for (Object arg : args) {
                if (arg != null && arg instanceof Intent) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    private static class StartActivity extends BaseMethodHandle {
        public StartActivity(Context mHostContext) {
            super(mHostContext);
        }
//invoke方法----》beforeInvoke---->afterInvoker
        @Override
        protected boolean beforeInvoke(Object receiver, Method method, Object[] args) {

            int intentOfArgIndex = findFirstIntentIndexInArgs(args);
            if (args != null && args.length > 1 && intentOfArgIndex >= 0) {
                Intent intent = (Intent) args[intentOfArgIndex];
//        包名   ----     ActivityInfo  MainAcitivty
            }
//瞒天过海
//                寻找传进来的intent
                Intent intent = null;
                int index = 0;

//目的  ---载入acgtivity  将它还原
                Intent newIntent = new Intent();
                ComponentName componentName = new ComponentName(mHostContext, ProxyActivity.class);
                newIntent.setComponent(componentName);
//                真实的意图 被我隐藏到了  键值对
                newIntent.putExtra("oldIntent", intent);
                args[index] = newIntent;

            return false;
        }


        @Override
        protected void afterInvoke(Object receiver, Method method, Object[] args) {
            super.afterInvoke(receiver, method, args);
        }
    }

//    public static ActivityInfo resolveActivity(Intent intent) {
//        try {
//            return  PluginManager.getInstance().resolveActivityInfo( intent,0);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
