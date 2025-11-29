package com.example.moodweather;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // 1. 绑定布局文件：这一行是将XML文件加载到Fragment中
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // 2. 查找XML中定义的图表控件，通过ID关联起来
        pieChart = view.findViewById(R.id.pieChartMoods);
        barChart = view.findViewById(R.id.barChartWeather);

        // 3. 自动调用方法填充数据和配置图表
        // 初始化 ViewModel
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        // 观察情绪统计数据的变化
        historyViewModel.emotionStats.observe(getViewLifecycleOwner(), moodStatsList -> {
            // 数据变化时，更新饼图
            setupPieChart(moodStatsList);
        });

        // 观察天气统计数据的变化
        historyViewModel.weatherStats.observe(getViewLifecycleOwner(), weatherStatsList -> {
            // 数据变化时，更新柱状图
            setupBarChart(weatherStatsList);
        });

        return view;
    }


    // --- 饼图 (情绪分布) 逻辑 ---
    private void setupPieChart(List<MoodStats> moodStatsList) {
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

        PieDataSet dataSet = new PieDataSet(entries, "情绪分布");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        // 配置和刷新图表
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("本月心情");
        pieChart.setCenterTextSize(16f);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    // --- 柱状图 (天气统计) 逻辑 ---
    private void setupBarChart(List<MoodStats> weatherStatsList) {
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

        BarDataSet dataSet = new BarDataSet(entries, "出现天数");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.BLACK);

        BarData data = new BarData(dataSet);
        barChart.setData(data);

        // 设置 X 轴的标签（显示文字）
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weatherLabels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false); // 隐藏网格线

        // 配置和刷新图表
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate();
    }
}