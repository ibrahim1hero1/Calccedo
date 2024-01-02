package org.calccedo.handlers;

import java.util.HashMap;

public class ConstantInjector {
   public static HashMap<String, Double> constantsNameMap = new HashMap();

   public void add(String consatntName, double constantValue) {
      this.initialValidation(consatntName);
      this.redundancyValidation(consatntName);
      constantsNameMap.put(consatntName, constantValue);
   }

   public void remove(String consatntName) {
      constantsNameMap.remove(consatntName);
   }

   public boolean checkConstantExists(String constantName) {
      if (constantsNameMap.containsKey(constantName)) {
         return true;
      } else {
         System.err.println("you want to remove constant name called " + constantName + " and " + constantName + " not exists, please ensure that you register this constant name >>> " + constantName);
         return false;
      }
   }

   private void initialValidation(String constantName) {
      for(int i = 0; i < constantName.length(); ++i) {
         if (!Character.isLetter(constantName.charAt(i))) {
            System.err.println("Exception: Calccedo refuse " + constantName + " as a constant \nmust contain only Letters \n\nPI is valid which mean 3.14 \n\nbut P1I not valid ");
            System.exit(0);
         }
      }

   }

   private void redundancyValidation(String constantName) {
      if (constantsNameMap.containsKey(constantName)) {
         System.err.println("Exception: " + constantName + " already added!");
         System.exit(0);
      }

   }
}
