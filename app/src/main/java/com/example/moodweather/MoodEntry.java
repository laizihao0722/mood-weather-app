package com.example.moodweather;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "mood_table")
public class MoodEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    // 使用 long 类型存储时间戳 (System.currentTimeMillis())
    public long timestamp;

    // 情绪标签
    public String emotionLabel;

    // 情绪天气
    public String weatherType;

    public MoodEntry(long timestamp, String emotionLabel, String weatherType) {
        this.timestamp = timestamp;
        this.emotionLabel = emotionLabel;
        this.weatherType = weatherType;
    }

    // Room 需要一个无参构造函数，即使不使用
    @Ignore
    public MoodEntry() {}
}
