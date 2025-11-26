package com.example.moodweather;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private Random random = new Random();

    // 🌟 情绪 -> 随机天气描述
    private HashMap<String, List<String>> moodToWeather = new HashMap<>() {{
        put("好开心", Arrays.asList(
                "阳光彩虹 ☀️🌈", "棉花糖云朵 ☁️🍬", "星星烟花 ✨🎆", "彩虹滑梯 🌈🛝"
        ));
        put("好难过", Arrays.asList(
                "毛毛雨 🌧️💧", "乌云密布 ☁️⛈️", "雪花飘落 ❄️🌨️", "微风安慰 🍃💭"
        ));
        put("愤怒", Arrays.asList(
                "火山爆发 🌋🔥", "雷电交加 ⚡🌩️", "龙卷风 🌪️💨", "岩浆喷射 🌋💢"
        ));
        put("困倦", Arrays.asList(
                "午睡云朵 ☁️😴", "月光摇篮 🌙🛏️", "暖炉时光 🔥🪑", "咖啡雾气 ☕🌫️"
        ));
        put("崩溃", Arrays.asList(
                "暴风雨 ⛈️💥", "地震摇晃 🌍📳", "海啸来袭 🌊💦", "陨石坠落 ☄️💥"
        ));
    }};

    // 🌟 情绪 -> 随机 Emoji 组合
    private HashMap<String, List<String>> moodToEmoji = new HashMap<>() {{
        put("开心", Arrays.asList("😊✨", "🌟🎉", "🌈💖"));
        put("难过", Arrays.asList("😢💧", "🥺🕊️", "😭☔"));
        put("愤怒", Arrays.asList("😠⚡", "👹🔥", "💢🗿"));
        put("困倦", Arrays.asList("🥱😴", "🌙💤", "🛌🐑"));
        put("崩溃", Arrays.asList("🤯💥", "😵⚠️", "🆘🚨"));
    }};

    // 🌟 情绪 -> 随机建议
    private HashMap<String, List<String>> moodToSuggestions = new HashMap<>() {{
        put("开心", Arrays.asList(
                "去公园散步 🌳", "和朋友分享快乐 🤗", "吃块巧克力 🍫", "听欢快的歌 🎵"
        ));
        put("难过", Arrays.asList(
                "喝杯热茶 🍵", "写日记释放情绪 📝", "听舒缓音乐 🎶", "抱抱毛绒玩具 🧸"
        ));
        put("愤怒", Arrays.asList(
                "深呼吸 10 次 🫁", "去跑步发泄 🏃", "撕废纸发泄 📄", "冷静 5 分钟 ⏳"
        ));
        put("困倦", Arrays.asList(
                "小睡 20 分钟 😴", "喝杯咖啡 ☕", "听轻音乐 🎵", "拉伸身体 🧘"
        ));
        put("崩溃", Arrays.asList(
                "找朋友倾诉 🗣️", "写下来再撕掉 📝", "深呼吸 20 次 🫁", "洗个热水澡 🚿"
        ));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        // 🎯 情绪按钮点击事件
        Button btnHappy = findViewById(R.id.btnHappy);
        Button btnSad = findViewById(R.id.btnSad);
        Button btnAngry = findViewById(R.id.btnAngry);
        Button btnTired = findViewById(R.id.btnTired);
        Button btnStress = findViewById(R.id.btnStress);

        btnHappy.setOnClickListener(v -> showMoodResult("开心"));
        btnSad.setOnClickListener(v -> showMoodResult("难过"));
        btnAngry.setOnClickListener(v -> showMoodResult("愤怒"));
        btnTired.setOnClickListener(v -> showMoodResult("困倦"));
        btnStress.setOnClickListener(v -> showMoodResult("崩溃"));
    }

    // 🌟 核心逻辑：根据情绪生成随机结果
    private void showMoodResult(String mood) {
        List<String> weathers = moodToWeather.get(mood);
        List<String> emojis = moodToEmoji.get(mood);
        List<String> suggestions = moodToSuggestions.get(mood);

        // 🎲 随机选择
        String randomWeather = weathers.get(random.nextInt(weathers.size()));
        String randomEmoji = emojis.get(random.nextInt(emojis.size()));
        String randomSuggestion = suggestions.get(random.nextInt(suggestions.size()));

        // 📝 组合结果
        String result = randomEmoji + " " + randomWeather + "！\n\n" +
                "今日幸运建议：" + randomSuggestion + "\n\n" +
                "🌤️ 你的情绪天气已生成！";

        tvResult.setText(result);
    }
}