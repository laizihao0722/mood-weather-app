//ViewModel负责从 DAO 获取数据并将其暴露给 Fragment
package com.example.moodweather;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private final MoodDao moodDao;
    public final LiveData<List<MoodStats>> emotionStats;
    public final LiveData<List<MoodStats>> weatherStats;

    public HistoryViewModel(Application application) {
        super(application);
        MoodDatabase db = MoodDatabase.getDatabase(application);
        moodDao = db.moodDao();

        // 直接从 DAO 获取 LiveData，当数据库数据改变时，Fragment 会自动更新
        emotionStats = moodDao.getEmotionStats();
        weatherStats = moodDao.getWeatherStats();
    }

    // 示例：插入数据的方法 (用于测试或首页记录)
    public void insert(MoodEntry mood) {
        MoodDatabase.databaseWriteExecutor.execute(() -> {
            moodDao.insert(mood);
        });
    }
}
