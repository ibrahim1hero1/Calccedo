package org.calccedo.handlers;

import java.util.HashMap;

public class SemanticInjector {
   public static HashMap<String, Object> semanticsNameMap = new HashMap();

   public void add(String semanticName, int quoteNumber, double value) {
      this.initialValidation(semanticName);
      this.redundancyValidation(semanticName, quoteNumber);
      semanticsNameMap.put(semanticName + quoteNumber, new Semantic(semanticName + quoteNumber, value));
   }

   public void add(String semanticName, double quoteNumber, double value) {
      this.initialValidation(semanticName);
      this.redundancyValidation(semanticName, quoteNumber);
      semanticsNameMap.put(semanticName + quoteNumber, new Semantic(semanticName + quoteNumber, value));
   }

   public void add(String semanticName, int quoteNumber, Object infinityOrError) {
      this.initialValidation(semanticName);
      this.redundancyValidation(semanticName, quoteNumber);
      semanticsNameMap.put(semanticName + quoteNumber, infinityOrError);
   }

   public void add(String semanticName, double quoteNumber, Object infinityOrError) {
      this.initialValidation(semanticName);
      this.redundancyValidation(semanticName, quoteNumber);
      semanticsNameMap.put(semanticName + quoteNumber, infinityOrError);
   }

   public HashMap<String, Object> getsemanticNameMap() {
      return semanticsNameMap;
   }

   public void remove(String semanticName, int quoteNumber) {
      semanticsNameMap.remove(semanticName + quoteNumber);
   }

   public void remove(String semanticName, double quoteNumber) {
      semanticsNameMap.remove(semanticName + quoteNumber);
   }

   public void updateSemanticTo(String semanticName, int quoteNumber, double new_value) {
      semanticsNameMap.put(semanticName + quoteNumber, new Semantic(semanticName + quoteNumber, new_value));
   }

   public void updateSemanticTo(String semanticName, double quoteNumber, double new_value) {
      semanticsNameMap.put(semanticName + quoteNumber, new Semantic(semanticName + quoteNumber, new_value));
   }

   public void updateSemanticTo(String semanticName, int quoteNumber, Object infinityOrError) {
      semanticsNameMap.put(semanticName + quoteNumber, infinityOrError);
   }

   public void updateSemanticTo(String semanticName, double quoteNumber, Object infinityOrError) {
      semanticsNameMap.put(semanticName + quoteNumber, infinityOrError);
   }

   public boolean checkSemanticExists(String semanticName, Number quoteNumber) {
      if (semanticsNameMap.containsKey(semanticName + quoteNumber)) {
         return true;
      } else {
         System.err.println("you want to remove semantic called " + semanticName + " and " + semanticName + " not exists, please ensure that you register this semantic name >>> " + semanticName);
         return false;
      }
   }

   private void initialValidation(String semanticName) {
      for(int i = 0; i < semanticName.length(); ++i) {
         if (!Character.isLetter(semanticName.charAt(i))) {
            System.err.println("Exception: Calccedo refuse " + semanticName + "() as a semantic \nmust contain only Letters \n\nSin(30) valid \n\nbut Si2n(30) not valid ");
            System.exit(0);
         }
      }

   }

   private void redundancyValidation(String semanticName, int quoteNumber) {
      if (semanticsNameMap.containsKey(semanticName + quoteNumber)) {
         System.err.println("Exception: " + semanticName + "(" + quoteNumber + ")" + " already added!");
         System.exit(0);
      }

   }

   private void redundancyValidation(String semanticName, double quoteNumber) {
      if (semanticsNameMap.containsKey(semanticName + quoteNumber)) {
         System.err.println("Exception: " + semanticName + "(" + quoteNumber + ")" + " already added!");
         System.exit(0);
      }

   }
}
