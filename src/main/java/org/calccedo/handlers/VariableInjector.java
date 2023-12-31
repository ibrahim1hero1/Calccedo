package org.calccedo.handlers;

import java.util.HashMap;

public class VariableInjector {
   public static HashMap<String, Double> variableNameMap = new HashMap();

   public void add(String variableName, double variableValue) {
      this.initialValidation(variableName);
      this.redundancyValidation(variableName);
      variableNameMap.put(variableName, variableValue);
   }

   public void remove(String variableName) {
      variableNameMap.remove(variableName);
   }

   public boolean checkVariableExists(String variableName) {
      if (variableNameMap.containsKey(variableName)) {
         return true;
      } else {
         System.err.println("you want to remove variable called " + variableName + " and " + variableName + " not exists, please ensure that you register this variable name >>> " + variableName);
         return false;
      }
   }

   public void updateVariableTo(String variableName, double newValue) {
      variableNameMap.put(variableName, newValue);
   }

   private void initialValidation(String variableName) {
      for(int i = 0; i < variableName.length(); ++i) {
         if (!Character.isLetter(variableName.charAt(i))) {
            System.err.println("Exception: Calccedo refuse " + variableName + " as a constant \nmust contain only Letters \n\nPI is valid which mean 3.14 \n\nbut P1I not valid ");
            System.exit(0);
         }
      }

   }

   private void redundancyValidation(String variableName) {
      if (variableNameMap.containsKey(variableName)) {
         System.err.println("Exception: " + variableName + " already added!");
         System.exit(0);
      }

   }
}
