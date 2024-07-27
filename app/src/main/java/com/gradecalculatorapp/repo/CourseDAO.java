package com.gradecalculatorapp.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM courses")
    void deleteAllCourses();

    @Query("SELECT * FROM courses")
    LiveData<List<Course>> getAllCourses();
}
