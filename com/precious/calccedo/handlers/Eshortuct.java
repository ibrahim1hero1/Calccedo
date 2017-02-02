
/*
 *   Calccedo Library
 *   hint this code under Apache License
 */


package com.precious.calccedo.handlers;




/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
public class Eshortuct extends CalculatorHandler{
    
     String leftTemp;
     char operandType;
     int leftOperandIndex;
     int rightOperandIndex;
   
        public Eshortuct(){
            
        }
     
    public Eshortuct(String leftTemp,char operandType,int leftOperandIndex,int rightOperandIndex){
        this.leftTemp=leftTemp;
        this.operandType=operandType;
        this.leftOperandIndex=leftOperandIndex;
        this.rightOperandIndex=rightOperandIndex;
    }

 
    // process E shortuct to not conflict with - and + operand     
    public Eshortuct process(String subformula,int Eindex){
        
         String leftTemp="E";
         char operandType = 0;
         int leftOperandIndex = 0;
         int rightOperandIndex = 0;
        
         boolean ignore=false;
         
         
         
           for(int i=Eindex;i>=0;i--){
                if(!isNumber(subformula.charAt(i))){
                   if(isMinus(subformula.charAt(i))&&ignore==false){
                       leftOperandIndex=i;
                       leftTemp=subformula.charAt(i)+leftTemp;
                       break;
                   }
                  else if(isPlus(subformula.charAt(i))){
                      leftOperandIndex=i;
                      break;
                   }
                   
               }
               else{
                   leftTemp=subformula.charAt(i)+leftTemp;
               }
           }
           
           
           
           for(int j=Eindex;j<subformula.length();j++){
               
                if(!isNumber(subformula.charAt(j))){
                   if(isMinus(subformula.charAt(j))&&Eindex+1=='-'&&ignore==false){
                       leftTemp=leftTemp+'-';
                       ignore=true;
                   }
                  else if(isPlus(subformula.charAt(j))){
                      operandType='+';
                      rightOperandIndex=j;
                      break;
                   }
                  else if(ignore==true&&(isMinus(subformula.charAt(j))||isPlus(subformula.charAt(j)))){
                       operandType=subformula.charAt(j);
                       rightOperandIndex=j;
                       break;
                  }
                 
                 else if(isMinus(subformula.charAt(j))||isPlus(subformula.charAt(j))){
                       operandType=subformula.charAt(j);
                       rightOperandIndex=j;
                       break;
                  }
                 
               }
               else{
                   leftTemp=leftTemp+subformula.charAt(j);
               }
           }
         
        return new Eshortuct(leftTemp, operandType,leftOperandIndex,rightOperandIndex);
    }
    
    
    
    public boolean isMinus(char c){
        return c=='-' ? true:false;
    }
    
    
    public boolean isPlus(char c){
        return c=='+' ? true:false;
    }
    

    
}
 
