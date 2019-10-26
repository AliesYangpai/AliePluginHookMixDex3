package org.alie.aliepluginhookmixdex3.plugin.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

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
}
