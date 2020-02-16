package org.alie.aliepluginhookmixdex3.plugin.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Alie on 2019/10/26.
 * 类描述
 * 版本
 */
public class PluginManagerService extends Service {

    private static volatile PackageManagerService packageManagerService;

    public static PackageManagerService getPackageManagerService(Context context) {
        if (packageManagerService == null ) {
            synchronized (PluginManagerService.class) {
                if(packageManagerService == null) {
                    packageManagerService = new PackageManagerService(context);
                    packageManagerService.main();
                }
            }
        }
        return packageManagerService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return getPackageManagerService(this);
    }
}
