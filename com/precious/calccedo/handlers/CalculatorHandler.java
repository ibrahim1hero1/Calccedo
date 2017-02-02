
/*
 *   Calccedo Library
 *   hint this code under Apache License
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
public class CalculatorHandler extends Calccedo implements Handler {

    
    ArrayList<Character> list;
     ArrayList<Character> list2;
    
    public CalculatorHandler(){
        list=new ArrayList<>();      
        list.add('S');
        list.add('C');
        list.add('T');
        list.add('L');     
        list.add('<');
        list.add('(');
        
        list2=new ArrayList<>();      
        list2.add('/');
        list2.add('+');
        list2.add('-');
        list2.add('*');
        list2.add('n');
        list2.add('s');
        list2.add('g');
        
        
        enableDeepTrace(false);
    
    }

 
    
    
    
     public Quote parsePartethis(String formula) {
     
          
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
 
     
      public String obtainQuoteOperand(String digits){
          
          if(digits.equals("Sin")){
              return "Sin";
          }
          
          else if(digits.equals("Cos")){
              return "Cos";
          }
           
          if(digits.equals("Tan")){
              return "Tan";
          }
          
          if(digits.equals("Log")){
              return "Log";
          }
          else{
              return "";
          }
          
      }
      
   @Override
   public boolean isNumber(char c){
       if(c=='.'){
           return true;
       }
       try{
           Integer.parseInt(""+c);
           return true;
       }
       
       catch(Exception ex){
           
           return false;
       }
       
   }
   
   
   
  

    @Override
    public String optimizeFormula(String formula) {
     String newformula="";   
        
        for(int i=0;i<formula.length();i++){
            if(list.contains(formula.charAt(i)) && i>0){
                if(list2.contains(formula.charAt(i-1)) && i>0){
                    newformula=newformula+formula.charAt(i);
                }
                else{
                    newformula=newformula+"*"+formula.charAt(i);
                }
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
       
        return openedPartethis==0 && openedRoot==0 ? true:false;
    }
   
    
    
  
    @Override
    public String calculate(String formula) {
      
       //String result="Denmark Copenhagn Crossworkers";
        
       // validate formula
       if(!initValidation(formula)) return  "error";
       
       // optimize formula
        formula=optimizeFormula(formula);
        
        // calculate inside partethis
        String firstProcess= calculatePartethis(formula);
        if(firstProcess.equals("error")){
            return "error";
        }
        
        
        // include final formula inside partetehis to process it 
        // second peocess is the final process in calccedo, just because conatins only +,-
        
        String secondProcess= calculatePartethis("("+firstProcess+")");
        if(secondProcess.equals("error")){
            return "error";
        }
        
        return secondProcess;
}

   

         public String calculatePartethis(String formula){
             Quote quote;
             SubFormula subFormula;
             QuoteResult quoteResult;
             while(formula.contains("(")){
                 quote=parsePartethis(formula);
                 subFormula=new SubFormula(quote);
                 quoteResult=subFormula.getQuoteResult();
                 if(subFormula.getQuoteResult()==null){
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

    @Override
    public boolean isNumber(String quote) {
        
        try{
           Double.parseDouble(quote);
           return true;
       }
       
       catch(Exception ex){
            
            if(Configuration.deepTracing){
              //  System.err.println(ex+"\n just dummy exception do in behind while validating Numbers\n");
               System.out.println("\nCalccedo Info: this is just info to help developers how Calccedo Library work");
               System.out.println("Info quote "+quote+", cannot wrapped to Double, so it will complete loop until finishing operations \n"); 
            }
            return false;
       }
    }

    
    
}


  

