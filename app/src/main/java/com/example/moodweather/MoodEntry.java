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

    // 新增：日记内容字段 (用于存储用户输入的文字)
    public String content;

    // 修改后：新增 content 参数
    public MoodEntry(long timestamp, String emotionLabel, String weatherType, String content) {
        this.timestamp = timestamp;
        this.emotionLabel = emotionLabel;
        this.weatherType = weatherType;
        this.content = content; // 保存日记内容
    }

    // 原始构造函数，现在使用 @Ignore 避免与上面的新构造函数冲突
    @Ignore
    public MoodEntry(long timestamp, String emotionLabel, String weatherType) {
        this.timestamp = timestamp;
        this.emotionLabel = emotionLabel;
        this.weatherType = weatherType;
        this.content = ""; // 默认设置为空字符串
    }

    // Room 需要一个无参构造函数，即使不使用
    @Ignore
    public MoodEntry() {}

    // 为字段添加 Getter 方法，方便在应用的其他地方获取数据
    public int getId() { return id; }
    public long getTimestamp() { return timestamp; }
    public String getEmotionLabel() { return emotionLabel; }
    public String getWeatherType() { return weatherType; }
    public String getContent() { return content; } // 新增 Getter
}
