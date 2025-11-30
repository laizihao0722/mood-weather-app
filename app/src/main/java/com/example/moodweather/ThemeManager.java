//管理 SharedPreferences 的读写和主题 ID 的映射
package com.example.moodweather;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeManager {
    // SharedPreferences 文件名和键名
    public static final String PREFS_NAME = "AppThemePrefs";
    public static final String KEY_CURRENT_THEME = "CurrentTheme";

    // 主题名称常量
    public static final String THEME_HEALING = "Healing";
    public static final String THEME_DARK = "Dark";
    public static final String THEME_ENERGY = "Energy";
    public static final String THEME_DEFAULT = THEME_HEALING;

    // 根据主题名称获取对应的主题资源ID
    public static int getThemeResId(String themeName) {
        switch (themeName) {
            case THEME_DARK:
                return R.style.Theme_MoodWeather_Dark;
            case THEME_ENERGY:
                return R.style.Theme_MoodWeather_Energy;
            case THEME_HEALING:
            default:
                return R.style.Theme_MoodWeather_Healing;
        }
    }

    // 从 SharedPreferences 中加载保存的主题
    public static String loadTheme(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // 如果没有保存过，默认返回治愈系
        return prefs.getString(KEY_CURRENT_THEME, THEME_DEFAULT);
    }

    // 保存用户选择的新主题
    public static void saveTheme(Context context, String themeName) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_CURRENT_THEME, themeName).apply();
    }
}
