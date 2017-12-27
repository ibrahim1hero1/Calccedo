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
    
    /**
     *boolean variable to print tracing acts or not
     */
    public static boolean deepTracing=false; 
     
     /**
     *object from CustomOperandMap to handle and hold all custom operands added by calccedo or added by developers 
     */
    CustomOperandMap customOperandMap;

    /**
     *boolean variable to avoid repeat super constructor calling of init() method 
     *just by assigned it to true 
     *
     */
    public static boolean closeGate = false;

    /**
     *Constructor of Configuration class which instantiate CustomOperandMap while calling
     */
    public Configuration() {
        this.customOperandMap = new CustomOperandMap();
    }

    /**
     *This method work as super method in calccedo, it is run once and do all configurations
     *needed in calccedo to help subclasses do the right calculation 
     */
    protected void init(){
       
       if(!closeGate){ 
        addCustomOperand("Sin",30, 0.5);
        addCustomOperand("Sin",30.0, 0.5);
        addCustomOperand("Cos",60, 0.5);
        addCustomOperand("Cos",60.0, 0.5);
        addCustomOperand("Cos",90, 0);
        addCustomOperand("Cos",90.0, 0);
        addCustomOperand("Tan",45,1);
        addCustomOperand("Tan",45.0,1);
        addCustomOperand("Tan",90,Infinity.class);
        addCustomOperand("Tan",90.0,Infinity.class);      
        closeGate=true; 
       }
      }
    
     /**
     * This method to help in tracing calccedo acts step by step if it set to true 
     * @param enabled true | false
     */
    protected void enableDeepTrace(boolean enabled){
        deepTracing=enabled;
    }
      
    /**
     *this method to add custom operand that handle integer value as a fact like Sin(30)=0.5 
     * @param customOperand   operand name>>>>>> Sin
     * @param quoteNumber     argument as int >>>>>> 30
     * @param value           value that custom operand should produce  >>>>>> 0.5
     */
    protected void addCustomOperand(String customOperand, int quoteNumber,double value){
      customOperandMap.add(customOperand, quoteNumber, value);
    }
    
    /**
     *this method to add custom operand that handle double value as a fact like Sin(30.0)=0.5 
     * @param customOperand   operand name>>>>>> Sin
     * @param quoteNumber     argument as double >>>>>> 30.0
     * @param value           value that custom operand should produce >>>>>> 0.5
     */
    protected void addCustomOperand(String customOperand, double quoteNumber,double value){
      customOperandMap.add(customOperand, quoteNumber, value);
    }
      
    /**
     *this method to add infinity fact to custom operand that handle integer value like Tan(90)= Error,Infinity
     * @param customOperand   operand name>>>>>> Tan
     * @param quoteNumber     argument number int >>>>>> 90
     * @param object          value that custom operand should produce infinity.class
     */
    protected void addCustomOperand(String customOperand, int quoteNumber, Object object){
      customOperandMap.add(customOperand, quoteNumber, object);
    }
      
    /**
     *this method to add infinity fact to custom operand that handle double value Tan(90.0)= Error,Infinity
     * @param customOperand  operand name>>>>>> Tan 
     * @param quoteNumber    argument number double >>>>>> 90.0
     * @param object         value that custom operand should produce infinity.class
     */
    protected void addCustomOperand(String customOperand, double quoteNumber, Object object){
      customOperandMap.add(customOperand, quoteNumber, object);
    }

    /**
     *
     * @return This is configuration class of calccedo which load predefined custom operands and predefined variables
     */
    @Override
    public String toString() {
        return "This is configuration class of calccedo which load predifiend custom operands and predefined variables";
    }
    
   
}

