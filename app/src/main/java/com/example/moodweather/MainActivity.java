package com.example.moodweather;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements SuggestionNavigator {

    private BottomNavigationView bottomNav;

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

    @Override
    public void navigateToSuggestion2() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Suggestion2Fragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void navigateToSuggestion3() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Suggestion3Fragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void navigateToSuggestion4() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Suggestion4Fragment())
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

    @Override
    public void navigateToResult() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SuggestionResultFragment())
                .addToBackStack(null)
                .commit();
    }
}
