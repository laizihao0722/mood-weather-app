package com.example.moodweather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SuggestionNavigator {

    private BottomNavigationView bottomNav;
    private static final int DAILY_REMINDER_REQUEST_CODE = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置主题
        String currentTheme = ThemeManager.loadTheme(this);
        setTheme(ThemeManager.getThemeResId(currentTheme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationHelper.createNotificationChannel(this);
        initBottomNavigation();

        // 默认显示主页
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
        //启动时检查并设置每日提醒
        scheduleDailyReminder(GoalManager.isDailyRecordGoalSet(this));
    }

    private void initBottomNavigation() {
        bottomNav = findViewById(R.id.bottom_navigation);

        // 设置菜单资源
        //bottomNav.inflateMenu(R.menu.bottom_nav_menu);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
                return true;
            } else if (item.getItemId() == R.id.navigation_advice) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Suggestion1Fragment())
                        .commit();
                return true;
            } else if (item.getItemId() == R.id.navigation_history) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HistoryFragment())
                        .commit();
                return true;
            } else if (item.getItemId() == R.id.navigation_settings) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SettingsFragment())
                        .commit();
                return true;
            }
            return false; // 默认返回false
        });
    }

    // 设置每日定时提醒闹钟 (每天 10:00)
    public void scheduleDailyReminder(boolean enable) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                DAILY_REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0)
        );

        if (alarmManager == null) return;

        if (enable) {
            // 设置闹钟时间为每天晚上10点
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 10); // 10:00
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            // 如果设置时间已过，则设置为明天10点
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }

            // 设置重复闹钟 (每天重复)
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, // 实时时钟唤醒设备
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );

        } else {
            // 取消闹钟
            alarmManager.cancel(pendingIntent);
        }
    }

    @Override
    public void navigateToSuggestion2() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Suggestion2Fragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void navigateToSuggestion5() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Suggestion5Fragment())
                .addToBackStack(null)
                .commit();
    }
}
