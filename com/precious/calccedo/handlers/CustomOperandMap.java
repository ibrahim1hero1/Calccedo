/*
 *   Calccedo Library
 *   hint this code under ApacheLicense
 */


package com.precious.calccedo.handlers;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */

public class CustomOperandMap {
    
    /**
     *HashMap contains all custom operands added by calcceodo like:Sin(30)=0.5 as a default operands 
     * and ready to contain any custom operand added by developer 
     * it is public static so you can view it and handle it, but you should care while dealing with it because 
     * it is contain pre dependent operands, so you should not change it's value 
     */
    public static HashMap<String, Object> customOperandMap_Constant=new HashMap<>();
  
     /**
     * list to hold first character of custom operand name to pass it to CalccedoHandler Sin >>> will hold S
     */
    public static ArrayList<Character> customOperandCharacters=new ArrayList<>(); 
    
    /**
     * list to hold custom operand name to pass it to SubFormula Handler Sin >>> will hold Sin
     */
    public static ArrayList<String>  customOperandNames=new ArrayList<>();
    
    /**
     * configure customOperandCharacters and customOperandNames to hold prefix symbols <,(
     */  
    static{
        customOperandCharacters.add('<');
        customOperandCharacters.add('(');
        customOperandCharacters.add('l');
        customOperandNames.add("log");
    }
    
    
    /**
     *Constructor of CustomOperandMap class, act as default constructor
     */
    public  CustomOperandMap(){
        
    }
    
    /**
     *this method to add custom operand that handle integer value as a fact like Sin(30)=0.5 
     * @param customOperand   operand name>>>>>> Sin
     * @param quoteNumber     argument as int >>>>>> 30
     * @param value           value that custom operand should produce  >>>>>> 0.5
     */
    public void add(String customOperand, int quoteNumber,double value){
      customOperandMap_Constant.put(customOperand+quoteNumber, new CustomOperand(customOperand+quoteNumber, value));
      integrateCustomOperand(customOperand);
    }
    
     /**
     *this method to add custom operand that handle double value as a fact like Sin(30.0)=0.5 
     * @param customOperand   operand name>>>>>> Sin
     * @param quoteNumber     argument as double >>>>>> 30.0
     * @param value           value that custom operand should produce >>>>>> 0.5
     */
    public  void add(String customOperand, double quoteNumber,double value){
         customOperandMap_Constant.put(customOperand+quoteNumber, new CustomOperand(customOperand+quoteNumber, value));
         integrateCustomOperand(customOperand);
    }
    
     /**
     *this method to add infinity fact to custom operand that handle integer value like Tan(90)= Error,Infinity
     * @param customOperand   operand name>>>>>> Tan
     * @param quoteNumber     argument number int >>>>>> 90
     * @param object          value that custom operand should produce infinity.class
     */
    public  void add(String customOperand, int quoteNumber,Object object){
         customOperandMap_Constant.put(customOperand+quoteNumber, object);
         integrateCustomOperand(customOperand);
    }
    
    /**
     *this method to add infinity fact to custom operand that handle double value Tan(90.0)= Error,Infinity
     * @param customOperand  operand name>>>>>> Tan 
     * @param quoteNumber    argument number double >>>>>> 90.0
     * @param object         value that custom operand should produce infinity.class
     */
    public  void add(String customOperand, double quoteNumber,Object object){
         customOperandMap_Constant.put(customOperand+quoteNumber, object);
         integrateCustomOperand(customOperand);
    }
    
    /**
     * this method to integrate custom operand characteristics into calccedo engine
     * @param customOperand operand name>>>>>> sin,pi
     */
    public void integrateCustomOperand(String customOperand){
        customOperandCharacters.add(customOperand.charAt(0));
        customOperandNames.add(customOperand);
    }
    
    
    /**
     *
     * @return HashMap of custom operands
     */
    public HashMap<String,Object> getcustomOperandMap_Constant(){
         return customOperandMap_Constant;
     }   

    /**
     *
     * @return This is utility class to add custom operands, it is overload four method with add syntax
     */
    @Override
    public String toString() {
        return "This is utility class to add custom operands, it is overload four method with add syntax";
    }
    
    
}
