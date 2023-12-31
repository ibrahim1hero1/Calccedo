package org.calccedo.handlers;

import java.lang.reflect.Method;
import java.util.HashMap;

public class FunctionInjector {
   public static HashMap<String, Function> injectedFunctionsMap = new HashMap();

   public void add(Class operationalClass, String methodName) {
      this.initialValidation(methodName);
      this.redundancyValidation(methodName, operationalClass);
      this.restrictionValidation(operationalClass, methodName);
      injectedFunctionsMap.put(methodName, new Function(operationalClass, methodName));
   }

   public void add(String functionName, Class operationalClass, String methodName) {
      this.initialValidation(functionName);
      this.redundancyValidation(functionName, operationalClass);
      this.restrictionValidation(operationalClass, methodName);
      injectedFunctionsMap.put(functionName, new Function(operationalClass, methodName));
   }

   public void remove(String functionName) {
      injectedFunctionsMap.remove(functionName);
   }

   public void updateFunctionTo(String functionName, Class operationalClass, String methodName) {
      injectedFunctionsMap.put(functionName, new Function(operationalClass, methodName));
   }

   public boolean checkFunctionExists(String functionName) {
      if (injectedFunctionsMap.containsKey(functionName)) {
         return true;
      } else {
         System.err.println("you want to remove function called " + functionName + " and " + functionName + " not exists, please ensure that you register this function name >>> " + functionName);
         return false;
      }
   }

   private void initialValidation(String functionName) {
      for(int i = 0; i < functionName.length(); ++i) {
         if (!Character.isLetter(functionName.charAt(i))) {
            System.err.println("Exception: Calccedo refuse injected function " + functionName + "() \nmust contain only Letters \n\nAVG() valid  \n\nbut AV2G() not valid ");
            System.exit(0);
         }
      }

   }

   private void redundancyValidation(String functionOrMethodName, Class operationalClass) {
      if (injectedFunctionsMap.containsKey(functionOrMethodName)) {
         System.err.println("Exception: " + functionOrMethodName + "(Number... numbers) already defined!");
         System.exit(0);
      }

   }

   private void restrictionValidation(Class operationalClass, String methodName) {
      Method method = null;

      try {
         method = operationalClass.getDeclaredMethod(methodName, Number[].class);
      } catch (NoSuchMethodException var5) {
         System.err.println("Exception: you want to inject " + methodName + "(...) into Calccedo and not defined in class " + operationalClass.getName());
         System.out.println("Injected Function must declare as below:");
         System.out.println("Like: public int " + methodName + "(Number... numbers){");
         System.out.println("\treturn 5;");
         System.out.println("}");
         System.exit(0);
      } catch (SecurityException var6) {
         System.err.println("Exception: compiler refuse " + methodName + "(Number... numbers) in class " + operationalClass.getName() + " for security reasons!");
         System.exit(0);
      }

      if (!method.getReturnType().isPrimitive() && method.getReturnType() != Short.class && method.getReturnType() != Integer.class && method.getReturnType() != Long.class && method.getReturnType() != Double.class && method.getReturnType() != Float.class) {
         System.err.println("Exception: you want to inject " + methodName + "(...) in class " + operationalClass.getName());
         System.err.println("Injected Function must return Number");
         System.err.println("Like: public int " + methodName + "(Number... numbers){");
         System.err.println("\treturn 5;");
         System.err.println("}");
         System.exit(0);
      }

   }
}
