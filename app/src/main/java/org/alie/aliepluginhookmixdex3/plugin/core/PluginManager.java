package org.alie.aliepluginhookmixdex3.plugin.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.os.IBinder;
import android.os.RemoteException;

import org.alie.aliepluginhookmixdex3.plugin.pm.IPluginManager;


/**
 * Created by Administrator on 2018/4/13.
 */

public class PluginManager implements ServiceConnection {
    private IPluginManager mPluginManager;

    private Context mHostContext;
    private static final PluginManager ourInstance = new PluginManager();

    public static PluginManager getInstance() {
        return ourInstance;
    }

    private PluginManager() {
    }
    public void init(Context context) {
        mHostContext = context;
    }
    public void connectToService() {
        if (mPluginManager == null) {
            try {
                Intent intent = new Intent(mHostContext, PluginManagerService.class);
                mHostContext.startService(intent);
                mHostContext.bindService(intent, this, Context.BIND_AUTO_CREATE);
            } catch (Exception e) {
            }
        }
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mPluginManager = IPluginManager.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public int installPackage(String filepath, int flags) {
        try {
            if (mPluginManager != null) {
                int result = mPluginManager.installPackage(filepath, flags);
                return result;
            } else {
            }
        }  catch (Exception e) {
        }
        return -1;
    }

    public ActivityInfo selectProxyActivity(Intent intent) {
        if (mPluginManager!=null) {
            try {
                return  mPluginManager.selectStubActivityInfoByIntent(intent);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ActivityInfo resolveActivityInfo(Intent intent, int flags) throws RemoteException {
        if (mPluginManager != null) {
            return mPluginManager.getActivityInfo(intent.getComponent(), flags);
        }
        return null;
    }

    public ApplicationInfo getApplicationInfo(ComponentName componentName, int flag) {

        if (mPluginManager != null) {
            try {
                return mPluginManager.getApplicationInfo(componentName.getPackageName(), flag);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }
}
