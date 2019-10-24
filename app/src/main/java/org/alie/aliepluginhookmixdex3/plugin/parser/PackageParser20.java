package org.alie.aliepluginhookmixdex3.plugin.parser;

import android.content.Context;
import android.util.DisplayMetrics;


import org.alie.aliepluginhookmixdex3.plugin.utils.reflect.MethodUtils;

import java.io.File;

/**
 * Created by Administrator on 2018/4/13.
 */

public class PackageParser20 extends PackageParser21{

    public PackageParser20(Context mContext) throws Exception {
        super(mContext);
    }

    @Override
    public void parsePackage(File file, int flags) throws Exception {
        super.parsePackage(file, flags);
        DisplayMetrics metrics = new DisplayMetrics();
        metrics.setToDefaults();
        String destCodePath = file.getPath();
        mPackageParser = MethodUtils.invokeConstructor(sPackageParserClass, destCodePath);
        mPackage = MethodUtils.invokeMethod(mPackageParser, "parsePackage"
                , file, destCodePath, metrics, flags);

    }
}
