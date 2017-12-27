
/*
 *   Calccedo Library
 *   hint this code under ApacheLicense
 */

package com.precious.calccedo.handlers;

import com.precious.calccedo.Calccedo;
import com.precious.calccedo.configuration.Configuration;
import java.util.ArrayList;


/**
 * 
 * @author Ibrahim Abdsaid Hanna 
 *         ibrahim.seniore@gmail.com
 */
 public class CalccedoHandler extends Calccedo implements Handler {

    
   private ArrayList<Character> list;
   private ArrayList<Character> list2;
    
    /**
     *Constructor of CalccedoHandler class which add postfix and prefix symbols while calling
     */
    public CalccedoHandler(){
        list=new ArrayList<>();      
        list.add('S');
        list.add('C');
        list.add('T');
        list.add('L');     
        list.add('<');
        list.add('(');

        
        list2=new ArrayList<>();      
        list2.add(')');
        list2.add('>');
        list2.add('.');
        list2.add('0');
        list2.add('1');
        list2.add('2');
        list2.add('3');
        list2.add('4');
        list2.add('5');
        list2.add('6');
        list2.add('7');
        list2.add('8');
        list2.add('9');
    
    }

 
  
     private Quote parsePartethis(String formula) {

           int offsetA=0;
           int offsetZ=0;
        
           
           for(int i=formula.length()-1;i>=0;i--){
               
                if(formula.charAt(i)=='('){
                         offsetA=i;
                    for(int j=i;j<formula.length();j++){
                         if(formula.charAt(j)==')'){
                          offsetZ=j;
                          i=-1;
                          break;
                        }                 
                }                    
           }     
         
           }
           
           if(offsetA==0){
              return new Quote(formula.substring(offsetA, offsetZ+1), offsetA, offsetZ+1);
           }
           else{
               try{
               if(formula.substring(offsetA-3, offsetA).equals("Sin")){
                    return new Quote("Sin", formula.substring(offsetA, offsetZ+1),offsetA, offsetZ+1);
               }
               else if(formula.substring(offsetA-3, offsetA).equals("Cos")){
                    return new Quote("Cos",  formula.substring(offsetA, offsetZ+1), offsetA, offsetZ+1);
               }
               else if(formula.substring(offsetA-3, offsetA).equals("Tan")){
                   return new Quote("Tan",  formula.substring(offsetA, offsetZ+1), offsetA, offsetZ+1);
               }
              
                else if(formula.substring(offsetA-3, offsetA).equals("Log")){
                   return new Quote("Log",  formula.substring(offsetA, offsetZ+1), offsetA, offsetZ+1);
               }
                else{
                    return new Quote(formula.substring(offsetA, offsetZ+1), offsetA, offsetZ+1);
                }
                   }
               catch(Exception ex){
                   return new Quote(formula.substring(offsetA, offsetZ+1), offsetA, offsetZ+1);
               }
              
           }
          
           
       
    }
 
    /**
     * method to check this charcter is number or not
     * @param c  any charcter  
     * @return boolean if this number or not
     */
    @Override
   public boolean isNumber(char c){
       if(c=='.'){
           return true;
       }
       try{
           Integer.parseInt(""+c);
           return true;
       }
       
       catch(NumberFormatException ex){
           return false;
       }
       
   }
   
    /**
     * method to optimize formula before before passing to calculate(String formula) method 
     * like (5Sin(30)<9>) >>>>>> (5*Sin(30)*<9>)
     * @param formula String of formula
     * @return optimization String of formula
     */
    @Override
    public String optimizeFormula(String formula) {
     String newformula="";   
        
     
        for(int i=0;i<formula.length();i++){
            if(list.contains(formula.charAt(i)) && i>0){
                 if(list2.contains(formula.charAt(i-1)) && i>0){
                     if(formula.charAt(i)=='<'){
                      newformula=newformula+"*"+formula.charAt(i)+"(";
                    }
                     else{
                    newformula=newformula+"*"+formula.charAt(i);
                     }
                }
                else if(formula.charAt(i)=='<'){
                    newformula=newformula+formula.charAt(i)+"(";
                }
                  else if(isNumber(formula.charAt(i-1))){
                    newformula=newformula+"*"+formula.charAt(i);
                }
                else{
                     newformula=newformula+formula.charAt(i);
                }
            }
            
            
            
            else if(formula.charAt(i)=='<'){
                newformula=newformula+formula.charAt(i)+"(";
            }
             else if(formula.charAt(i)=='>'){
                newformula=newformula+")"+formula.charAt(i);
            }
            else if(isNumber(formula.charAt(i))){
                if(i>0&&(formula.charAt(i-1)==')'||formula.charAt(i-1)=='>')){
                     newformula=newformula+"*"+formula.charAt(i);
                }
                else{
                  newformula=newformula+formula.charAt(i);
                }
            }
            else{
                newformula=newformula+formula.charAt(i);
            }
            
        }
        
        
         if(Configuration.deepTracing)
        System.out.println("optinmization is >>>>>>>>>>>"+newformula);
      

     return newformula;
    }

    /**
     * method to validate formula before passing to calculate(String formula) method
     * @param formula String of formula 
     * @return boolean if this formula valid for calculation
     */
    @Override
    public boolean initValidation(String formula) {
       
        if(!formula.contains("(")&&!formula.contains("<")&&!formula.contains(">")&&!formula.contains(")")){
            return true;
        }
        
         int openedPartethis=0;      
         int openedRoot=0;
        
       
         for(int i=0;i<formula.length();i++){
          
             if(formula.charAt(i)=='('){
           
                 openedPartethis++;
             }
             
               if(formula.charAt(i)==')'){
                 openedPartethis--;
             }
               if(openedPartethis<0){
                   return false;  
               }
                if(formula.charAt(i)=='<'){
                 openedRoot++;
             }
                
                if(formula.charAt(i)=='>'){
                 openedRoot--;
             }
                 if(openedRoot<0){
                   return false;  
               }
         }
       
        return openedPartethis==0 && openedRoot==0 ;
    }
   
    /**
     * backbone method in calccedo to calculate any standard formula 
     * @param formula String text of formula like: (5Sin(30)+10*20<9*9*9*9*>)/(10*20) = 81.0125
     * @return result as a String 
     */
    @Override
    public String calculate(String formula) {
            
       // validate formula
       if(!initValidation(formula)) return  "Error";
       
       // optimize formula
        formula=optimizeFormula(formula);
        
        // calculate inside partethis
        String firstProcess= calculatePartethis(formula);
        if(firstProcess.equals("error")){
            return "Error";
        }
        
        
        // include final formula inside partetehis to process it 
        // second peocess is the final process in calccedo, just because conatins only +,-
        
        String secondProcess= calculatePartethis("("+firstProcess+")");
        if(secondProcess.equals("error")){
            return "Error";
        }
        
        return secondProcess;
}

   

         private String calculatePartethis(String formula){
             Quote quote;
             SubFormula subFormula;
             QuoteResult quoteResult;
             while(formula.contains("(")){
                 quote=parsePartethis(formula);
                 subFormula=new SubFormula(quote);
                 quoteResult=subFormula.getQuoteResult();
                 if(quoteResult==null){
                     return "error";
                 }
                 else{
                     formula=formula.substring(0,quoteResult.offsetA)+quoteResult.result+formula.substring(quoteResult.offsetZ,formula.length());
                 }
                        
             }
              if(Configuration.deepTracing)
             System.out.println("formula after parsing partethis"+formula);
            return formula;
         }

    /**
     * method to check string inside quote is number or not
     * @param quote String of inside quote like: (25.0) or (5*5+5)
     * @return boolean the string inside quote is Number
     */
    @Override
    public boolean isNumber(String quote) { 
        try{
           Double.parseDouble(quote);
           return true;
       }
       catch(NumberFormatException ex){
            
            if(Configuration.deepTracing){
               System.out.println("\nCalccedo Info: this is just info to help developers how Calccedo Library work");
               System.out.println("Info quote "+quote+", cannot wrapped to Double, so it will complete loop until finishing operations \n"); 
            }
            return false;
       }
    }

     /**
     * @return This is the backbone handler class of calccedo, it recieves formula and handle all cycles to produce result
     *         
     */
    @Override
    public String toString() {
        return "This is the backbone handler class of calccedo, it recieves formula and handle all cycles to produce result";
    }

     
}


  


  

