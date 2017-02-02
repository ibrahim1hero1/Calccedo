/*
 *   Calccedo Library
 *   hint this code under Apache License
 */



package com.precious.calccedo.configuration;


import com.precious.calccedo.handlers.CustomOperandMap;
import com.precious.calccedo.handlers.Infinity;

/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
  public class Configuration {
    
      public static boolean deepTracing=false; 
      CustomOperandMap constantOperand=new CustomOperandMap();
    
      public Class getCustomeOperandMap(){
          return CustomOperandMap.class;
      }
    
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
    
    public void enableDeepTrace(boolean enabled){
        deepTracing=enabled;
    }
      
    private void addCustomOperand(String customOperand, int quoteNumber,double value){
      constantOperand.add(customOperand, quoteNumber, value);
    }
    
    
    private void addCustomOperand(String customOperand, double quoteNumber,double value){
      constantOperand.add(customOperand, quoteNumber, value);
    }
      
    private void addCustomOperand(String customOperand, int quoteNumber, Object object){
      constantOperand.add(customOperand, quoteNumber, object);
    }
      
    
    private void addCustomOperand(String customOperand, double quoteNumber, Object object){
      constantOperand.add(customOperand, quoteNumber, object);
    }
      
}
