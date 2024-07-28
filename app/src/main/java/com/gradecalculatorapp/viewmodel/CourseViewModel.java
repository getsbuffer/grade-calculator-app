package com.gradecalculatorapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gradecalculatorapp.model.Course;
import com.gradecalculatorapp.repo.CourseRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepository courseRepository;
    private LiveData<List<Course>> allCourses;
    private MutableLiveData<Map<String, Course>> courseMapLiveData;

    public CourseViewModel(Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
        allCourses = courseRepository.getAllCourses();
        courseMapLiveData = new MutableLiveData<>(new HashMap<>());
        loadCourses();
    }

    private void loadCourses() {
        allCourses.observeForever(courses -> {
            Map<String, Course> courseMap = new HashMap<>();
            for (Course course : courses) {
                courseMap.put(course.getName(), course);
            }
            courseMapLiveData.setValue(courseMap);
        });
    }

    public LiveData<Map<String, Course>> getCourses() {
        return courseMapLiveData;
    }

    public void addCourse(String courseName, Course course) {
        courseRepository.insert(course);
        // Update courseMapLiveData to reflect the changes
        Map<String, Course> courseMap = courseMapLiveData.getValue();
        if (courseMap != null) {
            courseMap.put(courseName, course);
            courseMapLiveData.setValue(courseMap);
        }
    }

    public void updateCourse(Course course) {
        courseRepository.update(course);
        // Update courseMapLiveData to reflect the changes
        Map<String, Course> courseMap = courseMapLiveData.getValue();
        if (courseMap != null) {
            courseMap.put(course.getName(), course);
            courseMapLiveData.setValue(courseMap);
        }
    }

    public void removeCourse(String courseName) {
        Map<String, Course> courseMap = courseMapLiveData.getValue();
        if (courseMap != null && courseMap.containsKey(courseName)) {
            Course course = courseMap.get(courseName);
            courseRepository.delete(course);
            courseMap.remove(courseName);
            courseMapLiveData.setValue(courseMap);
        }
    }

    public void clearAllCourses() {
        courseRepository.deleteAllCourses();
        courseMapLiveData.setValue(new HashMap<>()); // Clear the map
    }
}

