package com.example.moodweather;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// 导入 MPAndroidChart 必要的类
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class HistoryFragment extends Fragment {
    private PieChart pieChart;
    private BarChart barChart;
    private HistoryViewModel historyViewModel;
    //新增 UI 控件和状态变量
    private Button btnSwitchToday;
    private Button btnSwitchWeekly;
    private Button btnOpenCalendar;
    private TextView tvReportTitle;

    private enum ReportPeriod { TODAY, WEEKLY }
    private ReportPeriod currentPeriod = ReportPeriod.TODAY;

    public HistoryFragment() {
        // 空构造函数
    }

    // 定义情绪与颜色的统一映射（使用天气标签作为键，便于柱状图匹配）
    private static final Map<String, Integer> COLOR_MAP = new HashMap<String, Integer>() {{
        // 情绪标签与对应颜色
        put("开心", Color.parseColor("#FFD700")); // 黄金色
        put("难过", Color.parseColor("#4682B4")); // 钢铁蓝
        put("愤怒", Color.parseColor("#DC143C")); // 深红色
        put("困倦", Color.parseColor("#778899")); // 浅板岩灰
        put("崩溃", Color.parseColor("#696969")); // 暗灰色

        // 天气标签与对应颜色（必须与情绪颜色一一对应）
        put("Sunny", Color.parseColor("#FFD700"));
        put("Rainy", Color.parseColor("#4682B4"));
        put("Stormy", Color.parseColor("#DC143C"));
        put("Cloudy", Color.parseColor("#778899"));
        put("Typhoon", Color.parseColor("#696969"));
    }};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 绑定布局文件：这一行是将XML文件加载到Fragment中
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // 查找XML中定义的图表控件，通过ID关联起来
        pieChart = view.findViewById(R.id.pieChartMoods);
        barChart = view.findViewById(R.id.barChartWeather);

        // 初始化新增的 UI 控件
        btnSwitchToday = view.findViewById(R.id.btnSwitchToday);
        btnSwitchWeekly = view.findViewById(R.id.btnSwitchWeekly);
        btnOpenCalendar = view.findViewById(R.id.btnOpenCalendar);
        tvReportTitle = view.findViewById(R.id.tvReportTitle);

        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        // 设置切换按钮的监听器
        btnSwitchToday.setOnClickListener(v -> switchReportPeriod(ReportPeriod.TODAY));
        btnSwitchWeekly.setOnClickListener(v -> switchReportPeriod(ReportPeriod.WEEKLY));
        btnOpenCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MoodCalendarActivity.class);
            startActivity(intent);
        });

        // 统一设置 LiveData 观察者
        setupObservers();

        // 启动时默认显示今日统计
        switchReportPeriod(ReportPeriod.TODAY);

        return view;
    }

    // 统一设置 LiveData 观察者
    private void setupObservers() {
        // 观察今日数据
        historyViewModel.todayEmotionStats.observe(getViewLifecycleOwner(), moodStatsList -> {
            if (currentPeriod == ReportPeriod.TODAY) {
                setupPieChart(moodStatsList);
            }
        });

        historyViewModel.todayWeatherStats.observe(getViewLifecycleOwner(), weatherStatsList -> {
            if (currentPeriod == ReportPeriod.TODAY) {
                setupBarChart(weatherStatsList);
            }
        });

        // 观察周报数据
        historyViewModel.weeklyEmotionStats.observe(getViewLifecycleOwner(), moodStatsList -> {
            if (currentPeriod == ReportPeriod.WEEKLY) {
                setupPieChart(moodStatsList);
            }
        });

        historyViewModel.weeklyWeatherStats.observe(getViewLifecycleOwner(), weatherStatsList -> {
            if (currentPeriod == ReportPeriod.WEEKLY) {
                setupBarChart(weatherStatsList);
            }
        });
    }

    // 切换报告周期
    private void switchReportPeriod(ReportPeriod period) {
        int textColor = getChartTextColor();
        if (currentPeriod == period) return; // 避免重复切换

        currentPeriod = period;

        // 更新 UI 标题和按钮颜色
        if (period == ReportPeriod.TODAY) {
            tvReportTitle.setText("情绪回声");
            tvReportTitle.setTextColor(textColor);
            btnSwitchToday.setBackgroundColor(Color.parseColor("#42A5F5")); // 选中色
            btnSwitchWeekly.setBackgroundColor(Color.GRAY); // 未选中色

            // 立即刷新图表
            if (historyViewModel.todayEmotionStats.getValue() != null) {
                setupPieChart(historyViewModel.todayEmotionStats.getValue());
                setupBarChart(historyViewModel.todayWeatherStats.getValue());
            } else {
                setupPieChart(new ArrayList<>());
                setupBarChart(new ArrayList<>());
            }

        } else if (period == ReportPeriod.WEEKLY) {
            tvReportTitle.setText("历史周报情绪分布 (过去 7 天)");
            tvReportTitle.setTextColor(textColor);
            btnSwitchToday.setBackgroundColor(Color.GRAY); // 未选中色
            btnSwitchWeekly.setBackgroundColor(Color.parseColor("#42A5F5")); // 选中色

            // 立即刷新图表
            if (historyViewModel.weeklyEmotionStats.getValue() != null) {
                setupPieChart(historyViewModel.weeklyEmotionStats.getValue());
                setupBarChart(historyViewModel.weeklyWeatherStats.getValue());
            } else {
                setupPieChart(new ArrayList<>());
                setupBarChart(new ArrayList<>());
            }
        }
    }

    private int getChartTextColor() {
        if (getContext() == null) return Color.BLACK; // 默认返回黑色

        // 假设 ThemeManager.loadTheme 和 ThemeManager.THEME_DARK 已在项目中定义
        String currentTheme = ThemeManager.loadTheme(getContext());

        // 假设 "Dark" (暗黑系) 的主题常量为 ThemeManager.THEME_DARK
        if (ThemeManager.THEME_DARK.equals(currentTheme)) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    // 饼图 (情绪分布) 逻辑
    private void setupPieChart(List<MoodStats> moodStatsList) {
        int textColor = getChartTextColor();

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        // 循环遍历真实数据
        for (MoodStats stats : moodStatsList) {
            entries.add(new PieEntry(stats.count, stats.type));

            // 从统一的 COLOR_MAP 中获取颜色
            Integer color = COLOR_MAP.get(stats.type); // stats.type 是情绪标签
            if (color != null) {
                colors.add(color);
            } else {
                colors.add(Color.GRAY); // 默认颜色
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "情绪回声");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        // 配置和刷新图表
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("你的心情");
        pieChart.setCenterTextSize(16f);
        pieChart.getLegend().setTextColor(textColor);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }


    // 柱状图 (天气统计) 逻辑
    private void setupBarChart(List<MoodStats> weatherStatsList) {
        int textColor = getChartTextColor();

        ArrayList<BarEntry> entries = new ArrayList<>();
        String[] weatherLabels = new String[weatherStatsList.size()];
        // 柱状图的颜色列表
        ArrayList<Integer> colors = new ArrayList<>();

        for (int i = 0; i < weatherStatsList.size(); i++) {
            MoodStats stats = weatherStatsList.get(i);
            entries.add(new BarEntry(i, stats.count));
            weatherLabels[i] = stats.type; // 天气标签
            // 从统一的 COLOR_MAP 中获取颜色
            Integer color = COLOR_MAP.get(stats.type);
            if (color != null) {
                colors.add(color);
            } else {
                colors.add(Color.GRAY); // 默认颜色
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "出现次数");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(textColor);

        BarData data = new BarData(dataSet);
        barChart.setData(data);

        // 设置 X 轴的标签（显示文字）
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weatherLabels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false); // 隐藏网格线
        xAxis.setTextColor(textColor);

        // 配置和刷新图表
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setTextColor(textColor);
        barChart.getLegend().setTextColor(textColor);
        barChart.animateY(1000);
        barChart.invalidate();
    }
}