package org.calccedo.handlers;

import org.calccedo.configuration.Angel;
import org.calccedo.configuration.Configuration;
import org.calccedo.configuration.Structure;
import org.calccedo.configuration.Symbols;
import java.util.ArrayList;
import java.util.HashMap;

class SubFormula extends CalccedoHandler {
   Quote quote;
   ArrayList<Character> basicOperatorsList;
   ArrayList<Character> plus_minus_operatorsList;
   ArrayList<String> semanticsNameList;
   ArrayList<String> functionNameList;
   HashMap<String, Object> semanticsNameMap;
   HashMap<String, Function> injectedFunctionsMap;
   StringBuilder subformula;

   public SubFormula(Quote quote) {
      this.basicOperatorsList = Symbols.basicOperatorsList;
      this.plus_minus_operatorsList = Symbols.plus_minus_OperatorsList;
      this.semanticsNameList = Structure.semanticsNameList;
      this.functionNameList = Structure.functionsNameList;
      this.semanticsNameMap = SemanticInjector.semanticsNameMap;
      this.injectedFunctionsMap = FunctionInjector.injectedFunctionsMap;
      this.quote = quote;
      this.subformula = new StringBuilder(quote.subformula);
   }

   public QuoteResult getQuoteResult() {
      if (this.validational(this.subformula) == null) {
         return null;
      } else {
         String subformula_str = this.subformula.toString();
         if (subformula_str.contains("<")) {
            this.subformula = this.processSqrt(this.subformula);
            if (this.subformula.equals("error")) {
               return null;
            }
         }

         if (Configuration.deepTracing) {
            System.out.println("<> Subformula " + this.subformula);
         }

         if (subformula_str.contains("^")) {
            this.subformula = this.processPriority(this.subformula, '^');
            if (this.subformula.equals("error")) {
               return null;
            }
         }

         if (Configuration.deepTracing) {
            System.out.println("^ Subformula " + this.subformula);
         }

         if (subformula_str.contains("%")) {
            this.subformula = this.processPriority(this.subformula, '%');
            if (this.subformula.equals("error")) {
               return null;
            }
         }

         if (Configuration.deepTracing) {
            System.out.println("% Subformula " + this.subformula);
         }

         if (subformula_str.contains("/")) {
            this.subformula = this.processPriority(this.subformula, '/');
            if (this.subformula.equals("error")) {
               return null;
            }
         }

         if (Configuration.deepTracing) {
            System.out.println("/ Subformula " + this.subformula);
         }

         if (subformula_str.contains("*")) {
            this.subformula = this.processPriority(this.subformula, '*');
            if (this.subformula.equals("error")) {
               return null;
            }
         }

         if (Configuration.deepTracing) {
            System.out.println("* Subformula " + this.subformula);
         }

         if (subformula_str.contains("+") || subformula_str.contains("-")) {
            this.subformula = this.processStandard(this.subformula, this.plus_minus_operatorsList);
            if (this.subformula.equals("error")) {
               return null;
            }
         }

         if (Configuration.deepTracing) {
            System.out.println("+- Subformula " + this.subformula);
         }

         this.stringBuilderReplacer("(", "", this.subformula);
         this.stringBuilderReplacer(")", "", this.subformula);
         String finalQuoteNumber = this.subformula.toString();
         Number result = 0;
      
         if (this.semanticsNameList.contains(this.quote.tokenName)) {
            try {
               result = this.processSemantic(this.quote.tokenName, finalQuoteNumber);
               if (result != null) {
                  return new QuoteResult(this.quote.offsetA - this.quote.tokenName.length(), this.quote.offsetZ, (Number)result);
               }

               if (this.functionNameList.contains(this.quote.tokenName)) {
                  try {
                     result = this.processInjectedFunction(this.quote.tokenName, finalQuoteNumber);
                     return new QuoteResult(this.quote.offsetA - this.quote.tokenName.length(), this.quote.offsetZ, result);
                  } catch (Exception var6) {
                     System.err.println(var6);
                     return null;
                  }
               }
            } catch (SemanticException var10) {
               return null;
            } catch (RuntimeException var11) {
               System.err.println(var11);
               return null;
            }
         } else {
            if (this.functionNameList.contains(this.quote.tokenName)) {
               try {
                  result = this.processInjectedFunction(this.quote.tokenName, finalQuoteNumber);
                  return new QuoteResult(this.quote.offsetA - this.quote.tokenName.length(), this.quote.offsetZ, result);
               } catch (Exception var7) {
                  System.err.println(var7);
                  return null;
               }
            }

            try {
               result = Integer.parseInt(this.optemizeNumeric(finalQuoteNumber));
            } catch (Exception var9) {
               try {
                  result = Double.parseDouble(finalQuoteNumber);
               } catch (Exception var8) {
                  return null;
               }
            }
         }

         if (Configuration.deepTracing) {
            System.out.println("Result >>>>>>>>>>>>>>>>>>>>>>>>>>>>" + result);
         }

         return new QuoteResult(this.quote.offsetA, this.quote.offsetZ, (Number)result);
      }
   }

   public Object validational(StringBuilder subformula) {
      if (subformula.charAt(1) != '/' && subformula.charAt(1) != '*' && subformula.charAt(1) != '^') {
         return this.basicOperatorsList.contains(subformula.charAt(subformula.length() - 2)) ? null : "okay";
      } else {
         return null;
      }
   }

   public StringBuilder processPriority(StringBuilder subformula, char operand) {
      this.uncoverQuote(subformula);
      if (this.isNumber(subformula)) {
         return subformula;
      } else {
         this.coverQuote(subformula);
         this.optimizeFormula(subformula);
         StringBuilder leftTemp = new StringBuilder();
         StringBuilder rightTemp = new StringBuilder();
         double result = 0.0D;
         String infinityTester = "";
         int QLPointer = 0;
         int QRPointer = 0;
         Eshortuct eshortuct = new Eshortuct();

         for(int i = 0; i < subformula.length(); ++i) {
            if (subformula.charAt(i) == operand) {
               this.uncoverQuote(subformula);
               if (this.isNumber(subformula)) {
                  return subformula;
               }

               this.coverQuote(subformula);

               int jj;
               for(jj = i - 1; jj >= 0; --jj) {
                  if (subformula.charAt(jj) == 'E') {
                     eshortuct = eshortuct.process(subformula, jj);
                     leftTemp = eshortuct.leftTemp;
                     operand = eshortuct.operandType;
                     i = eshortuct.rightOperandIndex;
                     QLPointer = eshortuct.leftOperandIndex;
                     break;
                  }

                  if (this.basicOperatorsList.contains(subformula.charAt(jj))) {
                     if (subformula.charAt(jj) != '-' || operand == '^') {
                        QLPointer = jj;
                        break;
                     }

                     if (jj <= 0 || subformula.charAt(jj - 1) != 'E') {
                        leftTemp.insert(0, subformula.charAt(jj));
                        QLPointer = jj - 1;
                        break;
                     }
                  } else {
                     leftTemp.insert(0, subformula.charAt(jj));
                  }
               }

               for(jj = i + 1; jj < subformula.length(); ++jj) {
                  if (subformula.charAt(jj) == 'E') {
                     eshortuct = eshortuct.process(subformula, jj);
                     rightTemp = eshortuct.leftTemp;
                     QRPointer = eshortuct.rightOperandIndex;
                     break;
                  }

                  if (this.basicOperatorsList.contains(subformula.charAt(jj)) && this.basicOperatorsList.contains(subformula.charAt(jj - 1))) {
                     rightTemp.append(subformula.charAt(jj));
                     QRPointer = jj + 1;
                  } else if (this.basicOperatorsList.contains(subformula.charAt(jj))) {
                     if (subformula.charAt(jj) != '-' || operand == '^' || subformula.charAt(jj - 1) != 'E') {
                        QRPointer = jj;
                        break;
                     }
                  } else {
                     rightTemp.append(subformula.charAt(jj));
                  }
               }

               try {
                  double number1 = Double.parseDouble(leftTemp.toString());
                  double number2 = Double.parseDouble(rightTemp.toString());
                  switch(operand) {
                  case '%':
                     result = number1 % number2;
                     break;
                  case '*':
                     result = number1 * number2;
                     break;
                  case '/':
                     result = number1 / number2;
                     infinityTester = result + "";
                     if (!infinityTester.equals("Infinity") && !infinityTester.equals("-Infinity") && !infinityTester.equals("NaN")) {
                        break;
                     }

                     return new StringBuilder("error");
                  case '^':
                     result = Math.pow(number1, number2);
                  }
               } catch (Exception var16) {
                  System.err.println("Priority Exception" + var16);
                  return new StringBuilder("error");
               }

               if (Configuration.deepTracing) {
                  System.out.println("Operand " + operand + "    " + result);
                  System.out.println("QLPointer   " + QLPointer);
                  System.out.println("QRPointer   " + QRPointer);
               }

               String subformula_str = subformula.toString();
               String result_str = result + "";
               if (QLPointer == 0 && QRPointer == 0) {
                  return new StringBuilder("(" + result_str + ")");
               }

               if (subformula.charAt(QLPointer) != '(') {
                  subformula.replace(QLPointer + 1, QRPointer, result_str);
                  this.optimizeFormula(subformula);
                  i = 0;
               } else if (subformula.charAt(QLPointer) == '(' && QRPointer > 0) {
                  subformula.insert(0, "(").insert(1, result_str).replace(1 + result_str.length(), QRPointer + 1 + result_str.length(), "");
                  i = 0;
               } else if (!subformula_str.contains("/") && !subformula_str.contains("*") && !subformula_str.contains("+") && !subformula_str.contains("-") && !subformula_str.contains("^") && Configuration.deepTracing) {
                  return subformula;
               }

               subformula_str = null;
               result_str = null;
            }

            leftTemp.setLength(0);
            leftTemp.trimToSize();
            rightTemp.setLength(0);
            rightTemp.trimToSize();
         }

         return subformula;
      }
   }

   public StringBuilder processStandard(StringBuilder subformula, ArrayList<Character> clist) {
      this.uncoverQuote(subformula);
      if (this.isNumber(subformula)) {
         return subformula;
      } else {
         this.coverQuote(subformula);
         this.optimizeFormula(subformula);
         StringBuilder leftTemp = new StringBuilder();
         StringBuilder rightTemp = new StringBuilder();
         double result = 0.0D;
         int QLPointer = 0;
         int QRPointer = 0;
         Eshortuct eshortuct = new Eshortuct();
         if (Configuration.deepTracing) {
            System.out.println("Standard SubFormula   " + subformula);
         }

         for(int i = 0; i < subformula.length(); ++i) {
            this.uncoverQuote(subformula);
            if (this.isNumber(subformula)) {
               return subformula;
            }

            this.coverQuote(subformula);
            if (clist.contains(subformula.charAt(i))) {
               char operandType = subformula.charAt(i);
               if (i <= 1) {
                  ++i;

                  while(i < subformula.length()) {
                     if (clist.contains(subformula.charAt(i))) {
                        operandType = subformula.charAt(i);
                        break;
                     }

                     ++i;
                  }
               }

               int jj;
               for(jj = i - 1; jj >= 0; --jj) {
                  if (subformula.charAt(jj) == 'E') {
                     eshortuct = eshortuct.process(subformula, jj);
                     leftTemp = eshortuct.leftTemp;
                     operandType = eshortuct.operandType;
                     i = eshortuct.rightOperandIndex;
                     QLPointer = eshortuct.leftOperandIndex;
                     break;
                  }

                  if (this.basicOperatorsList.contains(subformula.charAt(jj))) {
                     if (subformula.charAt(jj) != '-') {
                        QLPointer = jj;
                        break;
                     }

                     if (jj <= 0 || subformula.charAt(jj - 1) != 'E') {
                        leftTemp.insert(0, subformula.charAt(jj));
                        QLPointer = jj - 1;
                        break;
                     }
                  } else {
                     leftTemp.insert(0, subformula.charAt(jj));
                  }
               }

               for(jj = i + 1; jj < subformula.length(); ++jj) {
                  if (subformula.charAt(jj) == 'E') {
                     eshortuct = eshortuct.process(subformula, jj);
                     rightTemp = eshortuct.leftTemp;
                     QRPointer = eshortuct.rightOperandIndex;
                     break;
                  }

                  if (this.basicOperatorsList.contains(subformula.charAt(jj))) {
                     if (subformula.charAt(jj) != '-' || subformula.charAt(jj - 1) != 'E') {
                        QRPointer = jj;
                        break;
                     }
                  } else {
                     rightTemp.append(subformula.charAt(jj));
                  }
               }

               try {
                  double number1 = Double.parseDouble(leftTemp.toString());
                  double number2 = Double.parseDouble(rightTemp.toString());
                  if (operandType == '+') {
                     result = number1 + number2;
                  } else if (operandType == '-') {
                     if (rightTemp.indexOf("-") == 0) {
                        number2 *= -1.0D;
                     }

                     result = number1 - number2;
                  }
               } catch (Exception var16) {
                  System.err.println("Standard Exception" + var16);
                  return null;
               }

               if (Configuration.deepTracing) {
                  System.out.println("Standard Result   " + result);
                  System.out.println("QLPointer   " + QLPointer);
                  System.out.println("QRPointer   " + QRPointer);
               }

               String subformula_str = subformula.toString();
               String result_str = result + "";
               if (QLPointer == 0 && QRPointer == 0) {
                  return new StringBuilder("(" + result_str + ")");
               }

               if ((subformula.charAt(QLPointer) == '(' || subformula.charAt(QLPointer - 1) != '(') && (subformula.charAt(QLPointer) == '(' || subformula.charAt(QLPointer - 1) == '(')) {
                  if (subformula.charAt(QLPointer) != '(') {
                     if (!subformula_str.contains("/") && !subformula_str.contains("*") && !subformula_str.contains("+") && !subformula_str.contains("-") && !subformula_str.contains("^")) {
                        return subformula;
                     }

                     return new StringBuilder("(" + result_str + ")");
                  }

                  subformula.insert(0, "(").insert(1, result_str).replace(1 + result_str.length(), QRPointer + 1 + result_str.length(), "");
                  i = 0;
               } else {
                  subformula.replace(QLPointer + 1, QRPointer, result_str);
                  this.optimizeFormula(subformula);
                  i = 0;
               }

               if (Configuration.deepTracing) {
                  System.out.println("Standard SubFormula   " + subformula);
               }

               subformula_str = null;
               result_str = null;
            }

            leftTemp.setLength(0);
            leftTemp.trimToSize();
            rightTemp.setLength(0);
            rightTemp.trimToSize();
         }

         return subformula;
      }
   }

   public Number processSemantic(String semanticName, String finalQuoteNumber) {
      if (this.semanticsNameMap.get(semanticName + finalQuoteNumber) != null && angel_type == Angel.DEGREES) {
         if (this.semanticsNameMap.get(semanticName + finalQuoteNumber) != Infinity.class && this.semanticsNameMap.get(semanticName + finalQuoteNumber) != Error.class) {
            Semantic obj = (Semantic)this.semanticsNameMap.get(semanticName + finalQuoteNumber);
            return obj.value;
         } else {
            throw new SemanticException(semanticName + "(" + finalQuoteNumber + ")");
         }
      } else {
         Double number = Double.parseDouble(finalQuoteNumber);
         byte var5 = -1;
         switch(semanticName.hashCode()) {
         case 3365:
            if (semanticName.equals("in")) {
               var5 = 10;
            }
            break;
         case 98695:
            if (semanticName.equals("cos")) {
               var5 = 1;
            }
            break;
         case 107332:
            if (semanticName.equals("log")) {
               var5 = 3;
            }
            break;
         case 113880:
            if (semanticName.equals("sin")) {
               var5 = 0;
            }
            break;
         case 114593:
            if (semanticName.equals("tan")) {
               var5 = 2;
            }
            break;
         case 2988422:
            if (semanticName.equals("acos")) {
               var5 = 8;
            }
            break;
         case 3003607:
            if (semanticName.equals("asin")) {
               var5 = 7;
            }
            break;
         case 3004320:
            if (semanticName.equals("atan")) {
               var5 = 9;
            }
            break;
         case 3059649:
            if (semanticName.equals("cosh")) {
               var5 = 5;
            }
            break;
         case 3530384:
            if (semanticName.equals("sinh")) {
               var5 = 4;
            }
            break;
         case 3552487:
            if (semanticName.equals("tanh")) {
               var5 = 6;
            }
         }

         switch(var5) {
         case 0:
            if (angel_type == Angel.RADIANS) {
               return Math.sin(Math.toDegrees(number) * 3.141592653589793D / 180.0D);
            } else if (angel_type == Angel.DEGREES) {
               return Math.sin(Math.toRadians(number));
            }
         case 1:
            if (angel_type == Angel.RADIANS) {
               return Math.cos(Math.toDegrees(number) * 3.141592653589793D / 180.0D);
            } else if (angel_type == Angel.DEGREES) {
               return Math.cos(Math.toRadians(number));
            }
         case 2:
            if (angel_type == Angel.RADIANS) {
               return Math.tan(Math.toDegrees(number) * 3.141592653589793D / 180.0D);
            } else if (angel_type == Angel.DEGREES) {
               return Math.tan(Math.toRadians(number));
            }
         case 3:
            return Math.log10(number);
         case 4:
            if (angel_type == Angel.RADIANS) {
               return Math.sinh(Math.toDegrees(number) * 3.141592653589793D / 180.0D);
            } else if (angel_type == Angel.DEGREES) {
               return Math.sinh(Math.toRadians(number));
            }
         case 5:
            if (angel_type == Angel.RADIANS) {
               return Math.cosh(Math.toDegrees(number) * 3.141592653589793D / 180.0D);
            } else if (angel_type == Angel.DEGREES) {
               return Math.cosh(Math.toRadians(number));
            }
         case 6:
            if (angel_type == Angel.RADIANS) {
               return Math.tanh(Math.toDegrees(number) * 3.141592653589793D / 180.0D);
            } else if (angel_type == Angel.DEGREES) {
               return Math.tanh(Math.toRadians(number));
            }
         case 7:
            if (angel_type == Angel.RADIANS) {
               return Math.asin(Math.toDegrees(number) * 3.141592653589793D / 180.0D);
            } else if (angel_type == Angel.DEGREES) {
               return Math.asin(Math.toRadians(number));
            }
         case 8:
            if (angel_type == Angel.RADIANS) {
               return Math.acos(Math.toDegrees(number) * 3.141592653589793D / 180.0D);
            } else if (angel_type == Angel.DEGREES) {
               return Math.acos(Math.toRadians(number));
            }
         case 9:
            if (angel_type == Angel.RADIANS) {
               return Math.atan(Math.toDegrees(number) * 3.141592653589793D / 180.0D);
            } else if (angel_type == Angel.DEGREES) {
               return Math.atan(Math.toRadians(number));
            }
         case 10:
            return Math.log(number);
         default:
            return null;
         }
      }
   }

   public double processInjectedFunction(String functionName, String argumentsStream) throws Exception {
      Function function = (Function)this.injectedFunctionsMap.get(functionName);
      FunctionHandler functionHandler = new FunctionHandler(function);
      double result = Double.parseDouble(functionHandler.calculateFunction(argumentsStream));
      return result;
   }

   public StringBuilder processSqrt(StringBuilder subformula) {
      StringBuilder temp = new StringBuilder();
      double sqrtResult = 0.0D;

      for(int i = 0; i < subformula.length(); ++i) {
         if (subformula.charAt(i) == '<') {
            for(int j = i + 1; j < subformula.length(); ++j) {
               if (subformula.charAt(j) == '>') {
                  try {
                     sqrtResult = Math.sqrt(Double.parseDouble(temp.toString()));
                  } catch (Exception var8) {
                     subformula.setLength(0);
                     subformula.trimToSize();
                     subformula.insert(0, "error");
                     break;
                  }

                  this.stringBuilderReplacer("<" + temp.toString() + ">", "" + sqrtResult, subformula);
                  temp.setLength(0);
                  temp.trimToSize();
                  break;
               }

               temp.append(subformula.charAt(j));
            }
         }
      }

      return subformula;
   }

   public void uncoverQuote(StringBuilder subformula) {
      this.stringBuilderReplacer("(", "", subformula);
      this.stringBuilderReplacer(")", "", subformula);
   }

   public void coverQuote(StringBuilder subformula) {
      subformula.insert(0, "(");
      subformula.insert(subformula.length(), ")");
   }

   public void stringBuilderReplacer(String oldStr, String newStr, StringBuilder strB) {
      try {
         strB.replace(strB.indexOf(oldStr), strB.indexOf(oldStr) + oldStr.length(), newStr);
      } catch (Exception var5) {
      }

   }

   public StringBuilder optimizeFormula(StringBuilder subformula) {
      this.stringBuilderReplacer("-+", "-", subformula);
      this.stringBuilderReplacer("+-", "-", subformula);
      this.stringBuilderReplacer("--", "+", subformula);
      return subformula;
   }

   public String optemizeNumeric(String string_number) {
      return string_number.endsWith(".0") ? string_number.substring(0, string_number.length() - 2) : string_number;
   }
}
