package com.gradecalculatorapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Course implements Serializable {
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

    // Getters and Setters

    public String getName() {
        return name;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public Map<String, Double> getGrades() {
        return grades;
    }

    public void deleteGrade(String categoryName) {
        grades.remove(categoryName);
    }
}
