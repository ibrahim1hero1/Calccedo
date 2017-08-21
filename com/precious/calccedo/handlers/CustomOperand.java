/*
 *   Calccedo Library
 *   hint this code under ApacheLicense
 */



package com.precious.calccedo.handlers;

/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
 class CustomOperand {
    
     String operandName;
     double value;

    public CustomOperand(String operandName, double value) {
        this.operandName = operandName;
        this.value = value;
    }

    public String getOperandName() {
        return operandName;
    }

    public void setOperandName(String operandName) {
        this.operandName = operandName;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


       
    
    
    
    
}
