//简单的辅助类，用于接收 COUNT 查询的结果
package com.example.moodweather;

// 不需要任何 Room 注解，只是一个数据容器
public class MoodStats {
    public String type; // 对应 emotionLabel 或 weatherType
    public int count;   // 出现次数

    public MoodStats(String type, int count) {
        this.type = type;
        this.count = count;
    }
}
