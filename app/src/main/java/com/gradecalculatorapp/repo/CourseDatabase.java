package com.gradecalculatorapp.repo;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.gradecalculatorapp.model.Course;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Course.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class CourseDatabase extends RoomDatabase {
    private static CourseDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract CourseDAO courseDao();

    public static synchronized CourseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CourseDatabase.class, "course_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
