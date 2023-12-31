package org.calccedo.handlers;

class Function {
   String functionName;
   Class operationalClass;
   String methodName;

   public Function(Class operationalClass, String methodName) {
      this.operationalClass = operationalClass;
      this.methodName = methodName;
   }

   public Function(String functionName, Class operationalClass, String methodName) {
      this.functionName = functionName;
      this.operationalClass = operationalClass;
      this.methodName = methodName;
   }

   public String getFunctionName() {
      return this.functionName;
   }

   public void setFunctionName(String functionName) {
      this.functionName = functionName;
   }

   public Class getOperationalClass() {
      return this.operationalClass;
   }

   public void setOperationalClass(Class operationalClass) {
      this.operationalClass = operationalClass;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public void setMethodName(String methodName) {
      this.methodName = methodName;
   }
}
