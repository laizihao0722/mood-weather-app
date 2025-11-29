//创建数据库实例的单例模式
package com.example.moodweather;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MoodEntry.class}, version = 1, exportSchema = false)
public abstract class MoodDatabase extends RoomDatabase {

    public abstract MoodDao moodDao();

    private static volatile MoodDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    // 异步执行数据库操作的线程池
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MoodDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoodDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MoodDatabase.class, "mood_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
