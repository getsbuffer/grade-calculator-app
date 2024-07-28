package com.gradecalculatorapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gradecalculatorapp.repo.Converters;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "courses")
@TypeConverters(Converters.class)
public class Course implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int creditHours;
    private double finalGrade;
    private Map<String, Double> grades;

    public Course(String name, int creditHours, double finalGrade) {
        this.name = name;
        this.creditHours = creditHours;
        this.finalGrade = finalGrade;
        this.grades = new HashMap<>();
    }

    // Getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public Map<String, Double> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, Double> grades) {
        this.grades = grades;
        updateFinalGrade();
    }

    public void addGrade(String category, double grade) {
        grades.put(category, grade);
        updateFinalGrade();
    }

    public void deleteGrade(String category) {
        grades.remove(category);
        updateFinalGrade();
    }

    private void updateFinalGrade() {
        if (grades.isEmpty()) {
            finalGrade = 0.0;
        } else {
            double total = 0.0;
            for (double grade : grades.values()) {
                total += grade;
            }
            finalGrade = total / grades.size();
        }
    }
}

