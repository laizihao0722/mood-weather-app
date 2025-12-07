package com.example.moodweather;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
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
    private Switch switchDailyReminder;
    private Spinner spinnerAvoidMood;
    private boolean isSpinnerInitialLoad = true;

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

        isSpinnerInitialLoad = true;
        switchDailyReminder = view.findViewById(R.id.switchDailyReminder);
        spinnerAvoidMood = view.findViewById(R.id.spinnerAvoidMood);
        Button btnUsageGuide = view.findViewById(R.id.btnUsageGuide);
        Button btnPrivacyPolicy = view.findViewById(R.id.btnPrivacyPolicy);
        Button btnFeedback = view.findViewById(R.id.btnFeedback);

        // 手动设置 Spinner 适配器
        if (getContext() != null) {
            // 使用 R.array.avoid_mood_options 资源创建适配器
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.avoid_mood_options,
                    android.R.layout.simple_spinner_item // 简单样式
            );
            // 设置下拉列表样式
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAvoidMood.setAdapter(adapter); // 设置适配器
        }

        // 加载当前设置
        loadGoalSettings(getContext());
        // 设置每日提醒开关监听器
        switchDailyReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            GoalManager.saveDailyRecordGoal(getContext(), isChecked);
            setDailyReminderAlarm(getContext(), isChecked); // 开启/关闭定时闹钟
            Toast.makeText(getContext(), isChecked ? "每日提醒已开启" : "每日提醒已关闭", Toast.LENGTH_SHORT).show();
        });

        // 设置避免情绪 Spinner 监听器
        spinnerAvoidMood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSpinnerInitialLoad) {
                    isSpinnerInitialLoad = false;
                    return; // 退出，不执行后续逻辑
                }
                // 从 Spinner 选项中提取情绪标签 (例如：从 "愤怒 (Angry)" 提取 "愤怒")
                String selectedItem = parent.getItemAtPosition(position).toString();
                String selectedMood = selectedItem.split(" ")[0];

                GoalManager.saveAvoidMoodGoal(getContext(), selectedMood);
                if (!selectedMood.equals(GoalManager.NO_AVOID_MOOD)) {
                    Toast.makeText(getContext(), "目标：减少记录 [" + selectedMood + "]", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 使用说明
        btnUsageGuide.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HelpFragment())
                        .addToBackStack(null) // 加入返回栈，以便用户按返回键
                        .commit();
            }
        });

        // 隐私政策与服务条款
        btnPrivacyPolicy.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PrivacyPolicyFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // 意见反馈/联系我们
        btnFeedback.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FeedbackFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

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
        Button btnSweet = dialogView.findViewById(R.id.btnThemeSweetDialog);
        Button btnWarm = dialogView.findViewById(R.id.btnThemeWarmDialog);
        Button btnBlue = dialogView.findViewById(R.id.btnThemeBlueDialog);

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

        btnSweet.setOnClickListener(v -> {
            setThemeAndRecreate(ThemeManager.THEME_SWEET);
            dialog.dismiss();
        });

        btnWarm.setOnClickListener(v -> {
            setThemeAndRecreate(ThemeManager.THEME_WARM);
            dialog.dismiss();
        });

        btnBlue.setOnClickListener(v -> {
            setThemeAndRecreate(ThemeManager.THEME_BLUE);
            dialog.dismiss();
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

    private void loadGoalSettings(Context context) {
        if (context == null) return;

        // 1. 加载每日记录提醒设置
        switchDailyReminder.setChecked(GoalManager.isDailyRecordGoalSet(context));

        // 2. 加载避免情绪目标
        String avoidMood = GoalManager.getAvoidMoodGoal(context);
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinnerAvoidMood.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                String itemText = adapter.getItem(i).toString().split(" ")[0];
                if (itemText.equals(avoidMood)) {
                    spinnerAvoidMood.setSelection(i);
                    break;
                }
            }
        }
    }

    //代理到 MainActivity 设置闹钟的方法
    private void setDailyReminderAlarm(Context context, boolean enable) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).scheduleDailyReminder(enable);
        }
    }
}