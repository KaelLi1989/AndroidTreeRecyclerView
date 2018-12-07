package com.kaelli.androidtreerecyclerview.utils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;

import com.kaelli.androidtreerecyclerview.App;

import java.text.MessageFormat;

/**
 * Created by KaelLi on 2018/11/26.
 */
public class ResourcesUtils {
    /**
     * 获取Resources
     * @return  资源Resources
     */
    public static Resources getResources() {
        return App.getInstance().getResources();
    }

    /**
     * 获取strings.xml资源文件字符串
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串
     */
    public static String getString(int id) {
        return getResources().getString(id);
    }

    /**
     * 获取strings.xml资源文件字符串数组
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串数组
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 获取drawable资源文件图片
     *
     * @param id 资源文件id
     * @return 资源文件对应图片
     */
    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    /**
     * 获取colors.xml资源文件颜色
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色值
     */
    public static int getColor(int id) {
        return getResources().getColor(id);
    }

    /**
     * 获取颜色的状态选择器
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色状态
     */
    public static ColorStateList getColorStateList(int id) {
        return getResources().getColorStateList(id);
    }

    /**
     * 获取dimens资源文件中具体像素值
     *
     * @param id 资源文件id
     * @return 资源文件对应像素值
     */
    public static int getDimen(int id) {
        return getResources().getDimensionPixelSize(id);// 返回具体像素值
    }

    /**
     * 加载布局文件
     *
     * @param id 布局文件id
     * @return 布局view
     */
    public static View inflate(int id) {
        return View.inflate(App.getInstance(), id, null);
    }

    /**
     * 获取字符串资源并格式化输出
     *
     * @param resId
     * @param params
     * @return
     */
    public static String getString(@StringRes int resId, Object... params) {
        String _str = App.getInstance().getString(resId);
        if (TextUtils.isEmpty(_str)) {
            return "";
        }
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                params[i] += "";
            }
            try {
                _str = _str.contains("{0}") ? MessageFormat.format(_str, params) : String.format(_str, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _str.replace("\u2028","");
    }
}
