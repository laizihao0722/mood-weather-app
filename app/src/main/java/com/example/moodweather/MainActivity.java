package com.example.moodweather;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置主题
        String currentTheme = ThemeManager.loadTheme(this);
        setTheme(ThemeManager.getThemeResId(currentTheme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBottomNavigation();

        // 默认显示主页
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }

    private void initBottomNavigation() {
        BottomNavigationView bottomNav;
        bottomNav = findViewById(R.id.bottom_navigation);

        // 设置菜单资源
        //bottomNav.inflateMenu(R.menu.bottom_nav_menu);

        bottomNav.setOnItemSelectedListener(item -> {
            //点击对应按钮跳转界面
            if (item.getItemId() == R.id.navigation_home) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
                return true;
            } else if(item.getItemId() == R.id.navigation_advice){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AdviceFragment())
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
            return false;
        });
    }
}