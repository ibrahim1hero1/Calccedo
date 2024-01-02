package org.calccedo.handlers;

import org.calccedo.calccedo.Calccedo;
import org.calccedo.configuration.Configuration;
import org.calccedo.configuration.Structure;
import org.calccedo.configuration.Symbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class CalccedoHandler extends Calccedo implements Handler {
   private ArrayList<Character> postfixSymbolsList;
   private ArrayList<Character> prefixSymbolsList;
   private HashMap<String, Double> constantsNameMap;
   private HashMap<String, Double> variablesNameMap;

   public CalccedoHandler() {
      this.postfixSymbolsList = Symbols.postfixSymbolsList;
      this.prefixSymbolsList = Symbols.prefixSymbolsList;
      this.constantsNameMap = ConstantInjector.constantsNameMap;
      this.variablesNameMap = VariableInjector.variableNameMap;
   }

   private Quote parsePartethis(String formula) {
      int offsetA = 0;
      int offsetZ = 0;
      String token = "";

      int t;
      for(t = formula.length() - 1; t >= 0; --t) {
         if (formula.charAt(t) == '(') {
            offsetA = t;

            for(int j = t; j < formula.length(); ++j) {
               if (formula.charAt(j) == ')') {
                  offsetZ = j;
                  t = -1;
                  break;
               }
            }
         }
      }

      if (offsetA == 0) {
         return new Quote(formula.substring(offsetA, offsetZ + 1), offsetA, offsetZ + 1);
      } else {
         try {
            for(t = offsetA - 1; t >= 0 && !this.isNumber(formula.charAt(t)) && !Symbols.basicOperatorsList.contains(formula.charAt(t)); --t) {
               token = formula.charAt(t) + token;
            }

            if (!Structure.semanticsNameList.contains(token) && !Structure.functionsNameList.contains(token)) {
               return new Quote(formula.substring(offsetA, offsetZ + 1), offsetA, offsetZ + 1);
            } else {
               return new Quote(token, formula.substring(offsetA, offsetZ + 1), offsetA, offsetZ + 1);
            }
         } catch (Exception var7) {
            return new Quote(formula.substring(offsetA, offsetZ + 1), offsetA, offsetZ + 1);
         }
      }
   }

   public boolean isNumber(char c) {
      if (c == '.') {
         return true;
      } else {
         try {
            Integer.parseInt("" + c);
            return true;
         } catch (Exception var3) {
            return false;
         }
      }
   }

   public StringBuilder optimizeFormula(StringBuilder formula) {
      StringBuilder newformula = new StringBuilder();

      for(int i = 0; i < formula.length(); ++i) {
         if (this.postfixSymbolsList.contains(formula.charAt(i)) && i > 0) {
            if (this.prefixSymbolsList.contains(formula.charAt(i - 1)) && i > 0) {
               if (formula.charAt(i) == '<') {
                  newformula.append("*").append(formula.charAt(i)).append("(");
               } else {
                  newformula.append("*").append(formula.charAt(i));
               }
            } else if (formula.charAt(i) == '<') {
               newformula.append(formula.charAt(i)).append("(");
            } else if (this.isNumber(formula.charAt(i - 1))) {
               newformula.append("*").append(formula.charAt(i));
            } else {
               newformula.append(formula.charAt(i));
            }
         } else if (formula.charAt(i) == '<') {
            newformula.append(formula.charAt(i)).append("(");
         } else if (formula.charAt(i) == '>') {
            newformula.append(")").append(formula.charAt(i));
         } else if (!this.isNumber(formula.charAt(i))) {
            newformula.append(formula.charAt(i));
         } else if (i <= 0 || formula.charAt(i - 1) != ')' && formula.charAt(i - 1) != '>') {
            newformula.append(formula.charAt(i));
         } else {
            newformula.append("*").append(formula.charAt(i));
         }
      }

      if (Configuration.deepTracing) {
         System.out.println("Optimization is >>>>>>>>>>>" + newformula);
      }

      return newformula;
   }

   public StringBuilder optimizeFormulaFromConstants(StringBuilder formula) {
      StringBuilder newformula = formula;
      Iterator iterator = this.constantsNameMap.entrySet().iterator();

      while(iterator.hasNext()) {
         Entry pair = (Entry)iterator.next();

         int startIndex;
         int lastIndex;
         for(String constantName = pair.getKey().toString(); newformula.indexOf(constantName) != -1; newformula.replace(startIndex, lastIndex, this.constantsNameMap.get(constantName) + "")) {
            startIndex = newformula.indexOf(constantName);
            lastIndex = newformula.indexOf(constantName) + constantName.length();
            if (lastIndex < newformula.length() && this.isNumber(newformula.charAt(lastIndex)) || lastIndex < newformula.length() && this.postfixSymbolsList.contains(newformula.charAt(lastIndex))) {
               newformula.insert(lastIndex, "*");
            }
         }
      }

      if (Configuration.deepTracing) {
         System.out.println("optinmization from constants is >>>>>>>>>>>" + newformula);
      }

      return newformula;
   }

   public StringBuilder optimizeFormulaFromVariables(StringBuilder formula) {
      StringBuilder newformula = formula;
      Iterator iterator = this.variablesNameMap.entrySet().iterator();

      while(iterator.hasNext()) {
         Entry pair = (Entry)iterator.next();

         int startIndex;
         int lastIndex;
         for(String variableName = pair.getKey().toString(); newformula.indexOf(variableName) != -1; newformula.replace(startIndex, lastIndex, this.variablesNameMap.get(variableName) + "")) {
            startIndex = newformula.indexOf(variableName);
            lastIndex = newformula.indexOf(variableName) + variableName.length();
            if (lastIndex < newformula.length() && this.isNumber(newformula.charAt(lastIndex)) || lastIndex < newformula.length() && this.postfixSymbolsList.contains(newformula.charAt(lastIndex))) {
               newformula.insert(lastIndex, "*");
            }
         }
      }

      if (Configuration.deepTracing) {
         System.out.println("optinmization from variables is >>>>>>>>>>>" + newformula + "\n");
      }

      return newformula;
   }

   public boolean initValidation(String formula) {
      if (!formula.contains("(") && !formula.contains("<") && !formula.contains(">") && !formula.contains(")")) {
         return true;
      } else {
         int openedPartethis = 0;
         int openedRoot = 0;

         for(int i = 0; i < formula.length(); ++i) {
            if (formula.charAt(i) == '(') {
               ++openedPartethis;
            }

            if (formula.charAt(i) == ')') {
               --openedPartethis;
            }

            if (openedPartethis < 0) {
               return false;
            }

            if (formula.charAt(i) == '<') {
               ++openedRoot;
            }

            if (formula.charAt(i) == '>') {
               --openedRoot;
            }

            if (openedRoot < 0) {
               return false;
            }
         }

         return openedPartethis == 0 && openedRoot == 0;
      }
   }

   public String calculate(String formula) {
      if (!this.initValidation(formula)) {
         return "Error";
      } else {
         StringBuilder formu = new StringBuilder(formula);
         formu = this.optimizeFormula(formu);
         formu = this.optimizeFormulaFromConstants(formu);
         formu = this.optimizeFormulaFromVariables(formu);
         String firstProcess = this.calculatePartethis(formu.toString());
         if (firstProcess.equals("error")) {
            return "Error";
         } else {
            String secondProcess = this.calculatePartethis("(" + firstProcess + ")");
            if (secondProcess.equals("error")) {
               return "Error";
            } else {
               if (secondProcess.endsWith(".0")) {
                  secondProcess = secondProcess.substring(0, secondProcess.lastIndexOf(".0"));
               }

               return secondProcess;
            }
         }
      }
   }

   private String calculatePartethis(String formula) {
      while(formula.contains("(")) {
         Quote quote = this.parsePartethis(formula);
         SubFormula subFormula = new SubFormula(quote);
         QuoteResult quoteResult = subFormula.getQuoteResult();
         if (quoteResult == null) {
            return "error";
         }

         formula = formula.substring(0, quoteResult.offsetA) + quoteResult.result + formula.substring(quoteResult.offsetZ, formula.length());
      }

      if (Configuration.deepTracing) {
         System.out.println("formula after parsing partethis" + formula);
      }

      return formula;
   }

   public boolean isNumber(StringBuilder quote) {
      try {
         Double.parseDouble(quote.toString());
         return true;
      } catch (Exception var3) {
         if (Configuration.deepTracing) {
            System.out.println("\nCalccedo Info: this is just info to help developers how Calccedo Library work");
            System.out.println("Info quote " + quote + ", cannot wrapped to Double, so it will complete loop until finishing operations \n");
         }

         return false;
      }
   }

   public String toString() {
      return "This is the backbone handler class of calccedo, it recieves formula and handle all cycles to bring result";
   }
}
