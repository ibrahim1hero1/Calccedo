package org.calccedo.configuration;

import org.calccedo.handlers.ConstantInjector;
import org.calccedo.handlers.FunctionInjector;
import org.calccedo.handlers.Infinity;
import org.calccedo.handlers.SemanticInjector;
import org.calccedo.handlers.VariableInjector;

public abstract class Configuration {
   public static boolean deepTracing = false;
   public static boolean closeGate = false;
   public static boolean closeCompiler = false;
   public static Angel angel_type;
   SemanticInjector semanticInjector = new SemanticInjector();
   FunctionInjector functionInjector = new FunctionInjector();
   ConstantInjector constantInjector = new ConstantInjector();
   VariableInjector variableInjector = new VariableInjector();
   Setter setter = new Setter();

   protected void init() {
      if (!closeGate) {
         this.addSemantic("sin", 30, 0.5D);
         this.addSemantic("sin", 30.0D, 0.5D);
         this.addSemantic("sin", 90, 1.0D);
         this.addSemantic("sin", 90.0D, 1.0D);
         this.addSemantic("sin", 150, 0.5D);
         this.addSemantic("sin", 150.0D, 0.5D);
         this.addSemantic("sin", 180, 0.0D);
         this.addSemantic("sin", 180.0D, 0.0D);
         this.addSemantic("sin", 210, -0.5D);
         this.addSemantic("sin", 210.0D, -0.5D);
         this.addSemantic("sin", 270, -1.0D);
         this.addSemantic("sin", 270.0D, -1.0D);
         this.addSemantic("sin", 330, -0.5D);
         this.addSemantic("sin", 330.0D, -0.5D);
         this.addSemantic("sin", 360, 0.0D);
         this.addSemantic("sin", 360.0D, 0.0D);
         this.addSemantic("cos", 60, 0.5D);
         this.addSemantic("cos", 60.0D, 0.5D);
         this.addSemantic("cos", 90, 0.0D);
         this.addSemantic("cos", 90.0D, 0.0D);
         this.addSemantic("cos", 180, -1.0D);
         this.addSemantic("cos", 180.0D, -1.0D);
         this.addSemantic("cos", 240, -0.5D);
         this.addSemantic("cos", 240.0D, -0.5D);
         this.addSemantic("cos", 270, 0.0D);
         this.addSemantic("cos", 270.0D, 0.0D);
         this.addSemantic("cos", 300, 0.5D);
         this.addSemantic("cos", 300.0D, 0.5D);
         this.addSemantic("cos", 360, 1.0D);
         this.addSemantic("cos", 360.0D, 1.0D);
         this.addSemantic("tan", 45, 1.0D);
         this.addSemantic("tan", 45.0D, 1.0D);
         this.addSemantic("tan", 180, 0.0D);
         this.addSemantic("tan", 180.0D, 0.0D);
         this.addSemantic("tan", 225, 1.0D);
         this.addSemantic("tan", 225.0D, 1.0D);
         this.addSemantic("tan", 360, 0.0D);
         this.addSemantic("tan", 360.0D, 0.0D);
         this.addSemantic("tan", 90, Infinity.class);
         this.addSemantic("tan", 90.0D, Infinity.class);
         this.addSemantic("tan", 270, Infinity.class);
         this.addSemantic("tan", 270.0D, Infinity.class);
         this.addSemantic("tan", 315, Infinity.class);
         this.addSemantic("tan", 315.0D, Infinity.class);
         this.addConstant("pi", 3.14D);
         this.addConstant("PI", 3.14D);
         Symbols.configurePostfixSymbols();
         Symbols.configurePrefixSymbols();
         Symbols.configureBasicOperators();
         Symbols.configurePlusMinusOperators();
         Structure.configureSemantics();
         closeGate = true;
      }

   }

   protected void enableDeepTrace(boolean enabled) {
      deepTracing = enabled;
   }

   protected void configAngel(Angel angel_type) {
      Configuration.angel_type = angel_type;
   }

   protected void addSemantic(String semanticName, int quoteNumber, double value) {
      if (!closeCompiler) {
         this.semanticInjector.add(semanticName, quoteNumber, value);
         Symbols.addPostfixSymbol(semanticName.charAt(0));
         Structure.addSemantic(semanticName);
      }

   }

   protected void addSemantic(String semanticName, double quoteNumber, double value) {
      if (!closeCompiler) {
         this.semanticInjector.add(semanticName, quoteNumber, value);
         Symbols.addPostfixSymbol(semanticName.charAt(0));
         Structure.addSemantic(semanticName);
      }

   }

   protected void addSemantic(String semanticName, int quoteNumber, Object infinityOrError) {
      if (!closeCompiler) {
         this.semanticInjector.add(semanticName, quoteNumber, infinityOrError);
         Symbols.addPostfixSymbol(semanticName.charAt(0));
         Structure.addSemantic(semanticName);
      }

   }

   protected void addSemantic(String semanticName, double quoteNumber, Object infinityOrError) {
      if (!closeCompiler) {
         this.semanticInjector.add(semanticName, quoteNumber, infinityOrError);
         Symbols.addPostfixSymbol(semanticName.charAt(0));
         Structure.addSemantic(semanticName);
      }

   }

   protected void injectFunction(Class operationalClass, String methodName) {
      if (!closeCompiler) {
         this.functionInjector.add(operationalClass, methodName);
         Symbols.addPostfixSymbol(methodName.charAt(0));
         Structure.addFunction(methodName);
      }

   }

   protected void injectFunction(String functionName, Class operationalClass, String methodName) {
      if (!closeCompiler) {
         this.functionInjector.add(functionName, operationalClass, methodName);
         Symbols.addPostfixSymbol(functionName.charAt(0));
         Structure.addFunction(functionName);
      }

   }

   protected void addConstant(String constantName, double constantValue) {
      if (!closeCompiler) {
         this.constantInjector.add(constantName, constantValue);
         Symbols.addPostfixSymbol(constantName.charAt(0));
         Structure.addConstant(constantName);
      }

   }

   protected void addVariable(String variableName, double variableValue) {
      if (!closeCompiler) {
         this.variableInjector.add(variableName, variableValue);
         Symbols.addPostfixSymbol(variableName.charAt(0));
         Structure.addVariable(variableName);
      }

   }

   protected void removeConstant(String constantName) {
      boolean constantExists = this.constantInjector.checkConstantExists(constantName);
      if (constantExists && !closeCompiler) {
         this.constantInjector.remove(constantName);
         Symbols.removePostfixSymbol(constantName.charAt(0));
         Structure.removeConstant(constantName);
      }

   }

   protected void removeVariable(String variableName) {
      boolean variableExists = this.variableInjector.checkVariableExists(variableName);
      if (variableExists && !closeCompiler) {
         this.variableInjector.remove(variableName);
         Symbols.removePostfixSymbol(variableName.charAt(0));
         Structure.removeVariable(variableName);
      }

   }

   protected void removeSemantic(String semanticName, int quoteNumber) {
      boolean semanticExists = this.semanticInjector.checkSemanticExists(semanticName, quoteNumber);
      if (semanticExists && !closeCompiler) {
         this.semanticInjector.remove(semanticName, quoteNumber);
         Symbols.removePostfixSymbol(semanticName.charAt(0));
         Structure.removeSemantic(semanticName);
      }

   }

   protected void removeSemantic(String semanticName, double quoteNumber) {
      boolean semanticExists = this.semanticInjector.checkSemanticExists(semanticName, quoteNumber);
      if (semanticExists && !closeCompiler) {
         this.semanticInjector.remove(semanticName, quoteNumber);
         Symbols.removePostfixSymbol(semanticName.charAt(0));
         Structure.removeSemantic(semanticName);
      }

   }

   protected void removeFunction(String functionName) {
      boolean functionExists = this.functionInjector.checkFunctionExists(functionName);
      if (functionExists && !closeCompiler) {
         this.functionInjector.remove(functionName);
         Symbols.removePostfixSymbol(functionName.charAt(0));
         Structure.removeFunction(functionName);
      }

   }

   protected void set(String variableName, double new_value) {
      this.setter.set(variableName, new_value);
   }

   protected void set(String semanticName, int quoteNumber, double new_value) {
      this.setter.set(semanticName, quoteNumber, new_value);
   }

   protected void set(String semanticName, double quoteNumber, double new_value) {
      this.setter.set(semanticName, quoteNumber, new_value);
   }

   protected void set(String semanticName, int quoteNumber, Object infinityOrError) {
      this.setter.set(semanticName, quoteNumber, infinityOrError);
   }

   protected void set(String semanticName, double quoteNumber, Object infinityOrError) {
      this.setter.set(semanticName, quoteNumber, infinityOrError);
   }

   protected void set(String functionName, Class operationalClass, String newMethodName) {
      this.setter.set(functionName, operationalClass, newMethodName);
   }

   static {
      angel_type = Angel.RADIANS;
   }
}
