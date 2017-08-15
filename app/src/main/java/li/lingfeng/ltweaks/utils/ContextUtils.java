package li.lingfeng.ltweaks.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.util.TypedValue;
import android.widget.Toast;

import li.lingfeng.ltweaks.MyApplication;
import li.lingfeng.ltweaks.prefs.PackageNames;

/**
 * Created by smallville on 2017/1/25.
 */

public class ContextUtils {

    public static Context createPackageContext(String packageName) {
        try {
            return MyApplication.instance().createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("Can't create context for package " + packageName + ", " + e.getMessage());
            Logger.stackTrace(e);
            return null;
        }
    }

    public static Context createLTweaksContext() {
        return createPackageContext(PackageNames.L_TWEAKS);
    }

    public static String getResNameById(int id) {
        return getResNameById(id, MyApplication.instance());
    }

    public static String getResNameById(int id, Context context) {
        if (id < 0x7F000000)
            return "";
        try {
            return context.getResources().getResourceEntryName(id);
        } catch (Exception e) {
            return "";
        }
    }

    public static int getResId(String name, String type) {
        return getResId(name, type, MyApplication.instance());
    }

    public static int getResId(String name, String type, Context context) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }

    public static int getIdId(String name) {
        return getIdId(name, MyApplication.instance());
    }

    public static int getIdId(String name, Context context) {
        return getResId(name, "id", context);
    }

    public static int getStringId(String name) {
        return getStringId(name, MyApplication.instance());
    }

    public static int getStringId(String name, Context context) {
        return getResId(name, "string", context);
    }

    public static int getDrawableId(String name) {
        return getDrawableId(name, MyApplication.instance());
    }

    public static int getDrawableId(String name, Context context) {
        return getResId(name, "drawable", context);
    }

    public static int getMipmapId(String name) {
        return getMipmapId(name, MyApplication.instance());
    }

    public static int getMipmapId(String name, Context context) {
        return getResId(name, "mipmap", context);
    }

    public static String getString(String name) {
        return getString(name, MyApplication.instance());
    }

    public static String getString(String name, Context context) {
        return context.getString(getStringId(name, context));
    }

    public static Drawable getDrawable(String name) {
        return getDrawable(name, MyApplication.instance());
    }

    public static Drawable getDrawable(String name, Context context) {
        return context.getResources().getDrawable(getDrawableId(name, context));
    }

    public static Drawable getMipmap(String name) {
        return getMipmap(name, MyApplication.instance());
    }

    public static Drawable getMipmap(String name, Context context) {
        return context.getResources().getDrawable(getMipmapId(name, context));
    }

    public static int getLayoutId(String name) {
        return getLayoutId(name, MyApplication.instance());
    }

    public static int getLayoutId(String name, Context context) {
        return getResId(name, "layout", context);
    }

    public static int getAttrId(String name) {
        return getAttrId(name, MyApplication.instance());
    }

    public static int getAttrId(String name, Context context) {
        return getResId(name, "attr", context);
    }

    public static XmlResourceParser getLayout(String name) {
        return getLayout(name, MyApplication.instance());
    }

    public static XmlResourceParser getLayout(String name, Context context) {
        return context.getResources().getLayout(getLayoutId(name, context));
    }

    public static int getThemeId(String name) {
        return getThemeId(name, MyApplication.instance());
    }

    public static int getThemeId(String name, Context context) {
        return getResId(name, "style", context);
    }

    public static int getColorFromTheme(Resources.Theme theme, String name) {
        int idColor = getAttrId(name);
        if (idColor <= 0)
            return Color.RED;
        return getColorFromTheme(theme, idColor);
    }

    public static int getColorFromTheme(Resources.Theme theme, int id) {
        TypedValue value = new TypedValue();
        theme.resolveAttribute(id, value, true);
        if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            return value.data;
        } else {
            return Color.RED;
        }
    }

    public static int getThemeValue(Resources.Theme theme, String name) {
        int id = getAttrId(name);
        if (id <= 0) {
            return 0;
        }
        return getThemeValue(theme, id);
    }

    public static int getThemeValue(Resources.Theme theme, int id) {
        TypedValue value = new TypedValue();
        theme.resolveAttribute(id, value, true);
        return value.data;
    }

    public static int getColorFromStyle(int idStyle, String name) {
        return getColorFromStyle(idStyle, name, MyApplication.instance());
    }

    public static int getColorFromStyle(int idStyle, String name, Context context) {
        int id = getAttrId(name);
        if (id <= 0) {
            return Color.RED;
        }
        return getColorFromStyle(idStyle, id, context);
    }

    public static int getColorFromStyle(int idStyle, int id) {
        return getColorFromStyle(idStyle, id, MyApplication.instance());
    }

    public static int getColorFromStyle(int idStyle, int id, Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(idStyle, new int[] { id });
        return typedArray.getColor(0, Color.RED);
    }

    public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                MyApplication.instance().getResources().getDisplayMetrics());
    }

    public static int px2dp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static Drawable getAppIcon() {
        return getAppIcon(MyApplication.instance().getPackageName());
    }

    public static Drawable getAppIcon(String packageName) {
        try {
            return MyApplication.instance().getPackageManager().getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("Can't get icon from app " + packageName);
            Logger.stackTrace(e);
            return new ColorDrawable(Color.WHITE);
        }
    }

    public static String getAppName() {
        return getAppName(MyApplication.instance().getPackageName());
    }

    public static String getAppName(String packageName) {
        try {
            ApplicationInfo appInfo = MyApplication.instance().getPackageManager().getApplicationInfo(packageName, 0);
            return MyApplication.instance().getPackageManager().getApplicationLabel(appInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            Logger.stackTrace(e);
            return "";
        }
    }

    public static boolean isCallingFromLTweaks() {
        try {
            int uid = Binder.getCallingUid();
            ApplicationInfo appInfo = MyApplication.instance().getPackageManager().getApplicationInfo(PackageNames.L_TWEAKS, 0);
            if (uid != appInfo.uid) {
                return true;
            }
        } catch (Exception e) {}
        return false;
    }

    public static String getCallingPackage() {
        try {
            int uid = Binder.getCallingUid();
            return MyApplication.instance().getPackageManager().getNameForUid(uid);
        } catch (Exception e) {}
        return null;
    }

    public static void startBrowser(Context context, String url) {
        Logger.v("startBrowser " + url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void openFolder(Context context, String path) {
        Logger.v("openFolder " + path);
        Uri uri = Uri.parse(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "resource/folder");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show();
        }
    }
}
