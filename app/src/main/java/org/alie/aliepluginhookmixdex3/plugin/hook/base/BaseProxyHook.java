package org.alie.aliepluginhookmixdex3.plugin.hook.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 2 使用动态代理方法是来kook的动态代理基类
 */

public  abstract class BaseProxyHook extends  BaseHook implements InvocationHandler {
    @Override
    public abstract  void onInit(ClassLoader classLoader);

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
//        分发总站     startActivity（宿主 OneActivity）
        //（宿主的activity当然是不需要被分发的，所以这个是为了开启或关闭分发功能，来作用与宿主或插件activity跳转）
        if (!isEnable()) {
            return method.invoke(realObj, method, args);
        }
//        //策略模式  2
        BaseMethodHandle baseMethodHandle = classHandle.getHookMethodHandler(method.getName());
        if (baseMethodHandle != null) {
            baseMethodHandle.doHookInnner(realObj, method, args);
        }

        return null;
    }
}
