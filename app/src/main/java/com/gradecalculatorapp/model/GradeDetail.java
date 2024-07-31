package com.gradecalculatorapp.model;

import java.io.Serializable;

public class GradeDetail implements Serializable {
    private double value;
    private double weight;

    public GradeDetail(double value, double weight) {
        this.value = value;
        this.weight = weight;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
