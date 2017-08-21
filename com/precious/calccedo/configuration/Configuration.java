/*
 *   Calccedo Library
 *   hint this code under ApacheLicense
 */



package com.precious.calccedo.configuration;



import com.precious.calccedo.handlers.CustomOperandMap;
import com.precious.calccedo.handlers.Infinity;

/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
  public abstract class Configuration {
    
      public static boolean deepTracing=false; 
      CustomOperandMap constantOperand=new CustomOperandMap();
     
      public Class getCustomeOperandMap(){
          return CustomOperandMap.class;
      }
    // rename list and list2 here to make calccedo handler clear design
      protected void init(){
       
       // Calccedo initlization 
        addCustomOperand("Sin",30, 0.5);
        addCustomOperand("Sin",30.0, 0.5);
        addCustomOperand("Cos",60, 0.5);
        addCustomOperand("Cos",60.0, 0.5);
        addCustomOperand("Cos",90, 0);
        addCustomOperand("Cos",90.0, 0);
        addCustomOperand("Tan",45,1);
        addCustomOperand("Tan",45.0,1);
        addCustomOperand("Tan",90,new Infinity());
        addCustomOperand("Tan",90.0,new Infinity());
       
        
      }
    
    protected void enableDeepTrace(boolean enabled){
        deepTracing=enabled;
    }
      
    protected void addCustomOperand(String customOperand, int quoteNumber,double value){
      constantOperand.add(customOperand, quoteNumber, value);
    }
    
    
    protected void addCustomOperand(String customOperand, double quoteNumber,double value){
      constantOperand.add(customOperand, quoteNumber, value);
    }
      
    protected void addCustomOperand(String customOperand, int quoteNumber, Object object){
      constantOperand.add(customOperand, quoteNumber, object);
    }
      
    
    protected void addCustomOperand(String customOperand, double quoteNumber, Object object){
      constantOperand.add(customOperand, quoteNumber, object);
    }
      
}
