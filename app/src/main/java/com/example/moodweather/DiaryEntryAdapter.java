package com.example.moodweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.DiaryEntryViewHolder> {

    // 存储日记数据的列表
    private List<MoodEntry> mEntries = new ArrayList<>();
    // 用于格式化时间戳
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());

    // ViewHolder 类：持有每个列表项的视图引用

    static class DiaryEntryViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDateTime;
        private final TextView tvMoodWeather;
        private final TextView tvContent;

        private DiaryEntryViewHolder(View itemView) {
            super(itemView);
            // 绑定 item_diary_entry.xml 中的控件 ID
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvMoodWeather = itemView.findViewById(R.id.tvMoodWeather);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }

    // 创建 ViewHolder (加载布局)

    @NonNull
    @Override
    public DiaryEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary_entry, parent, false);
        return new DiaryEntryViewHolder(itemView);
    }

    // 绑定数据到 ViewHolder

    @Override
    public void onBindViewHolder(@NonNull DiaryEntryViewHolder holder, int position) {
        MoodEntry current = mEntries.get(position);

        // 格式化时间戳
        String dateString = dateFormat.format(current.getTimestamp());
        holder.tvDateTime.setText(dateString);

        // 组合情绪和天气
        String moodWeather = current.getEmotionLabel() + " (" + current.getWeatherType() + ")";
        holder.tvMoodWeather.setText(moodWeather);

        // 显示日记内容，如果为空则显示提示
        String content = current.getContent().isEmpty() ? "[无文字记录]" : current.getContent();
        holder.tvContent.setText(content);
    }

    // 返回列表项数量

    @Override
    public int getItemCount() {
        return mEntries.size();
    }


     //更新数据并通知 RecyclerView 刷新
     //这个方法会在 LiveData 观察者中调用


    public void setEntries(List<MoodEntry> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }
}
