package com.example.moodweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Random;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DayViewHolder> {

    private final List<MoodCalendarActivity.CalendarDay> days;
    private final Random random = new Random();

    public CalendarAdapter(List<MoodCalendarActivity.CalendarDay> days) {
        this.days = days;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendar_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        MoodCalendarActivity.CalendarDay day = days.get(position);

        if (day == null) {
            // 空白格
            holder.tvDayNumber.setText("");
            holder.ivWeatherIcon.setImageDrawable(null);
            return;
        }

        holder.tvDayNumber.setText(String.valueOf(day.dayNumber));

        // 核心功能：情绪 -> 天气映射
        if (day.emotion != null) {
            int weatherResId = getWeatherResource(day.emotion);
            if (weatherResId != 0) {
                holder.ivWeatherIcon.setImageResource(weatherResId);
                holder.ivWeatherIcon.setVisibility(View.VISIBLE);
            } else {
                holder.ivWeatherIcon.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.ivWeatherIcon.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }


    private int getWeatherResource(String emotion) {
        switch (emotion) {
            case "开心":
                // 映射：晴空万里 / 温暖阳光
                return R.drawable.ic_weather_sunny;

            case "愤怒":
                // 映射：狂风暴雨 / 闪电雷鸣 -> Stormy
                return R.drawable.ic_weather_thunder;

            case "难过":
                return R.drawable.ic_weather_rain;
            case "崩溃":
                return random.nextBoolean() ? R.drawable.ic_weather_rain : R.drawable.ic_weather_fog;

            case "困倦":
                // 映射：慵懒多云 -> Cloudy
                return R.drawable.ic_weather_cloudy;

            default:
                return 0;
        }
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayNumber;
        ImageView ivWeatherIcon;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayNumber = itemView.findViewById(R.id.tvDayNumber);
            ivWeatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
        }
    }
}