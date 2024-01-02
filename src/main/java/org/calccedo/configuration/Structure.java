package org.calccedo.configuration;

import java.util.ArrayList;

public class Structure {
   public static ArrayList<String> semanticsNameList = new ArrayList();
   public static ArrayList<String> functionsNameList = new ArrayList();
   public static ArrayList<String> constantsNameList = new ArrayList();
   public static ArrayList<String> variableNameList = new ArrayList();

   static void addSemantic(String semanticName) {
      semanticsNameList.add(semanticName);
   }

   static void removeSemantic(String semanticName) {
      semanticsNameList.remove(semanticName);
   }

   static void addFunction(String functionName) {
      functionsNameList.add(functionName);
   }

   static void removeFunction(String functionName) {
      functionsNameList.remove(functionName);
   }

   static void addConstant(String constantName) {
      constantsNameList.add(constantName);
   }

   static void removeConstant(String constantName) {
      constantsNameList.remove(constantName);
   }

   static void addVariable(String variableName) {
      variableNameList.add(variableName);
   }

   static void removeVariable(String variableName) {
      variableNameList.remove(variableName);
   }

   static void configureSemantics() {
      addSemantic("log");
      addSemantic("sinh");
      addSemantic("cosh");
      addSemantic("tanh");
      addSemantic("asin");
      addSemantic("acos");
      addSemantic("atan");
      addSemantic("in");
   }
}
