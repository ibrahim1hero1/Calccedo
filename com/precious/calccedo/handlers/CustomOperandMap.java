/*
 *   Calccedo Library
 *   hint this code under ApacheLicense
 */




package com.precious.calccedo.handlers;

import java.util.HashMap;

/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */


public class CustomOperandMap {
    
  public static HashMap<String, Object> customOperandMap_Constant;
    
    
    public  CustomOperandMap(){
        customOperandMap_Constant=new HashMap<>();
    }
    
    
    
    
    public void add(String customOperand, int quoteNumber,double value){
      customOperandMap_Constant.put(customOperand+quoteNumber, new CustomOperand(customOperand+quoteNumber, value));
    }
    
    
    public  void add(String customOperand, double quoteNumber,double value){
         customOperandMap_Constant.put(customOperand+quoteNumber, new CustomOperand(customOperand+quoteNumber, value));
    }
    
    
    public  void add(String customOperand, int quoteNumber,Object object){
         customOperandMap_Constant.put(customOperand+quoteNumber, object);
    }
    
    public  void add(String customOperand, double quoteNumber,Object object){
         customOperandMap_Constant.put(customOperand+quoteNumber, object);
    }
    
    
     public HashMap<String,Object> getcustomOperandMap_Constant(){
         return customOperandMap_Constant;
     }   
    
    
}
