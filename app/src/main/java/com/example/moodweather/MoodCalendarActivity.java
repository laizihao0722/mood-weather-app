package com.example.moodweather;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.ExecutorService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MoodCalendarActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCalendar;
    private TextView tvMonthTitle;
    private CalendarAdapter adapter;
    private Calendar currentCalendar;

    // MoodDao 和 线程执行器
    private MoodDao moodDao;
    private ExecutorService executorService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_calendar);

        tvMonthTitle = findViewById(R.id.tvMonthTitle);
        recyclerViewCalendar = findViewById(R.id.recyclerViewCalendar);

        // 初始化数据库访问和线程池
        MoodDatabase db = MoodDatabase.getDatabase(this);
        moodDao = db.moodDao();
        executorService = MoodDatabase.databaseWriteExecutor;

        currentCalendar = Calendar.getInstance();
        recyclerViewCalendar.setLayoutManager(new GridLayoutManager(this, 7));

        updateCalendar();

        // 上个月按钮逻辑
        findViewById(R.id.btnPrevMonth).setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            updateCalendar();
        });

        // 下个月按钮逻辑
        findViewById(R.id.btnNextMonth).setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            updateCalendar();
        });

        // 返回按钮
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void updateCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年 MM月", Locale.getDefault());
        tvMonthTitle.setText(sdf.format(currentCalendar.getTime()));

        // 将日历生成逻辑放入后台线程
        executorService.execute(() -> {
            List<CalendarDay> days = generateDaysForMonth(currentCalendar);

            // 数据获取完毕后，切换回主线程更新 UI
            runOnUiThread(() -> {
                adapter = new CalendarAdapter(days);
                recyclerViewCalendar.setAdapter(adapter);
            });
        });
    }

    // 生成当月的天数数据
    private List<CalendarDay> generateDaysForMonth(Calendar calendar) {
        List<CalendarDay> dayList = new ArrayList<>();

        Calendar tempCal = (Calendar) calendar.clone();
        tempCal.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);
        int maxDays = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 填充空白格子
        for (int i = 1; i < firstDayOfWeek; i++) {
            dayList.add(null);
        }

        // 填充真实日期
        for (int i = 1; i <= maxDays; i++) {
            // 注意：这个方法现在是同步执行的，因为它已经在后台线程中
            String emotion = getEmotionFromDatabase(tempCal.getTime());

            dayList.add(new CalendarDay(i, emotion));
            tempCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dayList;
    }

    // 【修改】真实数据库查询 (已经在后台线程中安全调用)
    private String getEmotionFromDatabase(Date date) {
        // 1. 设置日期的时间范围
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(date);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        long startTime = startCal.getTimeInMillis();

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(date);
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);
        long endTime = endCal.getTimeInMillis();

        // 调用 MoodDao 获取最新情绪
        return moodDao.getLatestEmotionForDay(startTime, endTime);
    }

    // 数据模型类 (保持不变)
    public static class CalendarDay {
        int dayNumber;
        String emotion; // 当日主要情绪

        public CalendarDay(int dayNumber, String emotion) {
            this.dayNumber = dayNumber;
            this.emotion = emotion;
        }
    }
}