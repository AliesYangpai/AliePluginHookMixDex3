package org.alie.aliepluginhookmixdex3.plugin.hook.base;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * 4.我们不在不在IActivityManagerHook中直接重写invoke方法，而采用父类分发，
 * 达到的目的是，这个方法名称是什么，就使用哪个类来处理
 * 分发   hook方法名  ---》处理对应对象
 * 映射  ------分发给谁
 * method  方法级
 */

public abstract class BaseClassHandle {
//    映射关系    startActivity-----    BaseMethodHandle（具体处理startActivity-方法这个对象）
    protected Map<String, BaseMethodHandle> hookMethodHandles = new HashMap<>();
    protected Context mHostContext;

    public BaseClassHandle() {
        init(hookMethodHandles);
    }

    protected abstract void init(Map<String, BaseMethodHandle> hookMethodHandles);

    //    分发方法
    public BaseMethodHandle getHookMethodHandler(String methodName) {
        if (methodName != null) {
            return hookMethodHandles.get(methodName);
        }
        return null;
    }
}
