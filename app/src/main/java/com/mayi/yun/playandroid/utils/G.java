package com.mayi.yun.playandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_IN;
import static android.util.TypedValue.COMPLEX_UNIT_MM;
import static android.util.TypedValue.COMPLEX_UNIT_PT;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * ================================================
 * 作    者： wh
 * 时    间： 2016/7/8
 * 描    述：工具类汇总
 * 版    本：
 * 修订历史：
 * 主要接口：
 * ================================================
 */
public class G {

    /**
     * 调试信息
     */
    public static boolean DEBUG = true;
    /**
     * toast提示
     */
    private static Toast toast;

    /**
     * 尺寸
     */
    public static final class size {
        /**
         * 屏幕宽
         */
        public static int W = 480;
        /**
         * 屏幕高
         */
        public static int H = 800;
    }

    /**
     * 截屏
     */
    public static void screenshots(Activity activity, boolean isFullScreen) {
        File mFileTemp = new File(activity.getCacheDir(), "screenshots.jpg");
        try {
            //View是你需要截图的View
            View decorView = activity.getWindow().getDecorView();
            decorView.setDrawingCacheEnabled(true);
            decorView.buildDrawingCache();
            Bitmap b1 = decorView.getDrawingCache();
            // 获取状态栏高度 /
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            // 获取屏幕长和高 Get screen width and height
            int width = activity.getWindowManager().getDefaultDisplay().getWidth();
            int height = activity.getWindowManager().getDefaultDisplay().getHeight();
            // 去掉标题栏 Remove the statusBar Height
            Bitmap bitmap;
            if (isFullScreen) {
                bitmap = Bitmap.createBitmap(b1, 0, 0, width, height);
            } else {
                bitmap = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
            }
            decorView.destroyDrawingCache();
            FileOutputStream out = new FileOutputStream(mFileTemp);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化屏幕尺寸
     */
    public static void initDisplaySize(Activity activity) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        size.W = mDisplayMetrics.widthPixels;
        size.H = mDisplayMetrics.heightPixels;
    }

    /**
     * 提示
     *
     * @param msg 提示信息
     */
    public static void showToast(Context context, String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, size.H / 4);
        toast.show();
    }

    /**
     * 记录调试信息
     *
     * @param msg 调试信息
     */
    public static void log(Object msg) {
        if (DEBUG) {
            Log.i("TAG", String.valueOf(msg));
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmteny(String values) {
        if (null == values || "".equals(values) || "null".equals(values)) {
            return true;
        }
        return false;
    }

    /**
     * 删除缓存
     */
    public static int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /**
     * 清除WebView缓存
     */
    public static void clearWebViewCache(Context context) {
        //清理Webview缓存数据库
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //WebView 缓存文件
        File appCacheDir = context.getDir("cache", Context.MODE_PRIVATE);
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            context.deleteFile(appCacheDir.getAbsolutePath());
        }
    }

    /**
     * 进入权限管理页面
     *
     * @param context
     */
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    /**
     * 判断是否全为空格
     */
    public static boolean isAllSpace(String tag) {
        char[] chars = tag.toCharArray();
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                count++;
            }
        }
        if (count == chars.length) {
            return true;
        } else {
            return false;
        }
    }
    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case COMPLEX_UNIT_PX:
                return value;
            case COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }
}


