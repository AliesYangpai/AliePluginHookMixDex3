package org.alie.aliepluginhookmixdex3.plugin.hook.base;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by baby on 2018/4/16.
 */
//具体处理方法的对象  （方法级别）
public class BaseMethodHandle {
    protected Context mHostContext;

    public BaseMethodHandle(Context mHostContext) {
        this.mHostContext = mHostContext;
    }

    //   (前 ) hook  （hook后）
//
    public Object doHookInnner(Object receiver, Method method, Object[] args) {
        boolean suc =beforeInvoke(receiver, method, args);
        Object invokeResult = null;
        try {
            if (!suc) {
                invokeResult=  method.invoke(receiver, args);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
          afterInvoke(receiver, method, args);
        return invokeResult;
    }
    protected boolean beforeInvoke(Object receiver, Method method, Object[] args) {
        return false;
    }
    protected  void afterInvoke(Object receiver, Method method, Object[] args) {
    }

}
