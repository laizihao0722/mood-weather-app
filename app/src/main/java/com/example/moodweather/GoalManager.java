package com.example.moodweather;

import android.content.Context;
import android.content.SharedPreferences;

public class GoalManager {
    private static final String PREFS_NAME = "GoalPrefs";
    // 目标键名：每日是否记录心情
    public static final String KEY_DAILY_RECORD_GOAL = "DailyRecordGoal";
    // 目标键名：希望减少记录的情绪 (如 "愤怒", "崩溃")
    public static final String KEY_AVOID_MOOD_GOAL = "AvoidMoodGoal";
    // 默认值：不设置避免情绪
    public static final String NO_AVOID_MOOD = "None";

    public static void saveDailyRecordGoal(Context context, boolean isSet) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_DAILY_RECORD_GOAL, isSet).apply();
    }

    public static boolean isDailyRecordGoalSet(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // 默认开启每日记录提醒
        return prefs.getBoolean(KEY_DAILY_RECORD_GOAL, true);
    }

    public static void saveAvoidMoodGoal(Context context, String mood) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_AVOID_MOOD_GOAL, mood).apply();
    }

    public static String getAvoidMoodGoal(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_AVOID_MOOD_GOAL, NO_AVOID_MOOD);
    }
}