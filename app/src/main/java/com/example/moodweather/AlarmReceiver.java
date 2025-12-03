package com.example.moodweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 在这里可以添加逻辑来查询数据库，判断今天是否已经有记录
        // 如果没有记录，并且用户开启了目标，则发送提醒。

        // 暂时简化处理：只要目标开启，就发送提醒
        if (GoalManager.isDailyRecordGoalSet(context)) {
            NotificationHelper.sendDailyReminderNotification(context);
        }
    }
}