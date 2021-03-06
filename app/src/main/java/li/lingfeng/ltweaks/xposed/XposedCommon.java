package li.lingfeng.ltweaks.xposed;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageParser;
import android.os.Process;

import de.robv.android.xposed.XC_MethodHook;
import li.lingfeng.ltweaks.prefs.ClassNames;
import li.lingfeng.ltweaks.utils.Logger;

/**
 * Created by lilingfeng on 2017/6/30.
 */

public abstract class XposedCommon extends XposedBase {

    protected void hookAndSetComponentExported(final String packageName, final String componentName) {
        hookAllMethods(ClassNames.PACKAGE_PARSER, "parsePackage", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                PackageParser.Package pkg = (PackageParser.Package) param.getResult();
                if (pkg == null || pkg.packageName != packageName) {
                    return;
                }

                for (PackageParser.Activity activity : pkg.activities) {
                    if (activity.info.name.equals(componentName)) {
                        Logger.i("Set " + componentName + " exported to true.");
                        activity.info.exported = true;
                        activity.info.launchMode = ActivityInfo.LAUNCH_MULTIPLE;
                        break;
                    }
                }
            }
        });
    }

    protected boolean isUserInstalledApp() {
        return lpparam.appInfo.uid >= Process.FIRST_APPLICATION_UID && lpparam.appInfo.uid <= Process.LAST_APPLICATION_UID;
    }
}
