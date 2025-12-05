//数据库查询
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

    // 新增：查询某一日期范围内最新的情绪记录（用作日历上的主要情绪）
    // :startTime 和 :endTime 应该表示某一天的开始和结束时间戳
    @Query("SELECT emotionLabel FROM mood_table " +
            "WHERE timestamp BETWEEN :startTime AND :endTime " +
            "ORDER BY timestamp DESC " +  // 按时间戳倒序排列（最新的在最前面）
            "LIMIT 1")                     // 只取一条记录
    String getLatestEmotionForDay(long startTime, long endTime);

    // 统计今天各情绪类型的出现次数 (用于饼图)
    @Query("SELECT emotionLabel as type, COUNT(emotionLabel) as count FROM mood_table WHERE timestamp >= :sinceTime GROUP BY emotionLabel")
    LiveData<List<MoodStats>> getEmotionStatsSince(long sinceTime);

    // 统计今天各情绪天气的出现次数 (用于柱状图)
    @Query("SELECT weatherType as type, COUNT(weatherType) as count FROM mood_table WHERE timestamp >= :sinceTime GROUP BY weatherType")
    LiveData<List<MoodStats>> getWeatherStatsSince(long sinceTime);

    // 统计各情绪类型的出现次数 (用于饼图)(月报/周报通用接口)
    @Query("SELECT emotionLabel as type, COUNT(emotionLabel) as count FROM mood_table WHERE timestamp BETWEEN :startTime AND :endTime GROUP BY emotionLabel")
    LiveData<List<MoodStats>> getEmotionStatsBetween(long startTime, long endTime);

    // 统计各情绪天气的出现次数 (用于柱状图)(月报/周报通用接口)
    @Query("SELECT weatherType as type, COUNT(weatherType) as count FROM mood_table WHERE timestamp BETWEEN :startTime AND :endTime GROUP BY weatherType")
    LiveData<List<MoodStats>> getWeatherStatsBetween(long startTime, long endTime);
}
