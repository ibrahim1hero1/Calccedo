package org.calccedo.handlers;

import org.calccedo.configuration.Configuration;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

class FunctionHandler {
   Function function;
   Class operationalClass;
   String methodName;

   public FunctionHandler(Function function) {
      this.function = function;
      this.operationalClass = function.getOperationalClass();
      this.methodName = function.getMethodName();
   }

   public String calculateFunction(String argumentsStream) throws Exception {
      String calculateResult = "";
      Method method = this.operationalClass.getDeclaredMethod(this.methodName, Number[].class);
      Configuration.closeCompiler = true;
      Object obj = this.operationalClass.newInstance();
      calculateResult = method.invoke(obj, this.fetchArgumentsValue(argumentsStream)).toString();
      return calculateResult;
   }

   public Number[] fetchArgumentsValue(String stream) {
      int counter = 0;
      Number[] argumentsValue = null;
      if (stream.contains(",")) {
         StringTokenizer tokenizer = new StringTokenizer(stream, ",");

         while(tokenizer.hasMoreElements()) {
            ++counter;
            tokenizer.nextToken(",");
         }

         argumentsValue = new Number[counter];
         counter = 0;

         for(tokenizer = new StringTokenizer(stream, ","); tokenizer.hasMoreTokens(); ++counter) {
            argumentsValue[counter] = Double.parseDouble(tokenizer.nextToken(","));
         }
      } else {
         argumentsValue = new Number[]{Double.parseDouble(stream)};
      }

      return argumentsValue;
   }
}
