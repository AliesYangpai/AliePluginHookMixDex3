package org.alie.aliepluginhookmixdex3;

import android.app.Application;

import org.alie.aliepluginhookmixdex3.plugin.HookFactory;


/**
 * Created by baby on 2018/4/16.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //插件  又会调用一次
        HookFactory.getInstance().installHook(this,this.getClassLoader());
    }
}
