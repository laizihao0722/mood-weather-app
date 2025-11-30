package com.example.moodweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // 空构造函数
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 加载布局文件
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        // 找到“切换主题”按钮
        Button btnShowThemeDialog = view.findViewById(R.id.btnShowThemeDialog);
        // 设置点击事件：弹出主题选择对话框
        btnShowThemeDialog.setOnClickListener(v -> showThemeSelectionDialog());

        return view;
    }

    private void showThemeSelectionDialog() {
        if (getContext() == null) return;

        // 1. 使用 Builder 创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // 2. 引入自定义对话框布局
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_theme_selector, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // 3. 找到对话框中的按钮
        Button btnHealing = dialogView.findViewById(R.id.btnThemeHealingDialog);
        Button btnDark = dialogView.findViewById(R.id.btnThemeDarkDialog);
        Button btnEnergy = dialogView.findViewById(R.id.btnThemeEnergyDialog);

        // 4. 设置按钮点击事件
        btnHealing.setOnClickListener(v -> {
            setThemeAndRecreate(ThemeManager.THEME_HEALING);
            dialog.dismiss(); // 关闭对话框
        });

        btnDark.setOnClickListener(v -> {
            setThemeAndRecreate(ThemeManager.THEME_DARK);
            dialog.dismiss(); // 关闭对话框
        });

        btnEnergy.setOnClickListener(v -> {
            setThemeAndRecreate(ThemeManager.THEME_ENERGY);
            dialog.dismiss(); // 关闭对话框
        });

        // 5. 显示对话框
        dialog.show();
    }

    //保存主题设置并刷新 Activity
    private void setThemeAndRecreate(String themeName) {
        // 1. 保存用户选择的主题到 SharedPreferences
        ThemeManager.saveTheme(getContext(), themeName);

        // 2. 立即销毁并重建 Activity 以应用新主题
        if (getActivity() != null) {
            Toast.makeText(getContext(), "主题已切换为 " + themeName + "，正在刷新...", Toast.LENGTH_SHORT).show();
            getActivity().recreate();
        }
    }
}