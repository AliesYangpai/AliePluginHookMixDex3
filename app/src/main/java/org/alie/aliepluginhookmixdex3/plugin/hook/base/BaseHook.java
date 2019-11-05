package org.alie.aliepluginhookmixdex3.plugin.hook.base;

/**
 * 1
 */

public abstract class BaseHook {
//    hook类 ----动态代理  invoke        接口  serListner
//  还原的系统对象
    protected Object realObj;
//分发类
    protected  BaseClassHandle classHandle;

    //    开启关闭 （宿主的activity当然是不需要被分发的，所以这个是为了开启或关闭分发功能，来作用与宿主或插件activity跳转）
    public boolean isEnable = false;


    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void setRealObj(Object realObj) {
        this.realObj = realObj;
        classHandle=createHookHandle();
    }
//策略模式  1
    protected abstract BaseClassHandle createHookHandle();

    public abstract void onInit(ClassLoader classLoader);
}
