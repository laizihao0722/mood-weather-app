//数据访问对象
package com.example.moodweather;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoodDao {

    // 插入新记录
    @Insert
    void insert(MoodEntry mood);

    // 统计各情绪类型的出现次数 (用于饼图)
    @Query("SELECT emotionLabel as type, COUNT(emotionLabel) as count FROM mood_table GROUP BY emotionLabel")
    LiveData<List<MoodStats>> getEmotionStats();

    // 统计各情绪天气的出现次数 (用于柱状图)
    @Query("SELECT weatherType as type, COUNT(weatherType) as count FROM mood_table GROUP BY weatherType")
    LiveData<List<MoodStats>> getWeatherStats();
}
