package com.gradecalculatorapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gradecalculatorapp.model.Course;

import java.util.HashMap;
import java.util.Map;

public class CourseViewModel extends ViewModel {

    private final MutableLiveData<Map<String, Course>> courses = new MutableLiveData<>(new HashMap<>());

    public LiveData<Map<String, Course>> getCourses() {
        return courses;
    }

    public void addCourse(String name, Course course) {
        Map<String, Course> currentCourses = courses.getValue();
        if (currentCourses != null) {
            currentCourses.put(name, course);
            courses.setValue(currentCourses);
        }
    }

    public void removeCourse(String name) {
        Map<String, Course> currentCourses = courses.getValue();
        if (currentCourses != null) {
            currentCourses.remove(name);
            courses.setValue(currentCourses);
        }
    }

    public void updateCourse(Course course) {
        Map<String, Course> currentCourses = courses.getValue();
        if (currentCourses != null) {
            currentCourses.put(course.getName(), course);
            courses.setValue(currentCourses);
        }
    }
    public void clearAllCourses() {
        courses.setValue(new HashMap<>());
    }
}
