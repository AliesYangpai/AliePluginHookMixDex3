package org.alie.aliepluginhookmixdex3.plugin.parser;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;

import java.io.File;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Alie on 2019/10/23.
 * 类描述 自定义PackageParse来模仿pms解析apk
 * 版本
 */
public abstract class PackageParser {

    protected Object mPackageParser;


    /**
     * ===================定义解析方法=========================
     */
    public abstract void parsePackage(File packageFile, int flags) throws Exception;

    /**
     * ===================定义获取组件方法=========================
     */
    public abstract List getActivities() throws Exception;

    public abstract List getServices() throws Exception;

    public abstract List getProviders() throws Exception;

    public abstract List getPermissions() throws Exception;

    public abstract List getPermissionGroups() throws Exception;

    public abstract List getRequestedPermissions() throws Exception;

    public abstract List getReceivers() throws Exception;

    public abstract List getInstrumentations() throws Exception;

    public abstract String getPackageName() throws Exception;

    /**
     * ==================定义generateInfo方法=====================
     */
    /**
     *
     * @param activity 这个activity并非组件，而是一个信息
     * @param flags
     * @return
     */
    public abstract ActivityInfo generateActivityInfo(Object activity, int flags) throws Exception;

    public abstract ServiceInfo generateServiceInfo(Object service, int flags) throws Exception;

    public abstract ProviderInfo generateProviderInfo(Object provider, int flags) throws Exception;

    public abstract InstrumentationInfo generateInstrumentationInfo(Object instrumentation, int flags) throws Exception;

    public abstract ApplicationInfo generateApplicationInfo(int flags) throws Exception;

    public abstract PermissionGroupInfo generatePermissionGroupInfo(Object permissionGroup, int flags) throws Exception;

    public abstract PermissionInfo generatePermissionInfo(Object permission, int flags) throws Exception;

    public abstract PackageInfo generatePackageInfo(int gids[], int flags, long firstInstallTime, long lastUpdateTime, HashSet<String> grantedPermissions) throws Exception;


}
