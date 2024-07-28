package com.gradecalculatorapp.repo;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.gradecalculatorapp.model.Course;

import java.util.List;

public class CourseRepository {
    private CourseDAO courseDAO;
    private LiveData<List<Course>> allCourses;

    public CourseRepository(Application application) {
        CourseDatabase database = CourseDatabase.getInstance(application);
        courseDAO = database.courseDao();
        allCourses = courseDAO.getAllCourses();
    }

    public void insert(Course course) {
        CourseDatabase.databaseWriteExecutor.execute(() -> courseDAO.insert(course));
    }

    public void update(Course course) {
        CourseDatabase.databaseWriteExecutor.execute(() -> courseDAO.update(course));
    }

    public void delete(Course course) {
        CourseDatabase.databaseWriteExecutor.execute(() -> courseDAO.delete(course));
    }

    public void deleteAllCourses() {
        CourseDatabase.databaseWriteExecutor.execute(() -> courseDAO.deleteAllCourses());
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }
}
