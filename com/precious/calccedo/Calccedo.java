
/*
 *   Calccedo Library
 *   hint this code under Apache License
 *   
 *   
 */

 package com.precious.calccedo;


import com.precious.calccedo.configuration.Configuration;

/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
public class Calccedo extends Configuration{
    
    
    public Calccedo(){
        init();      
    }
    
    
   

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void enableDeepTrace(boolean enabled) {
        super.enableDeepTrace(enabled); 
    }

    @Override
    public String toString() {
        return " Calccedo Library is calculation engine library designed to help developers for building applications that need Mathematical equations,"
                + "\n  Calccedo is not just library, it's work as interperter and act as AI engines";
    }
    
    
    
   
   
    
    
    
}
