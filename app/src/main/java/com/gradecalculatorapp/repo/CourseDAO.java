package com.gradecalculatorapp.repo;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.gradecalculatorapp.model.Course;
import java.util.List;

@Dao
public interface CourseDAO {
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
