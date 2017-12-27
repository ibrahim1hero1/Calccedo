
/*
 *   Calccedo Library
 *   hint this code under ApacheLicense
 */


package com.precious.calccedo.handlers;


import com.precious.calccedo.configuration.Configuration;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */
 class SubFormula extends CalccedoHandler{
    
    Quote quote;   
    ArrayList<Character> list;
    ArrayList<Character> list2;
    ArrayList<String> list3;
    HashMap<String, Object> customOperandMap_Constant=CustomOperandMap.customOperandMap_Constant;  
    String subformula;
    
    
    
    public SubFormula(Quote quote){
        
        this.quote=quote;       
        list=new ArrayList<>();
        list2=new ArrayList<>();
        list3=new ArrayList<>();
        
        subformula=quote.subformula;  
        
        list.add('/');
        list.add('%');
        list.add('*');
        list.add('+');
        list.add('-');
        list.add('^');
        list.add('(');
        list.add(')');
        
  
        list2.add('+');
        list2.add('-');
        
        list3.add("Sin");
        list3.add("Cos");
        list3.add("Tan");
        list3.add("Log");
       
        
      
        
    }

  
    
    public QuoteResult getQuoteResult(){
      
   

       if (validational(subformula)==null) return null;   
      
      // detect standard operads (<>), if yes processing it      
       if(subformula.contains("<")){             
          subformula=processSqrt(subformula);
          if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
      System.out.println("<> Subformula "+subformula);
     // detect standard operads (^), if yes processing it      
       if(subformula.contains("^")){         
         subformula=processPriority(subformula,'^');
         if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
        System.out.println("^ Subformula "+subformula);
        // detect Reminder operads, if yes processing it     
       if(subformula.contains("%")){
          subformula=processPriority(subformula,'%'); 
          if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
       System.out.println("% Subformula "+subformula);
       
      // detect divison operads, if yes processing it     
       if(subformula.contains("/")){
           subformula=processPriority(subformula,'/');
           if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
       System.out.println("/ Subformula "+subformula);
      // detect multiplication operads, if yes processing it      
       if(subformula.contains("*")){
           subformula=processPriority(subformula,'*');
           if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
        System.out.println("* Subformula "+subformula);
       
          // detect standard operads (+,-), if yes processing it      
       if(subformula.contains("+")||subformula.contains("-")){
          subformula=processStandard(subformula,list2);
          if(subformula.equals("error")) return null;
       }
        if(Configuration.deepTracing)
      System.out.println("+- Subformula "+subformula);
       
      // final result of inside quote
     String finalQuoteNumber=subformula.replace("(", "");
            finalQuoteNumber=finalQuoteNumber.replace(")", "");
      
       double result=0;
            
      // now process custom operand without quotes
      if(list3.contains(quote.customOperand)){
          try{
          result= processCustomOperand(quote.customOperand,finalQuoteNumber);
          return  new QuoteResult(quote.offsetA-3, quote.offsetZ,result);
          }
          catch(CustomOperandException ex){
              System.err.println(ex);
              return null;
          }
      }
      else{
           try{
          result=Double.parseDouble(finalQuoteNumber);
           }
           catch(Exception exp){
              return null;
           }
      }
       if(Configuration.deepTracing)
        System.out.println("Result >>>>>>>>>>>>>>>>>>>>>>>>>>>>"+result);
      
      return  new QuoteResult(quote.offsetA, quote.offsetZ,result);
    }
    
    
    
   
    public Object validational(String subformula){
         
           if(subformula.charAt(1)=='/'|| subformula.charAt(1)=='*'||subformula.charAt(1)=='^'){
             return null;      // not legal to attach operator after open partethis eg (/121*3) 
            }
    
           if(list.contains(subformula.charAt(subformula.length()-2))){
               return null;      // not legal to attach operator before closing partethis eg (121*3/) 
     }
           return "okay"; // just dummy output
    }


     
   
    
    public String processPriority(String subformula,char operand){
     if(isNumber(uncoverQuote(subformula))){
             return subformula;
         }
     
     subformula=optimizeFormula(subformula);
         
      String leftTemp="";
      String rightTemp="";
      double result=0;
      String infinityTester="";
      int QLPointer=0;
      int QRPointer=0;
      Eshortuct eshortuct=new Eshortuct();

          
           
               
           for(int i=0;i<subformula.length();i++){
               
               // detect operand, now let's stop on it's index and take left and right numbers
               if(subformula.charAt(i)==operand){
                 
                   
         // every iterate check if inside quote number to return it without iterate
                if(isNumber(uncoverQuote(subformula))){
                    return subformula;
                }
               
                   //obtain left number
                   for(int j=i-1;j>=0;j--){
                        
                       // detect E sympol which mean e^10
                        if(subformula.charAt(j)=='E'){
                            eshortuct=eshortuct.process(subformula, j);
                            leftTemp=eshortuct.leftTemp;
                            operand=eshortuct.operandType;
                            i=eshortuct.rightOperandIndex;   
                            QLPointer=eshortuct.leftOperandIndex;
                         break;
                        }
                       
                       if(list.contains(subformula.charAt(j))){
                          if(subformula.charAt(j)=='-'&&operand!='^'){
                               if(j>0&&subformula.charAt(j-1)=='E'){
                           continue;
                           }
                            leftTemp=subformula.charAt(j)+leftTemp; // append operand to left number
                            QLPointer=j-1;
                            break;
                          }
                          else{
                           QLPointer=j;  // pointer to last number
                           break;    
                          }
                       }
                       else{
                           
                           leftTemp=subformula.charAt(j)+leftTemp;
                           
                           
                       }
                   }
                  
                   //obtain right number
                   for(int jj=i+1;jj<subformula.length();jj++){
                       
                         // detect E sympol which mean e^10
                        if(subformula.charAt(jj)=='E'){
                            eshortuct=eshortuct.process(subformula, jj);
                            rightTemp=eshortuct.leftTemp;
                            QRPointer=eshortuct.rightOperandIndex;
                         break;
                        }
                       
                       else if(list.contains(subformula.charAt(jj))&& list.contains(subformula.charAt(jj-1))){
                            rightTemp=rightTemp+subformula.charAt(jj);
                            QRPointer=jj+1; // stop pointer after right number to check there is another operand                                        
                          
                        }
                        else if(list.contains(subformula.charAt(jj))){
                              if(subformula.charAt(jj)=='-'&&operand!='^'){
                               if(subformula.charAt(jj-1)=='E'){
                           continue;
                           }
                              }
                            QRPointer=jj;
                            break;
                       }
                       else{
                           rightTemp=rightTemp+subformula.charAt(jj);
                          
                       }
                   }
           
              
                   try{
                   double number1=Double.parseDouble(leftTemp);
                   double number2=Double.parseDouble(rightTemp);     
                   
                    switch (operand) {
                        case '^':
                            result=Math.pow(number1,number2);
                            break;
                        case '%':
                            result=number1%number2;
                            break;
                        case '/':
                            result=number1/number2;
                            infinityTester=result+"";
                            if(infinityTester.equals("Infinity")||infinityTester.equals("-Infinity")||infinityTester.equals("NaN")){
                                return "error";
                            }       break;
                        case '*':
                            result=number1*number2;
                            break;
                        default:
                            break;
                    }
                   
                  
                   }
                   catch(Exception ex){
                       System.err.println("Priority Exception"+ex);
                       return "error";
                       
                   }
                   if(Configuration.deepTracing){
                   System.out.println("Operand "+operand+"    "+result);
                   System.out.println("QLPointer   "+QLPointer);
                   System.out.println("QRPointer   "+QRPointer); 
                   }
                 
                      if(QLPointer==0 && QRPointer==0){
                              return ("("+result+")");
                          } 
                          else if(subformula.charAt(QLPointer)!='('){
                                subformula=subformula.substring(0,QLPointer+1)+result+subformula.substring(QRPointer, subformula.length());
                                subformula=optimizeFormula(subformula);
                                i=0;
                            }
                           else if(subformula.charAt(QLPointer)=='('&&QRPointer>0 ){
                                subformula="("+result+subformula.substring(QRPointer, subformula.length());
                                i=0; 
                            }
                           else if(!subformula.contains("/")&&!subformula.contains("*")&&!subformula.contains("+")&&!subformula.contains("-")&&!subformula.contains("^")){
                                 if(Configuration.deepTracing)
                             
                                    return subformula; 
                                 
                            }
                          
                   
                     
                   
                   }
               leftTemp="";
               rightTemp="";
           }
            
          
         
      
     
        
      
      return subformula;
      
      
    }
    
    
    
    
    
     
   public String processStandard(String subformula,ArrayList<Character> clist){
     
       if(isNumber(uncoverQuote(subformula))){
             return subformula;
         }
         
       subformula=optimizeFormula(subformula);
       
      String leftTemp="";
      String rightTemp="";
      double result=0;
      int QLPointer=0;
      int QRPointer=0;
      char operandType;
      Eshortuct eshortuct=new Eshortuct();
      
       
       if(Configuration.deepTracing)
       System.out.println("Standard SubFormula   "+subformula);
   
           
           for(int i=0;i<subformula.length();i++){
               
               
            // every iterate check if inside quote number to return it without iterate
                if(isNumber(uncoverQuote(subformula))){
                    return subformula;
                }
               
               // detect +- operand, now let's stop on it's index and take left and right numbers
               if(clist.contains(subformula.charAt(i))) {
                  
                 operandType=subformula.charAt(i);
               
                   
                   
             // detect there is operand in first subformula      
                   if(i<=1){
                    for(i=i+1;i<subformula.length();i++){
                            if(clist.contains(subformula.charAt(i))) {
                                operandType=subformula.charAt(i);                            
                                break; 
                            }
                        }
                   }
                       
                   
                   //obtain left number
                   for(int j=i-1;j>=0;j--){
                        
                        // detect E sympol which mean e^10
                        if(subformula.charAt(j)=='E'){
                            eshortuct=eshortuct.process(subformula, j);
                            leftTemp=eshortuct.leftTemp;
                            operandType=eshortuct.operandType;
                            i=eshortuct.rightOperandIndex;   
                            QLPointer=eshortuct.leftOperandIndex;
                         break;
                        }
                       
                        else if(list.contains(subformula.charAt(j))){
                          if(subformula.charAt(j)=='-'){
                               if(j>0&&subformula.charAt(j-1)=='E'){
                                     continue;
                                  }
                            leftTemp=subformula.charAt(j)+leftTemp; // append operand to left number
                            QLPointer=j-1;
                            break;
                          }
                          else{
                           QLPointer=j;  // pointer to last number
                           break;    
                          }
                       }
                       else{
                           
                           leftTemp=subformula.charAt(j)+leftTemp;
                   
                           
                       }
                   }
                          
                   //obtain right number
                   for(int jj=i+1;jj<subformula.length();jj++){
                       
                       
                      // detect E sympol which mean e^10
                        if(subformula.charAt(jj)=='E'){
                            eshortuct=eshortuct.process(subformula, jj);
                            rightTemp=eshortuct.leftTemp;
                            QRPointer=eshortuct.rightOperandIndex;
                         break;
                        }
   
                        else if(list.contains(subformula.charAt(jj))){
                              if(subformula.charAt(jj)=='-'){
                               if(subformula.charAt(jj-1)=='E'){
                           continue;
                           }
                              }
                            QRPointer=jj; // sopt pointer after right number to check there is another operand                     
                            break;
                       }
                       else{
                           rightTemp=rightTemp+subformula.charAt(jj);
                          
                       }
                   }
           
              
                   try{
                   double number1=Double.parseDouble(leftTemp);
                   double number2=Double.parseDouble(rightTemp);     
                   
                      
                   
                   if(operandType=='+'){
                      result=number1+number2;
                      }                      
                  else if(operandType=='-'){
                       if(rightTemp.indexOf("-")==0){
                          number2=number2*-1;
                      }
                      result=number1-number2;
                      }
                    
                    
                   }
                   catch(Exception ex){
                       System.err.println("Standard Exception"+ex);
                       return null;
                       
                   }
                   
                    if(Configuration.deepTracing){
                   System.out.println("Standard Result   "+result);
                   System.out.println("QLPointer   "+QLPointer);
                   System.out.println("QRPointer   "+QRPointer); 
                    }
                      
                   
                        if(QLPointer==0 && QRPointer==0){
                              return ("("+result+")");
                          }                 
                         else if(subformula.charAt(QLPointer)!='(' && subformula.charAt(QLPointer-1)!='('){   
                                subformula=subformula.substring(0,QLPointer+1)+result+subformula.substring(QRPointer, subformula.length());                               
                                subformula=optimizeFormula(subformula);
                                i=0;
                            }
                        
                           else if(subformula.charAt(QLPointer)=='('){
                                subformula="("+result+subformula.substring(QRPointer, subformula.length());
                                i=0; 
                            }
                           else if(!subformula.contains("/")&&!subformula.contains("*")&&!subformula.contains("+")&&!subformula.contains("-")&&!subformula.contains("^")){
                              
                                    return subformula; 
                                 
                            }
                           else{
                               return ("("+result+")");
                           }
                       if(Configuration.deepTracing)    
                       System.out.println("Standard SubFormula   "+subformula);
                     
                   
                   }
               leftTemp="";
               rightTemp="";
           }
            
          
         
      
     
         
      
      return subformula;
      
      
    }
    

     
    public double processCustomOperand(String customOperand,String finalQuoteNumber){
        
        
        
        if(customOperandMap_Constant.get(customOperand+finalQuoteNumber)!=null){
            if(customOperandMap_Constant.get(customOperand+finalQuoteNumber)  == Infinity.class){
              throw new CustomOperandException(customOperand+"("+finalQuoteNumber+")");  
            }
            CustomOperand obj=(CustomOperand)customOperandMap_Constant.get(customOperand+finalQuoteNumber);
            return obj.value;
        }
        
        double number=Double.parseDouble(finalQuoteNumber);
        
        switch(customOperand){
            
            case "Sin":
                return Math.sin(Math.toRadians(number));
                
            case "Cos":
                return Math.cos(Math.toRadians(number));
                
            case "Tan":
                return Math.tan(Math.toRadians(number));
                
            case "Log":
                return Math.log10(number);
                
             default:
                 break;
                
        }
            
            
            return number; // just dummy return
            
        }
        
        
    
    
        public String processSqrt(String subformula){
            String temp="";
            double sqrtResult=0;
            // iterate inside formula untill find '<>'
            for(int i=0;i<subformula.length();i++){   
                if(subformula.charAt(i)=='<'){  
                    
                    for(int j=i+1;j<subformula.length();j++){                      
                        if(subformula.charAt(j)=='>'){
                            try{
                            sqrtResult=Math.sqrt(Double.parseDouble(temp));
                            }
                            catch(Exception ex){
                                subformula="error";
                                break;
                            }
                            subformula=subformula.replaceFirst("<"+temp+">",""+sqrtResult);
                            temp="";
                            break;
                        }
                        else{
                             temp=temp+subformula.charAt(j);                    
                        }
                    }
                                        
                }
                
            }
            
            return subformula;
            
        }

    @Override
    public String optimizeFormula(String subformula) {
      
         subformula=subformula.replace("-+", "-");
         subformula=subformula.replace("+-", "-");
         subformula=subformula.replace("--", "+");
         
         return subformula;
    }
       
    
    
    
 
     public String uncoverQuote(String subformula){
          String uncoverQuote=subformula.replace("(", "");
                 uncoverQuote=uncoverQuote.replace(")", "");
                 return uncoverQuote;
     }
     
}
