//ViewModel负责从 DAO 获取数据并将其暴露给 Fragment
package com.example.moodweather;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.Calendar;
import java.util.TimeZone;

public class HistoryViewModel extends AndroidViewModel {

    private final MoodDao moodDao;
    private final LiveData<List<MoodEntry>> mAllEntries;
    public final LiveData<List<MoodStats>> todayEmotionStats;
    public final LiveData<List<MoodStats>> todayWeatherStats;
    public final LiveData<List<MoodStats>> weeklyEmotionStats;
    public final LiveData<List<MoodStats>> weeklyWeatherStats;

    public HistoryViewModel(Application application) {
        super(application);
        MoodDatabase db = MoodDatabase.getDatabase(application);
        moodDao = db.moodDao();

        mAllEntries = moodDao.getAllEntries();
        // 计算今天零点的时间戳 (毫秒)
        long startOfToday = getStartOfTodayTimestamp();

        // // 直接从 DAO 获取 LiveData，当数据库数据改变时，Fragment 会自动更新
        todayEmotionStats = moodDao.getEmotionStatsSince(startOfToday);
        todayWeatherStats = moodDao.getWeatherStatsSince(startOfToday);

        long startOfLastWeek = getStartOfLastWeekTimestamp();
        long endOfLastWeek = System.currentTimeMillis();

        weeklyEmotionStats = moodDao.getEmotionStatsBetween(startOfLastWeek, endOfLastWeek);
        weeklyWeatherStats = moodDao.getWeatherStatsBetween(startOfLastWeek, endOfLastWeek);
    }

    //获取数据库中所有的日记条目，按时间倒序排列。
    public LiveData<List<MoodEntry>> getAllEntries() {
        return mAllEntries;
    }

    // 示例：插入数据的方法 (用于测试或首页记录)
    public void insert(MoodEntry mood) {
        MoodDatabase.databaseWriteExecutor.execute(() -> {
            moodDao.insert(mood);
        });
    }

    // 计算今天开始（零点）的时间戳
    private long getStartOfTodayTimestamp() {
        Calendar calendar = Calendar.getInstance();
        // 设置到当前时区 (确保与用户设备一致)
        calendar.setTimeZone(TimeZone.getDefault());

        // 清零小时、分钟、秒和毫秒
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 返回今天的零点时间戳 (毫秒)
        return calendar.getTimeInMillis();
    }

    // 计算过去7天开始的时间戳
    private long getStartOfLastWeekTimestamp() {
        Calendar calendar = Calendar.getInstance();
        // 倒退7天
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        // 清零小时、分钟、秒和毫秒，确保是从7天前的0点开始
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}
