/*
 *   Calccedo Library
 *   hint this code under ApacheLicense
 */

package com.precious.calccedo.handlers;

import com.precious.calccedo.configuration.Configuration;

/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
 class CustomOperandException extends RuntimeException{

    String breakPoint;
    public CustomOperandException(String breakPoint){
        this.breakPoint=breakPoint;
    }
    
    @Override
    public String toString() {
         if(Configuration.deepTracing){
             System.out.println("\nCalccedo Info:");
             System.out.println("Computer calculation different than simple calculator just because,");
             System.out.println("Computer RAM size bigger than simple calculator, so you can find some equations should return infinity result, but in computer return value");
             System.out.println("don't worry, Calccedo Library help you to custome any equation that has constant value");
             System.out.println("please read Calccedo documentation or contact me on www.github.com/ibrahim1hero1/calccedo \n");
         }

        System.err.println("Exception:");    
        return "Calccedo Library Detect "+breakPoint+" defined as infinty custom operand";
    }
    
    
    
    
}
