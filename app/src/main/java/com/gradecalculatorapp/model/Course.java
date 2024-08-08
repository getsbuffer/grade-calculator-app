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
    private String letterGrade;
    private Map<String, GradeDetail> grades;

    public Course(String name, String letterGrade, int creditHours, double finalGrade) {
        this.name = name;
        this.creditHours = creditHours;
        this.letterGrade = letterGrade;
        this.finalGrade = finalGrade;
        this.grades = new HashMap<>();
    }

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
    public String getLetterGrade() { return letterGrade; }

    public void setLetterGrade(String letterGrade) { this.letterGrade = letterGrade; }

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

    public Map<String, GradeDetail> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, GradeDetail> grades) {
        this.grades = grades;
        updateFinalGrade();
    }

    public void addGrade(String category, double value, double weight) {
        grades.put(category, new GradeDetail(value, weight));
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
            double totalWeightedScore = 0.0;
            double totalWeight = 0.0;
            for (GradeDetail detail : grades.values()) {
                totalWeightedScore += detail.getValue() * (detail.getWeight() / 100);
                totalWeight += detail.getWeight();
            }
            finalGrade = (totalWeight == 0) ? 0 : totalWeightedScore;
        }
    }
    public String calculateLetterGrade(double grade) {
        if (grade >= 93) {
            return "A";
        } else if (grade >= 90) {
            return "A-";
        } else if (grade >= 87) {
            return "B+";
        } else if (grade >= 83) {
            return "B";
        } else if (grade >= 80) {
            return "B-";
        } else if (grade >= 77) {
            return "C+";
        } else if (grade >= 73) {
            return "C";
        } else if (grade >= 70) {
            return "C-";
        } else if (grade >= 67) {
            return "D+";
        } else if (grade >= 63) {
            return "D";
        } else if (grade >= 60) {
            return "D-";
        } else {
            return "F";
        }
    }
}
