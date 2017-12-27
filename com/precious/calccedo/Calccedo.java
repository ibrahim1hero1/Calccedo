
/*
 *   Calccedo Library
 *   hint this code under ApacheLicense
 */

 package com.precious.calccedo;

import com.precious.calccedo.configuration.Configuration;

/**
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
public abstract class Calccedo extends Configuration{

    /**
     *Constructor of Calccedo which execute init() method in Configuration class while calling
     */
    public Calccedo() {
        super.init();
    }
    
    /**
     * This method to help in tracing calccedo acts step by step if it set to true 
     * @param enabled true | false
     */
    @Override
    protected void enableDeepTrace(boolean enabled) {
        super.enableDeepTrace(enabled); 
    }

    /**
     * @return Calccedo Library is calculation engine library designed to help developers for building applications that need complecated Mathematical equations, 
     *         Calccedo is not just library, it's work as interperter and act as AI engines
     */
    @Override
    public String toString() {
        return " Calccedo Library is calculation engine library designed to help developers for building applications that need complecated Mathematical equations,"
                + "\n  Calccedo is not just library, it's work as interperter and act as AI engines";
    }
     
    
}

