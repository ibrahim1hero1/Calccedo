
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
public class Quote {
    
    
    String customOperand;
    String subformula;
    int offsetA;
    int offsetZ;

    
    
    
    public Quote(String subformula, int offsetA, int offsetZ) {
        this.subformula = subformula;
        this.offsetA=offsetA;
        this.offsetZ=offsetZ;
    }
    
    
    public Quote(String customOperand, String subformula, int offsetA, int offsetZ) {
        this.customOperand = customOperand;
        this.subformula = subformula;
        this.offsetA=offsetA;
        this.offsetZ=offsetZ;
    }
    
    
    
    
    
    
    
    
}
