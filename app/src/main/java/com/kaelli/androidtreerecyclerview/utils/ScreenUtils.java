package com.kaelli.androidtreerecyclerview.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.kaelli.androidtreerecyclerview.App;

/**
 * Created by KaelLi on 2018/11/26.
 */
public class ScreenUtils {
    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet() {
        return (ResourcesUtils.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取设备宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return ResourcesUtils.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度
     *
     * @return
     */
    public static int getScreenHeight() {
        return ResourcesUtils.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * dp转px
     * @return
     */
    public static int dip2px(float dipValue) {
        float m = ResourcesUtils.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    /**
     * 获取StatusBar高度
     * @return  StatusBarHeight
     */
    public static int getStatusBarHeight() {
        //获取状态栏的高度
        int resourceId = ResourcesUtils.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return ResourcesUtils.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 获取屏幕尺寸
     */
    public static Point getScreenSize(){
        WindowManager windowManager = (WindowManager) App.getInstance().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            return point;
        } else {
            return new Point(getScreenWidth(), getScreenHeight());
        }
    }
}
