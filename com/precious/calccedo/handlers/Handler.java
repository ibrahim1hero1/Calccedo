
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
 interface Handler {
            
    String calculate(String formula);
    boolean isNumber(char c);
    boolean isNumber(String quote);
    boolean initValidation(String formula);
    String optimizeFormula(String formula);

    
    
    
 }
      
    
    
  

