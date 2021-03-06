package org.alie.aliepluginhookmixdex3.plugin.parser;

import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import android.util.Log;


import org.alie.aliepluginhookmixdex3.plugin.utils.reflect.ComponentNameComparator;
import org.alie.aliepluginhookmixdex3.plugin.utils.reflect.PluginDirHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 因此，我们解析的插件最终会设置到这个PluginPackageMap缓存表中，
 * 缓存的目的是：首先，系统pms中 之所以能进行跳转是因为，将apk文件转化为存档文件，所这个缓存表也是模仿pms
 * 组件内存映射：指的是从清单文件中加载的各种xml组件的对象映射
 * 组件内存存档：指的是组件映射中的xxxxInfo
 */

public class PluginPackageMap {

    private static final String TAG = "PluginPackageMap";

    //    ComponentName   组件类名   -------- Activity  前生 内存-----》存档  ActivityInfo
//    Object--->PackageParser.Activity  组件内存映射
    private Map<ComponentName, Object> mActivityObjCache = new TreeMap<ComponentName, Object>(new ComponentNameComparator());
    private Map<ComponentName, Object> mServiceObjCache = new TreeMap<ComponentName, Object>(new ComponentNameComparator());
    private Map<ComponentName, Object> mProviderObjCache = new TreeMap<ComponentName, Object>(new ComponentNameComparator());
    private Map<ComponentName, Object> mReceiversObjCache = new TreeMap<ComponentName, Object>(new ComponentNameComparator());
    //缓存插件  四大组件的存档 组件内存存档
    private Map<ComponentName, ActivityInfo> mActivityInfoCache = new TreeMap<ComponentName, ActivityInfo>(new ComponentNameComparator());
    private Map<ComponentName, ServiceInfo> mServiceInfoCache = new TreeMap<ComponentName, ServiceInfo>(new ComponentNameComparator());
    private Map<ComponentName, ProviderInfo> mProviderInfoCache = new TreeMap<ComponentName, ProviderInfo>(new ComponentNameComparator());
    private Map<ComponentName, ActivityInfo> mReceiversInfoCache = new TreeMap<ComponentName, ActivityInfo>(new ComponentNameComparator());

    //缓存   acitvity  组件 对应的隐式启动的IntentFilter
    private Map<ComponentName, List<IntentFilter>> mActivityIntentFilterCache = new TreeMap<ComponentName, List<IntentFilter>>(new ComponentNameComparator());
    private Map<ComponentName, List<IntentFilter>> mServiceIntentFilterCache = new TreeMap<ComponentName, List<IntentFilter>>(new ComponentNameComparator());
    private Map<ComponentName, List<IntentFilter>> mProviderIntentFilterCache = new TreeMap<ComponentName, List<IntentFilter>>(new ComponentNameComparator());
    private Map<ComponentName, List<IntentFilter>> mReceiverIntentFilterCache = new TreeMap<ComponentName, List<IntentFilter>>(new ComponentNameComparator());

    private File mPluginFile;
    private PackageParser mParser;
    private String mPackageName;
    private Context mHostContext;
    //宿主的包名信息
    private PackageInfo mHostPackageInfo;


    public String getmPackageName() {
        return mPackageName;
    }

    public PluginPackageMap(Context hostContext, File pluginFile) throws Exception {
        mHostContext = hostContext;
        mPluginFile = pluginFile;
        mParser = PackageParserManager.getInstance().getPluginParser(hostContext);
        mParser.parsePackage(pluginFile, 0);
//        插件的包名
        mPackageName=mParser.getPackageName();
//       ========================= Activity  ========================
        // 这里的datas是Activity组件映射
        List datas=mParser.getActivities();
        for (Object activity : datas) {
//            插件的包名  ----插件activity的类名
//            键
            ComponentName componentName = new ComponentName(mPackageName
                    , mParser.readNameFromComponent(activity));
            Log.i(TAG,"====PluginPackageMap()=="+componentName.getClassName());
//值
            mActivityObjCache.put(componentName, activity);
            ActivityInfo value = mParser.generateActivityInfo(activity, 0);
            fixApplicationInfo(value.applicationInfo);
            if (TextUtils.isEmpty(value.processName)) {
                value.processName = value.packageName;
            }
            mActivityInfoCache.put(componentName, value);
            List<IntentFilter> filters = mParser.readIntentFilterFromComponent(activity);
            mActivityIntentFilterCache.remove(componentName);
            mActivityIntentFilterCache.put(componentName, new ArrayList<IntentFilter>(filters));
        }

//       ========================= Service  ========================
        datas = mParser.getServices();
        for (Object data : datas) {
            ComponentName componentName = new ComponentName(mPackageName, mParser.readNameFromComponent(data));
            mServiceObjCache.put(componentName, data);
            ServiceInfo value = mParser.generateServiceInfo(data, 0);
            fixApplicationInfo(value.applicationInfo);
            if (TextUtils.isEmpty(value.processName)) {
                value.processName = value.packageName;
            }
            mServiceInfoCache.put(componentName, value);
            List<IntentFilter> filters = mParser.readIntentFilterFromComponent(data);
            mServiceIntentFilterCache.remove(componentName);
            mServiceIntentFilterCache.put(componentName, new ArrayList<IntentFilter>(filters));
        }

//       ========================= Providers  ========================
        datas = mParser.getProviders();
        for (Object data : datas) {
            ComponentName componentName = new ComponentName(mPackageName, mParser.readNameFromComponent(data));
            mProviderObjCache.put(componentName, data);
            ProviderInfo value = mParser.generateProviderInfo(data, 0);
            fixApplicationInfo(value.applicationInfo);
            if (TextUtils.isEmpty(value.processName)) {
                value.processName = value.packageName;
            }
            mProviderInfoCache.put(componentName, value);

            List<IntentFilter> filters = mParser.readIntentFilterFromComponent(data);
            mProviderIntentFilterCache.remove(componentName);
            mProviderIntentFilterCache.put(componentName, new ArrayList<IntentFilter>(filters));
        }
//       ========================= Receivers  ========================
        datas = mParser.getReceivers();
        for (Object data : datas) {
            ComponentName componentName = new ComponentName(mPackageName, mParser.readNameFromComponent(data));
            mReceiversObjCache.put(componentName, data);

            ActivityInfo value = mParser.generateActivityInfo(data, 0);
            // 根据业务要求，进行目录填充
            fixApplicationInfo(value.applicationInfo);
            if (TextUtils.isEmpty(value.processName)) {
                value.processName = value.packageName;
            }
            mReceiversInfoCache.put(componentName, value);


            List<IntentFilter> filters = mParser.readIntentFilterFromComponent(data);
            mReceiverIntentFilterCache.remove(componentName);
            mReceiverIntentFilterCache.put(componentName, new ArrayList<IntentFilter>(filters));
        }
    }

    /**
     * 已查看packagePaser对apk中applicationInfo的解析，在sourceDir，dataDir，nativeLibraryDir，processName
     * 这几个属性中并未进行赋值
     * @param applicationInfo
     * @return
     */
    private ApplicationInfo fixApplicationInfo(ApplicationInfo applicationInfo) {
        Log.i(TAG,"====fixApplicationInfo()==applicationInfo.processName："+applicationInfo.processName+" applicationInfo.packageName:"+applicationInfo.packageName);
        if (applicationInfo.sourceDir == null) {
            applicationInfo.sourceDir = mPluginFile.getPath();
        }
        if (applicationInfo.publicSourceDir == null) {
            applicationInfo.publicSourceDir = mPluginFile.getPath();
        }
        if (applicationInfo.dataDir == null) {
            applicationInfo.dataDir = PluginDirHelper.getPluginDataDir(mHostContext, applicationInfo.packageName);
        }
        applicationInfo.uid = mHostPackageInfo.applicationInfo.uid;
        if (applicationInfo.nativeLibraryDir == null) {
            applicationInfo.nativeLibraryDir = PluginDirHelper.getPluginNativeLibraryDir(mHostContext, applicationInfo.packageName);
        }
        if (TextUtils.isEmpty(applicationInfo.processName)) {
            applicationInfo.processName = applicationInfo.packageName;
        }
        return applicationInfo;
    }

    public ActivityInfo getActivityInfo(ComponentName className, int flags) throws Exception {
        ActivityInfo activityInfo;
        activityInfo = mActivityInfoCache.get(className);
        fixApplicationInfo(activityInfo.applicationInfo);
        if (TextUtils.isEmpty(activityInfo.processName)) {
            activityInfo.processName = activityInfo.packageName;
            return activityInfo;
        }
        return null;
    }


}
