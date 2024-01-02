package org.calccedo.configuration;

import org.calccedo.handlers.ConstantInjector;
import org.calccedo.handlers.FunctionInjector;
import org.calccedo.handlers.SemanticInjector;
import org.calccedo.handlers.VariableInjector;

class Setter {
   ConstantInjector constantInjector = new ConstantInjector();
   VariableInjector variableInjector = new VariableInjector();
   SemanticInjector semanticInjector = new SemanticInjector();
   FunctionInjector functionInjector = new FunctionInjector();

   public void set(String variableName, double new_value) {
      if (this.constantInjector.checkConstantExists(variableName)) {
         System.err.println("Error in set(" + variableName + "," + new_value + ") >>> " + variableName + " defined as a constant");
         System.exit(0);
      } else if (!this.variableInjector.checkVariableExists(variableName)) {
         System.err.println("Variable Name you want to update >>> " + variableName + " not exists");
         System.exit(0);
      } else {
         this.variableInjector.updateVariableTo(variableName, new_value);
      }

   }

   public void set(String semanticName, int quoteNumber, double new_value) {
      if (!this.semanticInjector.checkSemanticExists(semanticName, quoteNumber)) {
         System.err.println("Semantic Name you want to update >>> " + semanticName + "(" + quoteNumber + ") not exists");
         System.exit(0);
      } else {
         this.semanticInjector.updateSemanticTo(semanticName, quoteNumber, new_value);
      }

   }

   public void set(String semanticName, double quoteNumber, double new_value) {
      if (!this.semanticInjector.checkSemanticExists(semanticName, quoteNumber)) {
         System.err.println("Semantic Name you want to update >>> " + semanticName + "(" + quoteNumber + ") not exists");
         System.exit(0);
      } else {
         this.semanticInjector.updateSemanticTo(semanticName, quoteNumber, new_value);
      }

   }

   public void set(String semanticName, int quoteNumber, Object infinityOrError) {
      if (!this.semanticInjector.checkSemanticExists(semanticName, quoteNumber)) {
         System.err.println("Semantic Name you want to update >>> " + semanticName + "(" + quoteNumber + ") not exists");
         System.exit(0);
      } else {
         this.semanticInjector.updateSemanticTo(semanticName, quoteNumber, infinityOrError);
      }

   }

   public void set(String semanticName, double quoteNumber, Object infinityOrError) {
      if (!this.semanticInjector.checkSemanticExists(semanticName, quoteNumber)) {
         System.err.println("Semantic Name you want to update >>> " + semanticName + "(" + quoteNumber + ") not exists");
         System.exit(0);
      } else {
         this.semanticInjector.updateSemanticTo(semanticName, quoteNumber, infinityOrError);
      }

   }

   public void set(String functionName, Class operationalClass, String newMethodName) {
      if (!this.functionInjector.checkFunctionExists(functionName)) {
         System.err.println("Function Name you want to update >>> " + functionName + "(Number... numbers) not exists");
         System.exit(0);
      } else {
         this.functionInjector.updateFunctionTo(functionName, operationalClass, newMethodName);
      }

   }
}
