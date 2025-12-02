package com.example.moodweather;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationHelper {
    private static final String CHANNEL_ID = "MoodWeatherChannel";
    private static final String CHANNEL_NAME = "情绪气象提醒";
    private static final int NOTIFICATION_ID_DAILY = 100;
    private static final int NOTIFICATION_ID_NUDGE = 101;

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("用于每日记录提醒和情绪调整提示");
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    // 发送每日记录提醒通知
    public static void sendDailyReminderNotification(Context context) {
        if (!GoalManager.isDailyRecordGoalSet(context)) return;

        NotificationManager manager = context.getSystemService(NotificationManager.class);
        if (manager == null) return;

        Intent intent = new Intent(context, MainActivity.class);
        // PendingIntent 需要设置 FLAG_IMMUTABLE
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_ID_DAILY,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0)
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("🌤️ 情绪气象提醒")
                .setContentText("今天还没记录你的情绪哦！快来查阅你的情绪天气吧。")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        manager.notify(NOTIFICATION_ID_DAILY, builder.build());
    }

    /**
     * 发送情绪 Nudge 调整通知
     */
    public static void sendNudgeNotification(Context context, String suggestion) {
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        if (manager == null) return;

        String nudgeMessage = String.format("注意到您记录了目标情绪。\n建议：%s", suggestion);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("情绪气象站：给你的调整建议")
                .setContentText(nudgeMessage)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(nudgeMessage))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        manager.notify(NOTIFICATION_ID_NUDGE, builder.build());
    }
}