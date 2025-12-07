package com.example.moodweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeFragment extends Fragment {
    private HistoryViewModel historyViewModel;
    private TextView tvResult;
    private EditText etMoodEntry;//日记输入框
    private final Random random = new Random();

    // 情绪 -> 随机天气描述
    private final HashMap<String, List<String>> moodToWeather = new HashMap<>();

    // 情绪 -> 随机 Emoji 组合
    private final HashMap<String, List<String>> moodToEmoji = new HashMap<>();

    // 情绪 -> 随机建议
    private final HashMap<String, List<String>> moodToSuggestions = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 确保在 Fragment 存活期间 ViewModel 实例不变
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        initData(); // 初始化映射数据
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 初始化数据
        initData();
        //初始化 ViewModel
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        // 初始化UI
        initUI(view);

        return view;
    }

    private void initData() {
        moodToWeather.put("开心", Arrays.asList("阳光彩虹 ☀️🌈","温暖阳光☀️✨"));
        moodToWeather.put("难过", Arrays.asList("毛毛雨 🌧️💧", "乌云密布 ☁️⛈️","云雾缭绕☁️🌫️"));
        moodToWeather.put("愤怒", Arrays.asList("火山爆发 🌋🔥", "雷电交加 ⚡🌩️",  "岩浆喷射 🌋💢"));
        moodToWeather.put("困倦", Arrays.asList("午睡云朵 ☁️😴",  "咖啡雾气 ☕🌫️"));
        moodToWeather.put("崩溃", Arrays.asList("龙卷风 🌪️💨","地震摇晃 🌍📳"));

        moodToEmoji.put("开心", Arrays.asList("😊✨", "🌟🎉", "🌈💖"));
        moodToEmoji.put("难过", Arrays.asList("😢💧", "🥺🕊️", "😭☔"));
        moodToEmoji.put("愤怒", Arrays.asList("😠⚡", "👹🔥", "💢🗿"));
        moodToEmoji.put("困倦", Arrays.asList("🥱😴", "🌙💤", "🛌🐑"));
        moodToEmoji.put("崩溃", Arrays.asList("🤯💥", "😵⚠️", "🆘🚨"));

        moodToSuggestions.put("开心", Arrays.asList("去公园散步 🌳", "和朋友分享快乐 🤗", "吃块巧克力 🍫", "听欢快的歌 🎵"));
        moodToSuggestions.put("难过", Arrays.asList("喝杯热茶 🍵", "写日记释放情绪 📝", "听舒缓音乐 🎶", "抱抱毛绒玩具 🧸"));
        moodToSuggestions.put("愤怒", Arrays.asList("深呼吸 10 次 🫁", "去跑步发泄 🏃", "撕废纸发泄 📄", "冷静 5 分钟 ⏳"));
        moodToSuggestions.put("困倦", Arrays.asList("小睡 20 分钟 😴", "喝杯咖啡 ☕", "听轻音乐 🎵", "拉伸身体 🧘"));
        moodToSuggestions.put("崩溃", Arrays.asList("找朋友倾诉 🗣️", "写下来再撕掉 📝", "深呼吸 20 次 🫁", "洗个热水澡 🚿"));
    }

    private void initUI(View view) {
        tvResult = view.findViewById(R.id.tvResult);
        etMoodEntry = view.findViewById(R.id.etMoodEntry);

        // 🎯 情绪按钮点击事件
        Button btnHappy = view.findViewById(R.id.btnHappy);
        Button btnSad = view.findViewById(R.id.btnSad);
        Button btnAngry = view.findViewById(R.id.btnAngry);
        Button btnTired = view.findViewById(R.id.btnTired);
        Button btnStress = view.findViewById(R.id.btnStress);

        btnHappy.setOnClickListener(v -> showMoodResult("开心"));
        btnSad.setOnClickListener(v -> showMoodResult("难过"));
        btnAngry.setOnClickListener(v -> showMoodResult("愤怒"));
        btnTired.setOnClickListener(v -> showMoodResult("困倦"));
        btnStress.setOnClickListener(v -> showMoodResult("崩溃"));
    }

    private void showMoodResult(String mood) {
        List<String> weathers = moodToWeather.get(mood);
        List<String> emojis = moodToEmoji.get(mood);
        List<String> suggestions = moodToSuggestions.get(mood);

        // 随机选择
        String randomWeather = weathers.get(random.nextInt(weathers.size()));
        String randomEmoji = emojis.get(random.nextInt(emojis.size()));
        String randomSuggestion = suggestions.get(random.nextInt(suggestions.size()));

        //日记内容
        String diaryContent = etMoodEntry.getText().toString().trim();
        // 用户无输入默认空字符串
        if (diaryContent.isEmpty()) {
            diaryContent = "";
        }

        String weatherLabelForDB;
        switch (mood) {
            case "开心":
                weatherLabelForDB = "Sunny";
                break;
            case "难过":
                weatherLabelForDB = "Rainy";
                break;
            case "愤怒":
                weatherLabelForDB = "Stormy";
                break;
            case "困倦":
                weatherLabelForDB = "Cloudy";
                break;
            case "崩溃":
                weatherLabelForDB = "Typhoon";
                break;
            default:
                weatherLabelForDB = "Neutral";
                break;
        }

        // 组合结果
        String result = randomEmoji + " " + randomWeather + "！\n\n" +
                "今日幸运建议：" + randomSuggestion + "\n\n" +
                "🌤️ 你的情绪天气已生成！";

        tvResult.setText(result);

        // 保存到历史记录
        recordMood(mood, weatherLabelForDB);
        // 情绪 Nudge 检查
        checkAndSendNudge(mood, randomSuggestion);
    }

    private void checkAndSendNudge(String currentMood, String suggestion) {
        String avoidMood = GoalManager.getAvoidMoodGoal(getContext());

        // 如果当前记录的情绪是用户希望避免的情绪
        if (currentMood.equals(avoidMood) && !avoidMood.equals(GoalManager.NO_AVOID_MOOD)) {
            // 通过通知提供一个调整建议
            NotificationHelper.sendNudgeNotification(getContext(), suggestion);
        }
    }

    private void recordMood(String mood, String weatherType) {
        // 1. 获取当前时间戳
        long timestamp = System.currentTimeMillis();

        // 2. 创建 MoodEntry 对象
        MoodEntry newEntry = new MoodEntry(timestamp, mood, weatherType);

        // 3. 调用 ViewModel 的 insert 方法将数据异步插入数据库
        historyViewModel.insert(newEntry);

        // 4.清空输入框以便下次记录
        etMoodEntry.setText("");

        // 5. 用户反馈
        Toast.makeText(getContext(), "心情记录成功: " + mood + " / " + weatherType, Toast.LENGTH_SHORT).show();
    }
}
