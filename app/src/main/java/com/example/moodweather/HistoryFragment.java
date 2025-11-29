package com.example.moodweather;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class HistoryFragment extends Fragment {
    private PieChart pieChart;
    private BarChart barChart;

    public HistoryFragment() {
        // 空构造函数
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1. 绑定布局文件：这一行是将XML文件加载到Fragment中
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // 2. 查找XML中定义的图表控件，通过ID关联起来
        pieChart = view.findViewById(R.id.pieChartMoods);
        barChart = view.findViewById(R.id.barChartWeather);

        // 3. 调用方法来填充数据和配置图表
        setupPieChart();
        setupBarChart();

        return view;
    }

    // --- 饼图 (情绪分布) 逻辑 ---
    private void setupPieChart() {
        // ********************************************
        // TODO: 替换为从数据库读取的真实情绪数据
        // ********************************************
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "开心"));
        entries.add(new PieEntry(30f, "平静"));
        entries.add(new PieEntry(20f, "焦虑"));
        entries.add(new PieEntry(10f, "生气"));

        // 设置颜色
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FFD700"));
        colors.add(Color.parseColor("#87CEEB"));
        colors.add(Color.parseColor("#708090"));
        colors.add(Color.parseColor("#FF6347"));

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
    private void setupBarChart() {
        // ********************************************
        // TODO: 替换为从数据库读取的真实天气数据
        // ********************************************
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 12)); // 晴朗次数
        entries.add(new BarEntry(1, 8));  // 小雨次数
        entries.add(new BarEntry(2, 5));  // 阴天次数
        entries.add(new BarEntry(3, 3));  // 雷暴次数

        BarDataSet dataSet = new BarDataSet(entries, "出现天数");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);

        BarData data = new BarData(dataSet);
        barChart.setData(data);

        // 设置 X 轴的标签（显示文字）
        String[] weatherLabels = new String[]{"晴朗", "小雨", "阴天", "雷暴"};
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