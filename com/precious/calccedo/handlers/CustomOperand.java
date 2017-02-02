/*
 *   Calccedo Library
 *   hint this code under Apache License
 */



package com.precious.calccedo.handlers;

/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
public class CustomOperand {
    
    String operanName;
    double value;

    public CustomOperand(String operanName, double value) {
        this.operanName = operanName;
        this.value = value;
    }

    public String getOperanName() {
        return operanName;
    }

    public void setOperanName(String operanName) {
        this.operanName = operanName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


       
    
    
    
    
}
